package tech.grasshopper.combiner;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import tech.grasshopper.combiner.options.PojoOptions;

public class ReRunReportSample {

	private PojoOptions pojoOption;

	private String reportsDir = "sample/rerun/reports";

	@Test
	public void singleRunRerun() {
		List<String> jsonReports = Arrays
				.asList(new String[] { reportsDir + "/ExtentJsonMaster.json", reportsDir + "/ExtentJsonRerun1.json" });
		pojoOption = PojoOptions.builder().reportType("rerun").jsonReportPaths(jsonReports)
				.mergedReportDirPath("sample/rerun/single run").configType("xml").build();

		Combiner.main(pojoOption);
	}

	@Test
	public void multipeRunsRerunAllPass() {
		List<String> jsonReports = Arrays.asList(new String[] { reportsDir + "/ExtentJsonMaster.json",
				reportsDir + "/ExtentJsonRerun1.json", reportsDir + "/ExtentJsonRerun2Pass.json" });
		pojoOption = PojoOptions.builder().reportType("rerun").jsonReportPaths(jsonReports)
				.mergedReportDirPath("sample/rerun/multiple runs").configType("xml").build();

		Combiner.main(pojoOption);
	}
	
	@Test
	public void multipeRunsRerunFail() {
		List<String> jsonReports = Arrays.asList(new String[] { reportsDir + "/ExtentJsonMaster.json",
				reportsDir + "/ExtentJsonRerun1.json", reportsDir + "/ExtentJsonRerun2Fail.json" });
		pojoOption = PojoOptions.builder().reportType("rerun").jsonReportPaths(jsonReports)
				.mergedReportDirPath("sample/rerun/multiple runs fail").configType("xml").build();

		Combiner.main(pojoOption);
	}
}
