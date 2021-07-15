package tech.grasshopper.combiner.strategy.check;

import java.util.List;

import tech.grasshopper.combiner.exception.CombinerException;
import tech.grasshopper.combiner.pojo.Test;
import tech.grasshopper.combiner.search.SearchTest;

public class PrimaryJsonReportCheckStrategy {

	public static final String NAME = "DEFAULT";

	public static PrimaryJsonReportCheckStrategy createPrimaryJsonStrategy(String strategy) {

		if (strategy == null || strategy.isEmpty() || strategy.equalsIgnoreCase(PrimaryJsonReportCheckStrategy.NAME))
			return new PrimaryJsonReportCheckStrategy();

		if (strategy.equalsIgnoreCase(RerunPrimaryJsonReportCheckStrategy.NAME))
			return new RerunPrimaryJsonReportCheckStrategy();

		try {
			return (PrimaryJsonReportCheckStrategy) Class.forName(strategy).newInstance();
		} catch (Exception e) {
			throw new CombinerException(
					"There is no primary json report check strategy available for - '" + strategy + "'.");
		}
	}

	public void executeCheck(List<Test> test) {
	}

	public static class RerunPrimaryJsonReportCheckStrategy extends PrimaryJsonReportCheckStrategy {
		public static final String NAME = "RERUN";

		@Override
		public void executeCheck(List<Test> tests) {
			if (SearchTest.failFeatureTestStatusCount(tests) == 0)
				throw new CombinerException("No failed test in master JSON report.");
		}
	}
}
