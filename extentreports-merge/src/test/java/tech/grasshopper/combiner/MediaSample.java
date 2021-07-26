package tech.grasshopper.combiner;

import java.util.Arrays;

import org.junit.Test;

import tech.grasshopper.combiner.options.PojoOptions;

public class MediaSample {

	private PojoOptions pojoOption;

	@Test
	public void combineTwoReportsDefaultMedia() {

		String[] jsonReports = { "sample/combine/media/first/ExtentJson.json",
				"sample/combine/media/second/ExtentJson.json" };
		String mergedReportDir = "sample/combine/media/final";

		pojoOption = PojoOptions.builder().jsonReportPaths(Arrays.asList(jsonReports))
				.mergedReportDirPath(mergedReportDir).build();

		Combiner.main(pojoOption);
	}

	@Test
	public void rerunTwoReports() {

		String[] jsonReports = { "sample/rerun/media/2 reports/first/ExtentJson.json",
				"sample/rerun/media/2 reports/second/ExtentJson.json" };
		String[] medias = { "sample/rerun/media/2 reports/first", "sample/rerun/media/2 reports/second" };
		String mergedReportDir = "sample/rerun/media/2 reports/final";

		pojoOption = PojoOptions.builder().reportType("rerun").jsonReportPaths(Arrays.asList(jsonReports))
				.mediaPaths(Arrays.asList(medias)).mergedReportDirPath(mergedReportDir).build();

		Combiner.main(pojoOption);
	}

	@Test
	public void rerunThreeReports() {

		String[] jsonReports = { "sample/rerun/media/3 reports/first/ExtentJson.json",
				"sample/rerun/media/3 reports/second/ExtentJson.json",
				"sample/rerun/media/3 reports/third/ExtentJson.json" };
		String[] medias = { "sample/rerun/media/3 reports/first", "sample/rerun/media/3 reports/second",
				"sample/rerun/media/3 reports/third" };
		String mergedReportDir = "sample/rerun/media/3 reports/final";

		pojoOption = PojoOptions.builder().reportType("rerun").jsonReportPaths(Arrays.asList(jsonReports))
				.mediaPaths(Arrays.asList(medias)).mergedReportDirPath(mergedReportDir).build();

		Combiner.main(pojoOption);
	}
}
