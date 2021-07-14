package tech.grasshopper.combiner.strategy.extra;

import java.util.List;

import tech.grasshopper.combiner.exception.CombinerException;
import tech.grasshopper.combiner.pojo.Test;

public interface ExtraScenarioTestStrategy {

	public static ExtraScenarioTestStrategy createExtraStrategy(String strategy) {

		if (null == strategy || strategy.equals("") || strategy.equalsIgnoreCase(AddExtraScenarioTestStrategy.NAME))
			return new AddExtraScenarioTestStrategy();

		if (strategy.equalsIgnoreCase(ErrorExtraScenarioTestStrategy.NAME))
			return new ErrorExtraScenarioTestStrategy();

		try {
			return (ExtraScenarioTestStrategy) Class.forName(strategy).newInstance();
		} catch (Exception e) {
			throw new CombinerException("There is no extra scenario test strategy available for - '" + strategy + "'.");
		}
	}

	void processExtraSecondaryFeatureTest(List<Test> primaryFeatureTests, Test secondaryFeatureTest);

	void processExtraSecondaryScenarioOutlineTest(Test primaryFeatureTest, Test secondaryScenarioOutlineTest);

	void processExtraSecondaryScenarioTest(Test primaryFeatureOrScenarioOutlineTest, Test secondaryScenarioTest);

	public class AddExtraScenarioTestStrategy implements ExtraScenarioTestStrategy {
		public static final String NAME = "ADD";

		@Override
		public void processExtraSecondaryFeatureTest(List<Test> primaryFeatureTests, Test secondaryFeatureTest) {
			primaryFeatureTests.add(secondaryFeatureTest);
		}

		@Override
		public void processExtraSecondaryScenarioOutlineTest(Test primaryFeatureTest,
				Test secondaryScenarioOutlineTest) {
			primaryFeatureTest.getChildren().add(secondaryScenarioOutlineTest);
		}

		@Override
		public void processExtraSecondaryScenarioTest(Test primaryFeatureOrScenarioOutlineTest,
				Test secondaryScenarioTest) {
			primaryFeatureOrScenarioOutlineTest.getChildren().add(secondaryScenarioTest);
		}
	}

	public class ErrorExtraScenarioTestStrategy implements ExtraScenarioTestStrategy {
		public static final String NAME = "ERROR";

		@Override
		public void processExtraSecondaryFeatureTest(List<Test> primaryFeatureTests, Test secondaryFeatureTest) {
			throw new CombinerException("Rerun feature not present in master JSON report.");
		}

		@Override
		public void processExtraSecondaryScenarioOutlineTest(Test primaryFeatureTest,
				Test secondaryScenarioOutlineTest) {
			throw new CombinerException("Rerun scenario outline not present in master JSON report.");
		}

		@Override
		public void processExtraSecondaryScenarioTest(Test primaryFeatureOrScenarioOutlineTest,
				Test secondaryScenarioTest) {
			throw new CombinerException("Rerun scenario not present in master JSON report.");
		}
	}
}
