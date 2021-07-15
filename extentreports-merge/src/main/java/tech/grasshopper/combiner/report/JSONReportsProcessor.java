package tech.grasshopper.combiner.report;

import java.nio.file.Path;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import lombok.Builder;
import tech.grasshopper.combiner.media.MediaHandler;
import tech.grasshopper.combiner.pojo.Test;
import tech.grasshopper.combiner.search.SearchTest;

@Builder
public class JSONReportsProcessor {

	private BaseReportType reportType;

	private List<Test> mergedTests;

	private Path primaryScenarioMediaPath;

	private Path secondaryScenarioMediaPath;

	public void processFeature(Test secondaryFeatureTest) {
		Optional<Test> primaryFeatureOptionalTest = SearchTest.findTestByNameAndType(mergedTests, secondaryFeatureTest);

		if (primaryFeatureOptionalTest.isPresent()) {
			Test primaryFeatureTest = primaryFeatureOptionalTest.get();

			for (Test secondaryScenarioOutlineTest : SearchTest.findScenarioOutlineInFeature(secondaryFeatureTest))
				processScenarioOutline(primaryFeatureTest, secondaryScenarioOutlineTest);

			for (Test secondaryScenarioTest : SearchTest.findScenarioInFeature(secondaryFeatureTest))
				processScenario(primaryFeatureTest, secondaryScenarioTest);

			// Update start and end times of feature
			processStartAndEndTimes(primaryFeatureTest);

		} else
			reportType.getExtraScenarioTestStrategy().processExtraSecondaryFeatureTest(mergedTests,
					secondaryFeatureTest);
	}

	protected void processScenarioOutline(Test primaryFeatureTest, Test secondaryScenarioOutlineTest) {
		Optional<Test> primaryScenarioOutlineOptionalTest = SearchTest
				.findTestByNameAndType(primaryFeatureTest.getChildren(), secondaryScenarioOutlineTest);

		if (primaryScenarioOutlineOptionalTest.isPresent()) {
			for (Test secondaryScenarioTest : secondaryScenarioOutlineTest.getChildren())
				processScenario(primaryScenarioOutlineOptionalTest.get(), secondaryScenarioTest);

			// Update start and end times of scenario outline
			processStartAndEndTimes(primaryScenarioOutlineOptionalTest.get());

		} else
			reportType.getExtraScenarioTestStrategy().processExtraSecondaryScenarioOutlineTest(primaryFeatureTest,
					secondaryScenarioOutlineTest);
	}

	protected void processScenario(Test primaryFeatureOrScenarioOutlineTest, Test secondaryScenarioTest) {
		Optional<Test> primaryScenarioOptionalTest = SearchTest
				.findTestByNameAndType(primaryFeatureOrScenarioOutlineTest.getChildren(), secondaryScenarioTest);

		if (primaryScenarioOptionalTest.isPresent())
			processMatchingScenarioTest(primaryFeatureOrScenarioOutlineTest, primaryScenarioOptionalTest.get(),
					secondaryScenarioTest);
		else
			reportType.getExtraScenarioTestStrategy()
					.processExtraSecondaryScenarioTest(primaryFeatureOrScenarioOutlineTest, secondaryScenarioTest);
	}

	protected void processMatchingScenarioTest(Test primaryFeatureOrScenarioOutlineTest, Test primaryScenarioTest,
			Test secondaryScenarioTest) {
		Test selectedTest = reportType.getMatchingScenarioTestStrategy().selectScenarioTest(primaryScenarioTest,
				secondaryScenarioTest);

		if (!selectedTest.equals(primaryScenarioTest)) {
			manageSecondaryMediaLogs(secondaryScenarioTest, primaryScenarioTest);

			int index = primaryFeatureOrScenarioOutlineTest.getChildren().indexOf(primaryScenarioTest);
			primaryFeatureOrScenarioOutlineTest.getChildren().add(index, selectedTest);
			primaryFeatureOrScenarioOutlineTest.getChildren().remove(index + 1);
		} else {
			managePrimaryMediaLogs(primaryScenarioTest, secondaryScenarioTest);
		}
	}

	protected void processStartAndEndTimes(Test test) {
		if (test.getChildren().isEmpty())
			return;

		test.setStartTime(Collections.min(test.getChildren(), Comparator.comparing(Test::getStartTime)).getStartTime());
		test.setEndTime(Collections.max(test.getChildren(), Comparator.comparing(Test::getEndTime)).getEndTime());
	}

	protected void managePrimaryMediaLogs(Test primaryScenarioTest, Test secondaryScenarioTest) {
		MediaHandler.builder().options(reportType.getCombinerOptions()).selectedScenarioTest(primaryScenarioTest)
				.selectedScenarioTestMediaFolder(primaryScenarioMediaPath).replacedScenarioTest(secondaryScenarioTest)
				.replacedScenarioTestMediaFolder(secondaryScenarioMediaPath).build().copyReportMedias();
	}

	protected void manageSecondaryMediaLogs(Test secondaryScenarioTest, Test primaryScenarioTest) {
		MediaHandler.builder().options(reportType.getCombinerOptions()).selectedScenarioTest(secondaryScenarioTest)
				.selectedScenarioTestMediaFolder(secondaryScenarioMediaPath).replacedScenarioTest(primaryScenarioTest)
				.replacedScenarioTestMediaFolder(primaryScenarioMediaPath).build().copyReportMedias();
	}
}
