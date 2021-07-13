package tech.grasshopper.combiner.report;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import lombok.Builder;
import tech.grasshopper.combiner.exception.CombinerException;
import tech.grasshopper.combiner.options.CombinerOptions;
import tech.grasshopper.combiner.options.ConfigType;

@Builder
public class MergedReport {

	private CombinerOptions combinerOptions;

	private String jsonReportName;

	private String sparkReportName;

	public void createReport() {
		Path combinedJson = Paths.get(combinerOptions.getMergedReportFolderPath().toString(), jsonReportName);

		ExtentReports extent = new ExtentReports();
		try {
			extent.createDomainFromJsonArchive(combinedJson.toFile());
		} catch (IOException e) {
			throw new CombinerException("Exception in creating merged JSON report.", e);
		}

		extent.attachReporter(createSparkReport());
		extent.flush();
	}

	private ExtentSparkReporter createSparkReport() {
		ExtentSparkReporter spark = new ExtentSparkReporter(
				Paths.get(combinerOptions.getMergedReportFolderPath().toString(), sparkReportName).toFile());

		try {
			if (combinerOptions.getConfigType() == ConfigType.XML)
				spark.loadXMLConfig(combinerOptions.getMergedReportFolderPath().toString() + "/" + "extent-config.xml");
			else if (combinerOptions.getConfigType() == ConfigType.JSON)
				spark.loadJSONConfig(
						combinerOptions.getMergedReportFolderPath().toString() + "/" + "extent-config.json");
		} catch (IOException e) {
			throw new CombinerException("Unable to load extent configuration file for Spark report.");
		}

		return spark;
	}
}
