package tech.grasshopper.combiner;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import tech.grasshopper.combiner.options.PojoOptions;

public class CustomReportSample {

	private PojoOptions pojoOption;

	@Test
	public void customStrategyCombine() {
		String workingDir = "custom";

		List<String> reports = Arrays.asList(new String[] { "sample/combine/" + workingDir + "/ExtentJsonOne.json",
				"sample/combine/" + workingDir + "/ExtentJsonTwo.json",
				"sample/combine/" + workingDir + "/ExtentJsonThree.json",
				"sample/combine/" + workingDir + "/ExtentJsonFour.json" });

		pojoOption = PojoOptions.builder()
				.extraScenarioTestStrategy("tech.grasshopper.combiner.custom.CustomExtraScenarioTestStrategy")
				.matchingScenarioTestStrategy("tech.grasshopper.combiner.custom.CustomMatchingScenarioTestStrategy")
				.primaryJsonReportCheckStrategy("tech.grasshopper.combiner.custom.CustomPrimaryJsonReportCheckStrategy")
				.secondaryJsonReportCheckStrategy(
						"tech.grasshopper.combiner.custom.CustomSecondaryJsonReportCheckStrategy")
				.jsonReportPaths(reports).mergedReportDirPath("sample/combine/" + workingDir).configType("xml").build();

		Combiner.main(pojoOption);
	}

	/*
	 * @Test public void customReportType() { String workingDir = "custom";
	 * 
	 * List<String> reports = Arrays.asList(new String[] { "sample/combine/" +
	 * workingDir + "/ExtentJsonOne.json", "sample/combine/" + workingDir +
	 * "/ExtentJsonTwo.json" });
	 * 
	 * pojoOption = PojoOptions.builder().reportType(
	 * "tech.grasshopper.combiner.custom.CustomReportType")
	 * .jsonReportPaths(reports).mergedReportDirPath("sample/combine/" +
	 * workingDir).configType("xml").build();
	 * 
	 * Combiner.main(pojoOption); }
	 */
}
