package tech.grasshopper.combiner.custom;

import java.util.List;

import tech.grasshopper.combiner.pojo.Test;
import tech.grasshopper.combiner.strategy.extra.ExtraScenarioTestStrategy;

public class CustomExtraScenarioTestStrategy implements ExtraScenarioTestStrategy {

	@Override
	public void processExtraSecondaryFeatureTest(List<Test> primaryFeatureTests, Test secondaryFeatureTest) {
		System.out.println("CustomExtraScenarioTestStrategy - processExtraSecondaryFeatureTest");
		primaryFeatureTests.add(secondaryFeatureTest);
	}

	@Override
	public void processExtraSecondaryScenarioOutlineTest(Test primaryFeatureTest, Test secondaryScenarioOutlineTest) {
		System.out.println("CustomExtraScenarioTestStrategy - processExtraSecondaryScenarioOutlineTest");
		primaryFeatureTest.getChildren().add(secondaryScenarioOutlineTest);
	}

	@Override
	public void processExtraSecondaryScenarioTest(Test primaryFeatureOrScenarioOutlineTest,
			Test secondaryScenarioTest) {
		System.out.println("CustomExtraScenarioTestStrategy - processExtraSecondaryScenarioTest");
		primaryFeatureOrScenarioOutlineTest.getChildren().add(secondaryScenarioTest);
	}
}
