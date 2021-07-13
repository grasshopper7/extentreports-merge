package tech.grasshopper.combiner.options;

public enum ConfigType {

	JSON, XML, NONE;

	public static ConfigType createConfigType(String configType) {
		if (configType == null || configType.isEmpty())
			return ConfigType.NONE;

		try {
			return ConfigType.valueOf(configType.toUpperCase());
		} catch (IllegalArgumentException e) {
			return ConfigType.NONE;
		}
	}
}
