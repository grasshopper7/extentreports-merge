package tech.grasshopper.combiner.report;

import tech.grasshopper.combiner.exception.CombinerException;
import tech.grasshopper.combiner.options.CombinerOptions;
import tech.grasshopper.combiner.options.PojoOptions;
import tech.grasshopper.combiner.strategy.check.PrimaryJsonReportCheckStrategy;
import tech.grasshopper.combiner.strategy.check.PrimaryJsonReportCheckStrategy.RerunPrimaryJsonReportCheckStrategy;
import tech.grasshopper.combiner.strategy.check.SecondaryJsonReportCheckStrategy;
import tech.grasshopper.combiner.strategy.check.SecondaryJsonReportCheckStrategy.RerunSecondaryJsonReportCheckStrategy;
import tech.grasshopper.combiner.strategy.extra.ExtraScenarioTestStrategy.AddExtraScenarioTestStrategy;
import tech.grasshopper.combiner.strategy.extra.ExtraScenarioTestStrategy.ErrorExtraScenarioTestStrategy;
import tech.grasshopper.combiner.strategy.matching.MatchingScenarioTestStrategy;
import tech.grasshopper.combiner.strategy.matching.MatchingScenarioTestStrategy.LaterScenarioTestStrategy;
import tech.grasshopper.combiner.strategy.matching.MatchingScenarioTestStrategy.RerunPassScenarioTestStrategy;

public interface ReportType {

	public static ReportType createReportType(PojoOptions options) {

		String reportType = options.getReportType();

		// Default report type
		if (null == reportType || reportType.equals("") || reportType.equalsIgnoreCase("combine")) {
			BaseReportType baseReportType = BaseReportType.builder()
					.extraScenarioTestStrategy(new AddExtraScenarioTestStrategy())
					.matchingScenarioTestStrategy(new LaterScenarioTestStrategy())
					.primaryJsonReportCheckStrategy(new PrimaryJsonReportCheckStrategy())
					.secondaryJsonReportCheckStrategy(new SecondaryJsonReportCheckStrategy()).build();

			String matchingStrategy = options.getMatchingScenarioTestStrategy();
			if (null != matchingStrategy && !matchingStrategy.isEmpty())
				baseReportType
						.setMatchingScenarioTestStrategy(MatchingScenarioTestStrategy.createMatchingStrategy(matchingStrategy));

			return baseReportType;
		}

		// Disregard all other options. Log this in the console.
		if (reportType.equalsIgnoreCase("rerun"))
			return BaseReportType.builder().extraScenarioTestStrategy(new ErrorExtraScenarioTestStrategy())
					.matchingScenarioTestStrategy(new RerunPassScenarioTestStrategy())
					.primaryJsonReportCheckStrategy(new RerunPrimaryJsonReportCheckStrategy())
					.secondaryJsonReportCheckStrategy(new RerunSecondaryJsonReportCheckStrategy()).build();

		throw new CombinerException(
				"There is no matching in built report for the provided type - '" + options.getReportType() + "'.");
	}

	public abstract void generateReport();

	public abstract void setCombinerOptions(CombinerOptions combinerOptions);
}
