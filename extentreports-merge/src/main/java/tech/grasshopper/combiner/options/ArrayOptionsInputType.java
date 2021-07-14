package tech.grasshopper.combiner.options;

import java.util.Arrays;
import java.util.List;

import lombok.Builder;
import tech.grasshopper.combiner.exception.CombinerException;

@Builder
public class ArrayOptionsInputType extends OptionsInputType {

	private String[] options;

	private static final String REPORT_TYPE_OPTION = "-reportType";
	private static final String EXTRA_SCENARIO_TEST_STRATEGY_OPTION = "-extraStrategy";
	private static final String MATCHING_SCENARIO_TEST_STRATEGY_OPTION = "-matchingStrategy";
	private static final String JSON_REPORT_PATHS_OPTION = "-jsonReportPaths";
	private static final String MEDIA_PATHS_OPTION = "-mediaPaths";
	private static final String MERGED_REPORT_DIR_PATH_OPTION = "-mergedReportDirPath";
	private static final String CONFIG_TYPE_OPTION = "-configType";

	@Override
	public CombinerOptions generateOptions() {

		PojoOptions pojoOptions = PojoOptions.builder().build();

		for (int i = 0; i < options.length; i++) {
			String option = options[i];
			String value = "";

			if (option.equalsIgnoreCase(REPORT_TYPE_OPTION)) {
				value = getValue(i, REPORT_TYPE_OPTION);
				pojoOptions.setReportType(value);
			} else if (option.equalsIgnoreCase(EXTRA_SCENARIO_TEST_STRATEGY_OPTION)) {
				value = getValue(i, EXTRA_SCENARIO_TEST_STRATEGY_OPTION);
				pojoOptions.setExtraScenarioTestStrategy(value);
			} else if (option.equalsIgnoreCase(MATCHING_SCENARIO_TEST_STRATEGY_OPTION)) {
				value = getValue(i, MATCHING_SCENARIO_TEST_STRATEGY_OPTION);
				pojoOptions.setMatchingScenarioTestStrategy(value);
			} else if (option.equalsIgnoreCase(JSON_REPORT_PATHS_OPTION)) {
				value = getValue(i, JSON_REPORT_PATHS_OPTION);
				pojoOptions.setJsonReportPaths(splitAndListFilePaths(value));
			} else if (option.equalsIgnoreCase(MEDIA_PATHS_OPTION)) {
				value = getValue(i, MEDIA_PATHS_OPTION);
				pojoOptions.setMediaPaths(splitAndListFilePaths(value));
			} else if (option.equalsIgnoreCase(MERGED_REPORT_DIR_PATH_OPTION)) {
				value = getValue(i, MERGED_REPORT_DIR_PATH_OPTION);
				pojoOptions.setMergedReportDirPath(value);
			} else if (option.equalsIgnoreCase(CONFIG_TYPE_OPTION)) {
				value = getValue(i, CONFIG_TYPE_OPTION);
				pojoOptions.setConfigType(value);
			}
		}

		return PojoOptionsInputType.builder().options(pojoOptions).build().generateOptions();
	}

	private String getValue(int index, String option) {
		if (index + 1 > options.length)
			throw new CombinerException("No value available for option " + option);

		return options[index + 1].trim();
	}

	private List<String> splitAndListFilePaths(String filePaths) {
		if (filePaths == null || filePaths.isEmpty())
			return null;

		return Arrays.asList(filePaths.split(","));
	}

}
