package tech.grasshopper.combiner.options;

import lombok.Builder;
import tech.grasshopper.combiner.report.ReportType;

@Builder
public class PojoOptionsInputType extends OptionsInputType {

	private PojoOptions options;

	@Override
	public CombinerOptions generateOptions() {
		CombinerOptions combinerOptions = CombinerOptions.builder().build();

		combinerOptions.setReportType(ReportType.createReportType(options));

		PathsHandler paths = PathsHandler.builder().strJsonReportPaths(options.getJsonReportPaths())
				.strMediaPaths(options.getMediaPaths()).strMergedReportFolderPath(options.getMergedReportDirPath())
				.build();
		paths.processReportPaths();

		combinerOptions.setJsonReportPaths(paths.getJsonReportPaths());
		combinerOptions.setMediaPaths(paths.getMediaPaths());

		combinerOptions.setMergedReportFolderPath(paths.getMergedReportFolderPath());
		combinerOptions.setConfigType(ConfigType.createConfigType(options.getConfigType()));

		combinerOptions.getReportType().setCombinerOptions(combinerOptions);
		return combinerOptions;
	}
}
