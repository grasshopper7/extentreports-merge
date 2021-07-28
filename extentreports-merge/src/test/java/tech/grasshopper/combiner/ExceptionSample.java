package tech.grasshopper.combiner;

import java.util.Arrays;

import org.junit.Test;

import tech.grasshopper.combiner.options.PojoOptions;
import tech.grasshopper.combiner.strategy.matching.MatchingScenarioTestStrategy;

public class ExceptionSample {

	private PojoOptions pojoOption;

	@Test
	public void combineException() {

		String[] jsonReports = { "sample/combine/exception/ExtentJson1.json",
				"sample/combine/exception/ExtentJson2.json" };
		String mergedReportDir = "sample/combine/exception";

		pojoOption = PojoOptions.builder().jsonReportPaths(Arrays.asList(jsonReports))
				.mergedReportDirPath(mergedReportDir)
				.matchingScenarioTestStrategy(MatchingScenarioTestStrategy.EarlierScenarioTestStrategy.NAME).build();

		Combiner.main(pojoOption);
	}
}
