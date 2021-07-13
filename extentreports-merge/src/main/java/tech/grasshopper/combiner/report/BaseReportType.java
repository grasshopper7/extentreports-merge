package tech.grasshopper.combiner.report;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Builder;
import lombok.Data;
import tech.grasshopper.combiner.media.MediaHandler;
import tech.grasshopper.combiner.options.CombinerOptions;
import tech.grasshopper.combiner.pojo.Test;
import tech.grasshopper.combiner.strategy.check.PrimaryJsonReportCheckStrategy;
import tech.grasshopper.combiner.strategy.check.SecondaryJsonReportCheckStrategy;
import tech.grasshopper.combiner.strategy.extra.ExtraScenarioTestStrategy;
import tech.grasshopper.combiner.strategy.matching.MatchingScenarioTestStrategy;
import tech.grasshopper.combiner.util.FileOperations;

@Data
@Builder
public class BaseReportType implements ReportType {

	protected ExtraScenarioTestStrategy extraScenarioTestStrategy;

	protected MatchingScenarioTestStrategy matchingScenarioTestStrategy;

	protected PrimaryJsonReportCheckStrategy primaryJsonReportCheckStrategy;

	protected SecondaryJsonReportCheckStrategy secondaryJsonReportCheckStrategy;

	protected CombinerOptions combinerOptions;

	protected List<Test> mergedTests;

	private Path primaryScenarioMediaPath;

	private Path secondaryScenarioMediaPath;

	protected static final String MERGED_JSON_REPORT_NAME = "json.json";

	protected static final String MERGED_SPARK_REPORT_NAME = "spark.html";

	@Override
	public void generateReport() {

		createMediaFolder();

		combineTests();

		generateCombinedReport();
	}

	protected void combineTests() {
		mergedTests = Arrays.stream(FileOperations.readTestsFromJSONReport(combinerOptions.getJsonReportPaths().get(0)))
				.collect(Collectors.toList());

		primaryJsonReportCheckStrategy.execute(mergedTests);

		for (int i = 1; i < combinerOptions.getJsonReportPaths().size(); i++) {
			Path reportPath = combinerOptions.getJsonReportPaths().get(i);

			if (i == 1)
				primaryScenarioMediaPath = combinerOptions.getMediaPaths().get(0);
			else
				primaryScenarioMediaPath = Paths.get(combinerOptions.getMergedReportFolderPath().toString(),
						MediaHandler.MEDIA_FOLDER);
			secondaryScenarioMediaPath = combinerOptions.getMediaPaths().get(i);

			mergeReportTests(reportPath);
		}
	}

	protected void mergeReportTests(Path reportPath) {
		List<Test> jsonTests = Arrays.stream(FileOperations.readTestsFromJSONReport(reportPath))
				.collect(Collectors.toList());

		if (!secondaryJsonReportCheckStrategy.execute(jsonTests))
			return;

		for (Test featureTest : jsonTests)
			JSONReportsProcessor.builder().reportType(this).primaryScenarioMediaPath(primaryScenarioMediaPath)
					.secondaryScenarioMediaPath(secondaryScenarioMediaPath).mergedTests(mergedTests).build()
					.processFeature(featureTest);
	}

	protected void createMediaFolder() {
		FileOperations.createMediaFolder(combinerOptions.getMergedReportFolderPath().toString());
	}

	protected void generateCombinedReport() {
		Path combinedJson = Paths.get(combinerOptions.getMergedReportFolderPath().toString(), MERGED_JSON_REPORT_NAME);
		FileOperations.writeTestsToJSONReport(mergedTests, combinedJson);

		MergedReport.builder().combinerOptions(combinerOptions).jsonReportName(MERGED_JSON_REPORT_NAME)
				.sparkReportName(MERGED_SPARK_REPORT_NAME).build().createReport();
	}
}
