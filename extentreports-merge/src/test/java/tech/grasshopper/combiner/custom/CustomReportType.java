package tech.grasshopper.combiner.custom;

import tech.grasshopper.combiner.options.CombinerOptions;
import tech.grasshopper.combiner.report.ReportType;

public class CustomReportType implements ReportType {

	@Override
	public void generateReport() {
		System.out.println("Custom Report Type - Write logic in the generateReport() method.");
	}

	@Override
	public void setCombinerOptions(CombinerOptions combinerOptions) {

	}
}
