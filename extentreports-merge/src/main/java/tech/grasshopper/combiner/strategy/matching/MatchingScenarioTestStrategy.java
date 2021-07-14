package tech.grasshopper.combiner.strategy.matching;

import com.aventstack.extentreports.Status;

import tech.grasshopper.combiner.exception.CombinerException;
import tech.grasshopper.combiner.pojo.Test;

public interface MatchingScenarioTestStrategy {

	public static MatchingScenarioTestStrategy createMatchingStrategy(String strategy) {

		if (null == strategy || strategy.equals("") || strategy.equalsIgnoreCase(LaterScenarioTestStrategy.NAME))
			return new LaterScenarioTestStrategy();

		if (strategy.equalsIgnoreCase(EarlierScenarioTestStrategy.NAME))
			return new EarlierScenarioTestStrategy();

		if (strategy.equalsIgnoreCase(LowStatusScenarioTestStrategy.NAME))
			return new LowStatusScenarioTestStrategy();

		if (strategy.equalsIgnoreCase(HighStatusScenarioTestStrategy.NAME))
			return new HighStatusScenarioTestStrategy();

		if (strategy.equalsIgnoreCase(RerunPassScenarioTestStrategy.NAME))
			return new RerunPassScenarioTestStrategy();

		try {
			return (MatchingScenarioTestStrategy) Class.forName(strategy).newInstance();
		} catch (Exception e) {
			throw new CombinerException("There is no matching scenario test strategy availble for - " + strategy + ".");
		}
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
