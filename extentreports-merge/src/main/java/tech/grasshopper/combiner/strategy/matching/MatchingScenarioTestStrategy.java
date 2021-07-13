package tech.grasshopper.combiner.strategy.matching;

import com.aventstack.extentreports.Status;

import tech.grasshopper.combiner.exception.CombinerException;
import tech.grasshopper.combiner.pojo.Test;

public interface MatchingScenarioTestStrategy {

	public static MatchingScenarioTestStrategy createStrategy(String strategy) {

		if (strategy.equalsIgnoreCase("earlier"))
			return new EarlierScenarioTestStrategy();

		if (strategy.equalsIgnoreCase("later"))
			return new LaterScenarioTestStrategy();

		if (strategy.equalsIgnoreCase("lowstatus"))
			return new LowStatusScenarioTestStrategy();

		if (strategy.equalsIgnoreCase("highstatus"))
			return new HighStatusScenarioTestStrategy();

		if (strategy.equalsIgnoreCase("rerunpass"))
			return new RerunPassScenarioTestStrategy();

		throw new CombinerException(
				"There is no matching in built report for the provided matching scenario strategy - " + strategy + ".");
	}

	Test select(Test primaryScenarioTest, Test secondaryScenarioTest);

	public class EarlierScenarioTestStrategy implements MatchingScenarioTestStrategy {
		public static final String NAME = "EARLIER";

		@Override
		public Test select(Test primaryScenarioTest, Test secondaryScenarioTest) {
			return secondaryScenarioTest.getStartTime().before(primaryScenarioTest.getStartTime())
					? secondaryScenarioTest
					: primaryScenarioTest;
		}
	}

	public class LaterScenarioTestStrategy implements MatchingScenarioTestStrategy {
		public static final String NAME = "LATER";

		@Override
		public Test select(Test primaryScenarioTest, Test secondaryScenarioTest) {
			return secondaryScenarioTest.getStartTime().after(primaryScenarioTest.getStartTime())
					? secondaryScenarioTest
					: primaryScenarioTest;
		}
	}

	public class HighStatusScenarioTestStrategy implements MatchingScenarioTestStrategy {
		public static final String NAME = "HIGHSTATUS";

		@Override
		public Test select(Test primaryScenarioTest, Test secondaryScenarioTest) {
			return secondaryScenarioTest.getStatus().getLevel() > primaryScenarioTest.getStatus().getLevel()
					? secondaryScenarioTest
					: primaryScenarioTest;
		}
	}

	public class LowStatusScenarioTestStrategy implements MatchingScenarioTestStrategy {
		public static final String NAME = "LOWSTATUS";

		@Override
		public Test select(Test primaryScenarioTest, Test secondaryScenarioTest) {
			return secondaryScenarioTest.getStatus().getLevel() < primaryScenarioTest.getStatus().getLevel()
					? secondaryScenarioTest
					: primaryScenarioTest;
		}
	}

	public class RerunPassScenarioTestStrategy implements MatchingScenarioTestStrategy {
		public static final String NAME = "PASSSTATUS";

		@Override
		public Test select(Test primaryScenarioTest, Test secondaryScenarioTest) {
			return (primaryScenarioTest.getStatus() == Status.FAIL && secondaryScenarioTest.getStatus() == Status.PASS)
					? secondaryScenarioTest
					: primaryScenarioTest;
		}
	}
}
