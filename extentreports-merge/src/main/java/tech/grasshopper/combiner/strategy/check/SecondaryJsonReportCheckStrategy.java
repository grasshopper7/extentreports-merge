package tech.grasshopper.combiner.strategy.check;

import java.util.List;

import tech.grasshopper.combiner.pojo.Test;
import tech.grasshopper.combiner.search.SearchTest;

public class SecondaryJsonReportCheckStrategy {

	public boolean execute(List<Test> test) {
		return true;
	}

	public static class RerunSecondaryJsonReportCheckStrategy extends SecondaryJsonReportCheckStrategy {

		@Override
		public boolean execute(List<Test> test) {
			if (SearchTest.passScenarioTestStatusCount(test) > 0)
				return true;

			return false;
		}
	}
}
