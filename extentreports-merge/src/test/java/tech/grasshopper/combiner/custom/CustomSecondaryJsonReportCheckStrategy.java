package tech.grasshopper.combiner.custom;

import java.util.List;

import tech.grasshopper.combiner.pojo.Test;
import tech.grasshopper.combiner.strategy.check.SecondaryJsonReportCheckStrategy;

public class CustomSecondaryJsonReportCheckStrategy extends SecondaryJsonReportCheckStrategy {

	public boolean executeCheck(List<Test> test) {
		System.out.println("CustomSecondaryJsonReportCheckStrategy - executeCheck");
		return true;
	}
}
