package tech.grasshopper.combiner.custom;

import java.util.List;

import tech.grasshopper.combiner.pojo.Test;
import tech.grasshopper.combiner.strategy.check.PrimaryJsonReportCheckStrategy;

public class CustomPrimaryJsonReportCheckStrategy extends PrimaryJsonReportCheckStrategy {

	
	public void executeCheck(List<Test> test) {
		System.out.println("CustomPrimaryJsonReportCheckStrategy - executeCheck");
	}
}
