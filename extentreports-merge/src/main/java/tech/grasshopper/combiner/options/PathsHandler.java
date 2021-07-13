package tech.grasshopper.combiner.options;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import tech.grasshopper.combiner.exception.CombinerException;

@Builder
public class PathsHandler {

	private List<String> strJsonReportPaths;
	private List<String> strMediaPaths;
	private String strMergedReportFolderPath;

	@Getter
	private List<Path> jsonReportPaths;
	@Getter
	private List<Path> mediaPaths;
	@Getter
	private Path mergedReportFolderPath;

	private static final String DEFAULT_WORKING_FOLDER = "target/";

	public void processReportPaths() {
		verifyPaths();

		jsonReportPaths = getFilePaths(strJsonReportPaths, "jsonReportPathsMsg");

		if (null == strMediaPaths || strMediaPaths.isEmpty())
			mediaPaths = defaultMediaPaths(jsonReportPaths);
		else
			mediaPaths = getFilePaths(strMediaPaths, "mediaPathsMsg");

		mergedReportFolderPath = getMergedFolderPath(strMergedReportFolderPath, "mergedReportDirMsg");
	}

	private Path getMergedFolderPath(String strMergedReportFolderPath, String mergedMsg) {
		if (strMergedReportFolderPath == null || strMergedReportFolderPath.isEmpty())
			strMergedReportFolderPath = DEFAULT_WORKING_FOLDER;

		return getFolderPath(strMergedReportFolderPath, mergedMsg);
	}

	private void verifyPaths() {
		if (strJsonReportPaths == null || strJsonReportPaths.isEmpty())
			throw new CombinerException("No JSON Reports are mentioned.");

		if (strJsonReportPaths.size() < 2)
			throw new CombinerException("Number of JSON Reports should be greater than 1.");

		if (strMediaPaths != null && !strMediaPaths.isEmpty() && strJsonReportPaths.size() != strMediaPaths.size())
			throw new CombinerException("Size of JSON reports and media paths are not equal.");
	}

	private List<Path> defaultMediaPaths(List<Path> jsonReportPaths) {
		List<Path> paths = new ArrayList<>();

		jsonReportPaths.forEach(p -> paths.add(p.getParent()));
		return paths;
	}

	private Path getFolderPath(String folderPath, String option) {
		Path path = Paths.get(folderPath.trim());

		if (!path.toFile().exists()) {
			try {
				Files.createDirectories(path);
			} catch (IOException e) {
				throw new CombinerException("Error in creating folder for " + option, e);
			}
		}
		return path;
	}

	private List<Path> getFilePaths(List<String> filePaths, String option) {
		List<Path> paths = new ArrayList<>();

		filePaths.forEach(p -> {
			try {
				paths.add(getFilePath(p.trim(), option));
			} catch (CombinerException e) {
				throw new CombinerException("File path value " + p + " invalid for option " + option);
			}
		});
		return paths;
	}

	private Path getFilePath(String filePath, String option) {
		Path path = Paths.get(filePath.trim());

		if (!path.toFile().exists())
			throw new CombinerException("File path value invalid for option " + option);
		return path;
	}
}
