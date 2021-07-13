package tech.grasshopper.combiner;

import org.junit.Test;

public class CmdOptionsSample {

	@Test
	public void cmdOptionsCombine() {
		String[] args = new String[6];

		args[0] = "-jsonReportPaths";
		args[1] = "sample/combine/cmdline/ExtentJsonOne.json, sample/combine/cmdline/ExtentJsonTwo.json, sample/combine/cmdline/ExtentJsonThree.json";
		args[2] = "-mergedReportDirPath";
		args[3] = "sample/combine/cmdline/";
		args[4] = "-configType";
		args[5] = "xml";

		Combiner.main(args);
	}

	@Test
	public void cmdOptionsMediaRerun() {
		String[] args = new String[10];

		args[0] = "-reportType";
		args[1] = "rerun";
		args[2] = "-jsonReportPaths";
		args[3] = "sample/rerun/cmdline/first/ExtentJson.json, sample/rerun/cmdline/second/ExtentJson.json";
		args[4] = "-mediaPaths";
		args[5] = "sample/rerun/cmdline/first, sample/rerun/cmdline/second";
		args[6] = "-mergedReportDirPath";
		args[7] = "sample/rerun/cmdline/combined";
		args[8] = "-configType";
		args[9] = "xml";

		Combiner.main(args);
	}

}
