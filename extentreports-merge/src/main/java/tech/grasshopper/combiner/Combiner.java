package tech.grasshopper.combiner;

import tech.grasshopper.combiner.options.ArrayOptionsInputType;
import tech.grasshopper.combiner.options.CombinerOptions;
import tech.grasshopper.combiner.options.PojoOptions;
import tech.grasshopper.combiner.options.PojoOptionsInputType;

public class Combiner {

	public static void main(PojoOptions pojoOptions) {
		process(PojoOptionsInputType.builder().options(pojoOptions).build().generateOptions());
	}

	public static void main(String[] args) {
		process(ArrayOptionsInputType.builder().options(args).build().generateOptions());
	}

	private static void process(CombinerOptions options) {
		options.getReportType().generateReport();
	}
}
