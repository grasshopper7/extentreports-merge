package tech.grasshopper.combiner.search;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.aventstack.extentreports.Status;

import tech.grasshopper.combiner.pojo.Test;

public class SearchTest {

	public static Optional<Test> findTestByName(Test[] tests, String name) {
		return findTestByName(Arrays.asList(tests), name);
	}

	public static Optional<Test> findTestByNameAndType(Test[] tests, Test test) {
		return findTestByNameAndType(Arrays.asList(tests), test);
	}

	public static Optional<Test> findTestByName(List<Test> tests, String name) {
		return tests.stream().filter(t -> t.getName().equals(name)).findFirst();
	}

	public static Optional<Test> findTestByNameAndType(List<Test> tests, Test test) {
		return tests.stream()
				.filter(t -> t.getBddType().equals(test.getBddType()) && t.getName().equals(test.getName()))
				.findFirst();
	}

	public static List<Test> findScenarioInFeature(Test featureTest) {
		return featureTest.getChildren().stream().filter(t -> t.getBddType().endsWith(".Scenario"))
				.collect(Collectors.toList());
	}

	public static List<Test> findScenarioOutlineInFeature(Test featureTest) {
		return featureTest.getChildren().stream().filter(t -> t.getBddType().endsWith(".ScenarioOutline"))
				.collect(Collectors.toList());
	}

	public static List<Test> findScenarioInScenarioOutline(Test scenarioOutlineTest) {
		return scenarioOutlineTest.getChildren().stream().filter(t -> t.getBddType().endsWith(".Scenario"))
				.collect(Collectors.toList());
	}

	public static long failFeatureTestStatusCount(List<Test> tests) {
		return tests.stream().filter(t -> t.getStatus() == Status.FAIL).count();
	}

	public static long passScenarioTestStatusCount(List<Test> tests) {
		List<Test> childTest = tests.stream().flatMap(t -> t.getChildren().stream()).collect(Collectors.toList());

		return childTest.stream().filter(t -> t.getBddType().endsWith(".Scenario") && t.getStatus() == Status.PASS)
				.count()
				+ childTest.stream().filter(t -> t.getBddType().endsWith(".ScenarioOutline"))
						.flatMap(t -> t.getChildren().stream()).filter(t -> t.getStatus() == Status.PASS).count();
	}
}
