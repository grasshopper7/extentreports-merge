package tech.grasshopper.combiner.strategy.check;

import java.util.List;

import tech.grasshopper.combiner.exception.CombinerException;
import tech.grasshopper.combiner.pojo.Test;
import tech.grasshopper.combiner.search.SearchTest;

public class PrimaryJsonReportCheckStrategy {

	public void execute(List<Test> test) {

	}

	public static class RerunPrimaryJsonReportCheckStrategy extends PrimaryJsonReportCheckStrategy {

		@Override
		public void execute(List<Test> tests) {
			if (SearchTest.failFeatureTestStatusCount(tests) == 0)
				throw new CombinerException("No failed test in master JSON report.");
		}
	}
}
