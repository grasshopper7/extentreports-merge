package tech.grasshopper.combiner.options;

import java.util.List;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import tech.grasshopper.combiner.strategy.check.PrimaryJsonReportCheckStrategy;
import tech.grasshopper.combiner.strategy.check.SecondaryJsonReportCheckStrategy;
import tech.grasshopper.combiner.strategy.extra.ExtraScenarioTestStrategy.AddExtraScenarioTestStrategy;
import tech.grasshopper.combiner.strategy.matching.MatchingScenarioTestStrategy.LaterScenarioTestStrategy;;

@Data
@Builder
public class PojoOptions {

	// combine, rerun
	private String reportType;

	// add, error
	@Default
	private String extraScenarioTestStrategy = AddExtraScenarioTestStrategy.NAME;

	// earlier, later, highstatus, lowstatus, passrerun
	@Default
	private String matchingScenarioTestStrategy = LaterScenarioTestStrategy.NAME;

	// default, rerun
	@Default
	private String primaryJsonReportCheckStrategy = PrimaryJsonReportCheckStrategy.NAME;

	// default, rerun
	@Default
	private String secondaryJsonReportCheckStrategy = SecondaryJsonReportCheckStrategy.NAME;

	private List<String> jsonReportPaths;
	private List<String> mediaPaths;
	private String mergedReportDirPath;
	private String configType;
}
