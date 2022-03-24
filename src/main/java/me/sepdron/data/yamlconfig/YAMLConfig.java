package me.sepdron.data.yamlconfig;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

import me.sepdron.utils.CollectionUtils;

public class YAMLConfig {

	private Map<String, Object> mapValues;
	private List<Object> listValues;

	public YAMLConfig(Map<?, ?> map) {
		mapValues = new HashMap<String, Object>(CollectionUtils.getMapAs(map, String.class, Object.class));
		mapValues.replaceAll((key, value) -> convert(value));
	}
	public YAMLConfig(List<?> map) {
		listValues = new ArrayList<Object>(CollectionUtils.getListAs(map, Object.class));
		listValues.replaceAll((value) -> convert(value));
	}

	private static Object convert(Object obj) {
		if (obj instanceof Map<?, ?>) return new YAMLConfig((Map<?, ?>) obj);
		if (obj instanceof List<?>) return new YAMLConfig((List<?>) obj);
		return obj;
	}

	public static YAMLConfig load(File file) throws IOException {
		try (var reader = new BufferedReader(new FileReader(file, Charset.forName("UTF-8")))) {
			Yaml loader = new Yaml();
			Object values = loader.load(reader);

			if (values instanceof Map<?, ?>) return new YAMLConfig((Map<?, ?>) values);
			if (values instanceof List<?>) return new YAMLConfig((List<?>) values);
			return null;

		} catch (IOException e) {
			throw e;
		}
	}

}
