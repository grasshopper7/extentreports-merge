package tech.grasshopper.combiner.util;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import tech.grasshopper.combiner.exception.CombinerException;
import tech.grasshopper.combiner.media.MediaHandler;
import tech.grasshopper.combiner.pojo.Test;

public class FileOperations {

	private static Gson gson = new GsonBuilder().create();

	public static Test[] readTestsFromJSONReport(Path path) {
		try {
			return gson.fromJson(Files.newBufferedReader(path), Test[].class);
		} catch (Exception e) {
			throw new CombinerException("Error in reading tests from JSON report.");
		}
	}

	public static void writeTestsToJSONReport(List<Test> tests, Path path) {
		try (FileWriter writer = new FileWriter(path.toFile())) {
			gson.toJson(tests, writer);
		} catch (Exception e) {
			throw new CombinerException("Error in writing tests to JSON report.");
		}
	}

	public static void writeTestsToJSONReport(Test[] tests, Path path) {
		try (FileWriter writer = new FileWriter(path.toFile())) {
			gson.toJson(tests, writer);
		} catch (Exception e) {
			throw new CombinerException("Error in writing tests to JSON report.");
		}
	}

	public static void createMediaFolder(String combinedReportPath) {
		if (Files.exists(Paths.get(combinedReportPath + "/" + MediaHandler.MEDIA_FOLDER)))
			return;

		try {
			Path mediaFolder = Paths.get(combinedReportPath + "/" + MediaHandler.MEDIA_FOLDER);
			Files.createDirectories(mediaFolder);
		} catch (IOException e) {
			throw new CombinerException("Error in creating media folder.");
		}
	}
}
