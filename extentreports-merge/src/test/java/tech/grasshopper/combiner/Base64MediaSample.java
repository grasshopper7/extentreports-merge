package tech.grasshopper.combiner;

import java.util.Arrays;

import org.junit.Test;

import tech.grasshopper.combiner.options.PojoOptions;
import tech.grasshopper.combiner.strategy.matching.MatchingScenarioTestStrategy;

public class Base64MediaSample {

	private PojoOptions pojoOption;

	@Test
	public void combineBase64Media() {

		String[] jsonReports = { "sample/combine/base64/both/ExtentJson1.json",
				"sample/combine/base64/both/ExtentJson2.json" };
		String mergedReportDir = "sample/combine/base64/both";

		pojoOption = PojoOptions.builder().jsonReportPaths(Arrays.asList(jsonReports))
				.mergedReportDirPath(mergedReportDir).build();

		Combiner.main(pojoOption);
	}

	@Test
	public void combineImageAndSelectBase64Media() {

		String[] jsonReports = { "sample/combine/base64/separate/ExtentJson1.json",
				"sample/combine/base64/separate/ExtentJson2.json" };
		String mergedReportDir = "sample/combine/base64/separate/imgselbase";

		pojoOption = PojoOptions.builder().jsonReportPaths(Arrays.asList(jsonReports))
				.mergedReportDirPath(mergedReportDir)/*
														 * .matchingScenarioTestStrategy(MatchingScenarioTestStrategy.
														 * EarlierScenarioTestStrategy.NAME)
														 */.build();

		Combiner.main(pojoOption);
	}

	@Test
	public void combineSelectImageAndBase64Media() {

		String[] jsonReports = { "sample/combine/base64/separate/ExtentJson1.json",
				"sample/combine/base64/separate/ExtentJson2.json" };
		String mergedReportDir = "sample/combine/base64/separate/selimgbase";

		pojoOption = PojoOptions.builder().jsonReportPaths(Arrays.asList(jsonReports))
				.mergedReportDirPath(mergedReportDir)
				.matchingScenarioTestStrategy(MatchingScenarioTestStrategy.EarlierScenarioTestStrategy.NAME).build();

		Combiner.main(pojoOption);
	}
}
