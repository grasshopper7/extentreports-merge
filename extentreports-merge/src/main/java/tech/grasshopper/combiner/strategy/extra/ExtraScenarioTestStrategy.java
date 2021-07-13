package tech.grasshopper.combiner.strategy.extra;

import java.util.List;

import tech.grasshopper.combiner.exception.CombinerException;
import tech.grasshopper.combiner.pojo.Test;

public interface ExtraScenarioTestStrategy {

	void processExtraSecondaryFeatureTest(List<Test> primaryFeatureTests, Test secondaryFeatureTest);

	void processExtraSecondaryScenarioOutlineTest(Test primaryFeatureTest, Test secondaryScenarioOutlineTest);

	void processExtraSecondaryScenarioTest(Test primaryFeatureOrScenarioOutlineTest, Test secondaryScenarioTest);

	public class AddExtraScenarioTestStrategy implements ExtraScenarioTestStrategy {
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
