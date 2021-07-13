package tech.grasshopper.combiner.pojo;

import lombok.Data;

@Data
public class ExceptionInfo {

	private String name;
	private Throwable exception;
	private String stackTrace;
}
