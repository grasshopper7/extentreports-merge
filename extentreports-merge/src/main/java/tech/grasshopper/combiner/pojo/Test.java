package tech.grasshopper.combiner.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.model.Author;
import com.aventstack.extentreports.model.Category;
import com.aventstack.extentreports.model.Device;

import lombok.Data;

@Data
public class Test {

	private boolean useNaturalConf = true;
	private Date startTime;
	private Date endTime;
	private Status status = Status.PASS;
	private Integer level = 0;
	private boolean isLeaf = true;
	private String name;
	private String description;
	private String bddType;

	private Map<String, Object> infoMap = new HashMap<>();
	private List<Test> children = new ArrayList<>();
	private List<Log> logs = new ArrayList<>();
	private List<Media> media = new ArrayList<>();
	private List<ExceptionInfo> exceptions = new ArrayList<>();
	private Set<Author> authorSet = new HashSet<>();
	private Set<Category> categorySet = new HashSet<>();
	private Set<Device> deviceSet = new HashSet<>();
	private List<Log> generatedLog = new ArrayList<>();
}
