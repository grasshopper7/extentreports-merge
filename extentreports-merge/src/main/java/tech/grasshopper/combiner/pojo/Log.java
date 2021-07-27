package tech.grasshopper.combiner.pojo;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.aventstack.extentreports.Status;

import lombok.Data;

@Data
public class Log {

	private Date timestamp;
	private Status status = Status.PASS;
	private String details;
	private Integer seq = -1;
	private final Map<String, Object> infoMap = new HashMap<>();
	private Media media;
	private ExceptionInfo exception;
}
