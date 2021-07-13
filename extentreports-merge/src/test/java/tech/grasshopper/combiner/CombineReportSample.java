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
		createOptionsWithStrategyAndExecute("same scens early", EarlierScenarioTestStrategy.NAME);
	}

	@Test
	public void sameScenarioLaterStrategyCombine() {
		createOptionsWithStrategyAndExecute("same scens later", LaterScenarioTestStrategy.NAME);
	}

	@Test
	public void sameScenarioHighStatusStrategyCombine() {
		createOptionsWithStrategyAndExecute("same scens high status", HighStatusScenarioTestStrategy.NAME);
	}

	@Test
	public void sameScenarioLowStatusStrategyCombine() {
		createOptionsWithStrategyAndExecute("same scens low status", LowStatusScenarioTestStrategy.NAME);
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

	private void createOptionsWithStrategyAndExecute(String workingDir, String strategy) {
		pojoOption = PojoOptions.builder().matchingScenarioTestStrategy(strategy)
				.jsonReportPaths(createJsonReportList(workingDir)).mergedReportDirPath(createMergedDirPath(workingDir))
				.configType("xml").build();

		Combiner.main(pojoOption);
	}
}
