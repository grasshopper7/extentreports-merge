package tech.grasshopper.combiner.custom;

import tech.grasshopper.combiner.pojo.Test;
import tech.grasshopper.combiner.strategy.matching.MatchingScenarioTestStrategy;

public class CustomMatchingScenarioTestStrategy implements MatchingScenarioTestStrategy {

	@Override
	public Test selectScenarioTest(Test primaryScenarioTest, Test secondaryScenarioTest) {
		System.out.println("CustomMatchingScenarioTestStrategy - selectScenarioTest");
		return secondaryScenarioTest.getStartTime().before(primaryScenarioTest.getStartTime())
				? secondaryScenarioTest
				: primaryScenarioTest;
	}

}
