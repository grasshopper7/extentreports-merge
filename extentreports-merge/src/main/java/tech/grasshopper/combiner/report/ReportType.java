package tech.grasshopper.combiner.report;

import tech.grasshopper.combiner.exception.CombinerException;
import tech.grasshopper.combiner.options.CombinerOptions;
import tech.grasshopper.combiner.options.PojoOptions;
import tech.grasshopper.combiner.strategy.check.PrimaryJsonReportCheckStrategy;
import tech.grasshopper.combiner.strategy.check.PrimaryJsonReportCheckStrategy.RerunPrimaryJsonReportCheckStrategy;
import tech.grasshopper.combiner.strategy.check.SecondaryJsonReportCheckStrategy;
import tech.grasshopper.combiner.strategy.check.SecondaryJsonReportCheckStrategy.RerunSecondaryJsonReportCheckStrategy;
import tech.grasshopper.combiner.strategy.extra.ExtraScenarioTestStrategy;
import tech.grasshopper.combiner.strategy.extra.ExtraScenarioTestStrategy.ErrorExtraScenarioTestStrategy;
import tech.grasshopper.combiner.strategy.matching.MatchingScenarioTestStrategy;
import tech.grasshopper.combiner.strategy.matching.MatchingScenarioTestStrategy.RerunPassScenarioTestStrategy;

public interface ReportType {

	public static ReportType createReportType(PojoOptions options) {

		String reportType = options.getReportType();

		// Default report type
		if (null == reportType || reportType.equals("") || reportType.equalsIgnoreCase("combine"))
			return BaseReportType.builder()
					.extraScenarioTestStrategy(
							ExtraScenarioTestStrategy.createExtraStrategy(options.getExtraScenarioTestStrategy()))
					.matchingScenarioTestStrategy(MatchingScenarioTestStrategy
							.createMatchingStrategy(options.getMatchingScenarioTestStrategy()))
					.primaryJsonReportCheckStrategy(PrimaryJsonReportCheckStrategy
							.createPrimaryJsonStrategy(options.getPrimaryJsonReportCheckStrategy()))
					.secondaryJsonReportCheckStrategy(SecondaryJsonReportCheckStrategy
							.createSecondaryJsonStrategy(options.getSecondaryJsonReportCheckStrategy()))
					.build();

		// Disregard all other options. Log this in the console.
		if (reportType.equalsIgnoreCase("rerun"))
			return BaseReportType.builder().extraScenarioTestStrategy(new ErrorExtraScenarioTestStrategy())
					.matchingScenarioTestStrategy(new RerunPassScenarioTestStrategy())
					.primaryJsonReportCheckStrategy(new RerunPrimaryJsonReportCheckStrategy())
					.secondaryJsonReportCheckStrategy(new RerunSecondaryJsonReportCheckStrategy()).build();

		// Custom report type
		try {
			return (ReportType) Class.forName(reportType).newInstance();
		} catch (Exception e) {
			throw new CombinerException("There is no report type availble for - " + reportType + ".");
		}
	}

	public abstract void generateReport();

	public abstract void setCombinerOptions(CombinerOptions combinerOptions);
}
