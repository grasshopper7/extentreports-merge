package tech.grasshopper.combiner;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import tech.grasshopper.combiner.options.PojoOptions;
import tech.grasshopper.combiner.strategy.matching.MatchingScenarioTestStrategy.EarlierScenarioTestStrategy;
import tech.grasshopper.combiner.strategy.matching.MatchingScenarioTestStrategy.HighStatusScenarioTestStrategy;
import tech.grasshopper.combiner.strategy.matching.MatchingScenarioTestStrategy.LaterScenarioTestStrategy;
import tech.grasshopper.combiner.strategy.matching.MatchingScenarioTestStrategy.LowStatusScenarioTestStrategy;

public class CombineReportSample {

	private PojoOptions pojoOption;

	@Test
	public void separateScenariosCombine() {
		createOptionsAndExecute("separate scens");
	}

	@Test
	public void sameScenarioOutlineSeparateScenariosCombine() {
		createOptionsAndExecute("same sos separate scens");
	}

	@Test
	public void sameScenarioEarlierStrategyCombine() {
		createOptionsWithMatchingStrategyAndExecute("time", "early", EarlierScenarioTestStrategy.NAME);
	}

	@Test
	public void sameScenarioLaterStrategyCombine() {
		createOptionsWithMatchingStrategyAndExecute("time", "later", LaterScenarioTestStrategy.NAME);
	}

	@Test
	public void sameScenarioHighStatusStrategyCombine() {
		createOptionsWithMatchingStrategyAndExecute("status", "high status", HighStatusScenarioTestStrategy.NAME);
	}

	@Test
	public void sameScenarioLowStatusStrategyCombine() {
		createOptionsWithMatchingStrategyAndExecute("status", "low status", LowStatusScenarioTestStrategy.NAME);
	}

	@Test
	public void multipleReportsCombine() {
		String workingDir = "multiple";
		List<String> reports = Arrays.asList(new String[] { "sample/combine/" + workingDir + "/ExtentJsonOne.json",
				"sample/combine/" + workingDir + "/ExtentJsonTwo.json",
				"sample/combine/" + workingDir + "/ExtentJsonThree.json",
				"sample/combine/" + workingDir + "/ExtentJsonFour.json" });

		pojoOption = PojoOptions.builder().jsonReportPaths(reports).mergedReportDirPath(createMergedDirPath(workingDir))
				.configType("xml").build();

		Combiner.main(pojoOption);

	}

	@Test
	public void mediaReportsCombine() {
		String workingDir = "media";
		List<String> reports = Arrays.asList(new String[] { "sample/combine/" + workingDir + "/first/ExtentJson.json",
				"sample/combine/" + workingDir + "/second/ExtentJson.json",
				"sample/combine/" + workingDir + "/no media/ExtentJson.json" });
		List<String> medias = Arrays.asList(new String[] { "sample/combine/" + workingDir + "/first",
				"sample/combine/" + workingDir + "/second", "sample/combine/" + workingDir + "/no media" });

		pojoOption = PojoOptions.builder().jsonReportPaths(reports).mergedReportDirPath(createMergedDirPath(workingDir))
				.mediaPaths(medias).build();

		Combiner.main(pojoOption);

	}

	private void createOptionsAndExecute(String workingDir) {
		pojoOption = PojoOptions.builder().jsonReportPaths(createJsonReportList(workingDir))
				.mergedReportDirPath(createMergedDirPath(workingDir)).configType("xml").build();

		Combiner.main(pojoOption);
	}

	private List<String> createJsonReportList(String workingDir) {
		return Arrays.asList(new String[] { "sample/combine/" + workingDir + "/ExtentJsonOne.json",
				"sample/combine/" + workingDir + "/ExtentJsonTwo.json" });
	}

	private String createMergedDirPath(String workingDir) {
		return "sample/combine/" + workingDir;
	}

	private void createOptionsWithMatchingStrategyAndExecute(String jsonReportDir, String workingDir, String strategy) {
		workingDir = "matching/" + workingDir;
		jsonReportDir = "matching/" + jsonReportDir;

		pojoOption = PojoOptions.builder().matchingScenarioTestStrategy(strategy)
				.jsonReportPaths(createJsonReportList(jsonReportDir))
				.mergedReportDirPath(createMergedDirPath(workingDir)).configType("xml").build();

		Combiner.main(pojoOption);
	}
}
