package tech.grasshopper.combiner.options;

import java.nio.file.Path;
import java.util.List;

import lombok.Builder;
import lombok.Data;
import tech.grasshopper.combiner.report.ReportType;

@Data
@Builder
public class CombinerOptions {

	private ReportType reportType;
	private List<Path> jsonReportPaths;
	private List<Path> mediaPaths;
	private Path mergedReportFolderPath;
	private ConfigType configType;
}
