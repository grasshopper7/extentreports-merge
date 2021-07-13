package tech.grasshopper.combiner.options;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PojoOptions {

	// combine, rerun
	private String reportType;
	// add, error
	// private String extraScenarioTestStrategy;
	// earlier, later, highstatus, lowstatus, passrerun
	private String matchingScenarioTestStrategy;

	// default, rerun
	// private String primaryJsonReportCheckStrategy;
	// default, rerun
	// private String secondaryJsonReportCheckStrategy;
	// default, rerun
	// private String scenarioTestProcessCheckStrategy;

	private List<String> jsonReportPaths;
	private List<String> mediaPaths;
	private String mergedReportDirPath;
	private String configType;
}
