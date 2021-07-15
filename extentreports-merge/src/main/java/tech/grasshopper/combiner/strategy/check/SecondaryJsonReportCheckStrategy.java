package tech.grasshopper.combiner.strategy.check;

import java.util.List;

import tech.grasshopper.combiner.exception.CombinerException;
import tech.grasshopper.combiner.pojo.Test;
import tech.grasshopper.combiner.search.SearchTest;

public class SecondaryJsonReportCheckStrategy {

	public static final String NAME = "DEFAULT";

	public static SecondaryJsonReportCheckStrategy createSecondaryJsonStrategy(String strategy) {

		if (strategy == null || strategy.isEmpty() || strategy.equalsIgnoreCase(SecondaryJsonReportCheckStrategy.NAME))
			return new SecondaryJsonReportCheckStrategy();

		if (strategy.equalsIgnoreCase(RerunSecondaryJsonReportCheckStrategy.NAME))
			return new RerunSecondaryJsonReportCheckStrategy();

		try {
			return (SecondaryJsonReportCheckStrategy) Class.forName(strategy).newInstance();
		} catch (Exception e) {
			throw new CombinerException(
					"There is no secondary json report check strategy available for - '" + strategy + "'.");
		}
	}

	public boolean executeCheck(List<Test> test) {
		return true;
	}

	public static class RerunSecondaryJsonReportCheckStrategy extends SecondaryJsonReportCheckStrategy {
		public static final String NAME = "RERUN";

		@Override
		public boolean executeCheck(List<Test> test) {
			if (SearchTest.passScenarioTestStatusCount(test) > 0)
				return true;

			return false;
		}
	}
}
