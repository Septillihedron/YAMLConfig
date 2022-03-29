package me.sepdron.data.yamlconfig;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

import me.sepdron.utils.CollectionUtils;

public class YAMLConfig {

	private final YAMLConfig parent;
	private final String path;

	private Map<String, Object> mapValues;
	private List<Object> listValues;
	private final boolean isMap;
	private final boolean isList;

	public YAMLConfig(YAMLConfig parent, String path, Map<?, ?> data) {
		this.parent = parent;
		this.path = path;

		mapValues = CollectionUtils.getMapAs(data, String.class, Object.class);
		mapValues.replaceAll((key, value) -> convert(key, value));
		isMap = true;
		isList = false;
	}
	public YAMLConfig(YAMLConfig parent, String path, List<?> data) {
		this.parent = parent;
		this.path = path;

		listValues = CollectionUtils.getListAs(data, Object.class);
		for (int i=0; i<listValues.size(); i++) {
			listValues.set(i, convert(i, listValues.get(i)));
		}
		isMap = false;
		isList = true;
	}

	private Object convert(String name, Object obj) {
		if (obj instanceof Map<?, ?>) 
			return new YAMLConfig(this, getPathOf(name), (Map<?, ?>) obj);
		if (obj instanceof List<?>) 
			return new YAMLConfig(this, getPathOf(name), (List<?>) obj);
		return obj;
	}
	private Object convert(int index, Object obj) {
		if (obj instanceof Map<?, ?>) 
			return new YAMLConfig(this, getPathOf(index), (Map<?, ?>) obj);
		if (obj instanceof List<?>) 
			return new YAMLConfig(this, getPathOf(index), (List<?>) obj);
		return obj;
	}

	private String getPathOf(String key) {
		return String.format("%s%s%s", path, ((parent == null)? ": " : "."), key);
	}
	private String getPathOf(int index) {
		return String.format("%s[%d]", path, index);
	}

	public static YAMLConfig load(File file) throws IOException {
		try (var reader = new BufferedReader(new FileReader(file, Charset.forName("UTF-8")))) {
			Yaml loader = new Yaml();
			Object values = loader.load(reader);

			if (values instanceof Map<?, ?>) 
				return new YAMLConfig(null, file.getPath(), (Map<?, ?>) values);
			if (values instanceof List<?>) 
				return new YAMLConfig(null, file.getPath(), (List<?>) values);
			return null;

		} catch (IOException e) {
			throw e;
		}
	}

	public YAMLConfig getParent() {
		return parent;
	}
	public String getPath() {
		return path;
	}
	public Map<String, Object> getMapValues() {
		return mapValues;
	}
	public List<Object> getListValues() {
		return listValues;
	}

	public boolean isMap() {
		return isMap;
	}
	public boolean isList() {
		return isList;
	}

	private boolean inRange(int index) {
		return (index >= 0 && index < listValues.size());
	}

	public Object getObject(String key) throws WrongMappingException {
		if (!isMap) throw WrongMappingException.NOT_A_MAP_EXCEPTION;
		return mapValues.get(key);
	}
	public Object getObject(String key, Object defaultValue) throws WrongMappingException {
		if (!isMap) throw WrongMappingException.NOT_A_MAP_EXCEPTION;
		return mapValues.getOrDefault(key, defaultValue);
	}
	public Object getObject(String key, boolean required) throws WrongMappingException, NoneOfTypeException {
		if (!isMap) throw WrongMappingException.NOT_A_MAP_EXCEPTION;
		if (required && !mapValues.containsKey(key)) 
			throw NoneOfTypeException.createNotExistException(getPathOf(key));
		return mapValues.get(key);
	}
	public Object getObject(int index) throws WrongMappingException {
		if (!isList) throw WrongMappingException.NOT_A_LIST_EXCEPTION;
		return inRange(index)? listValues.get(index) : null;
	}
	public Object getObject(int index, Object defaultValue) throws WrongMappingException {
		if (!isList) throw WrongMappingException.NOT_A_LIST_EXCEPTION;
		return inRange(index)? listValues.get(index) : defaultValue;
	}
	public Object getObject(int index, boolean required) throws WrongMappingException, NoneOfTypeException {
		if (!isList) throw WrongMappingException.NOT_A_LIST_EXCEPTION;
		if (required && !inRange(index)) 
			throw NoneOfTypeException.createNotExistException(getPathOf(index));
		return inRange(index)? listValues.get(index) : null;
	}

	public String getString(String key) throws WrongMappingException {
		Object o = getObject(key);
		if (o instanceof String) return (String) o;
		return null;
	}
	public String getString(String key, String defaultValue) throws WrongMappingException {
		Object o = getObject(key);
		if (o instanceof String) return (String) o;
		return defaultValue;
	}
	public String getString(String key, boolean required) throws WrongMappingException, NoneOfTypeException {
		Object o = getObject(key, required);
		if (o instanceof String) return (String) o;
		else if (required) throw NoneOfTypeException.createTypeException(getPathOf(key), "String");
		return null;
	}
	public String getString(int index) throws WrongMappingException {
		Object o = getObject(index);
		if (o instanceof String) return (String) o;
		return null;
	}
	public String getString(int index, String defaultValue) throws WrongMappingException {
		Object o = getObject(index);
		if (o instanceof String) return (String) o;
		return defaultValue;
	}
	public String getString(int index, boolean required) throws WrongMappingException, NoneOfTypeException {
		Object o = getObject(index, required);
		if (o instanceof String) return (String) o;
		else if (required) throw NoneOfTypeException.createTypeException(getPathOf(index), "String");
		return null;
	}

	public int getInt(String key) throws WrongMappingException {
		Object o = getObject(key);
		if (o instanceof Integer) return (Integer) o;
		return 0;
	}
	public int getInt(String key, int defaultValue) throws WrongMappingException {
		Object o = getObject(key);
		if (o instanceof Integer) return (Integer) o;
		return defaultValue;
	}
	public int getInt(String key, boolean required) throws WrongMappingException, NoneOfTypeException {
		Object o = getObject(key, required);
		if (o instanceof Integer) return (Integer) o;
		else if (required) throw NoneOfTypeException.createTypeException(getPathOf(key), "int");
		return 0;
	}
	public int getInt(int index) throws WrongMappingException {
		Object o = getObject(index);
		if (o instanceof Integer) return (Integer) o;
		return 0;
	}
	public int getInt(int index, int defaultValue) throws WrongMappingException {
		Object o = getObject(index);
		if (o instanceof Integer) return (Integer) o;
		return defaultValue;
	}
	public int getInt(int index, boolean required) throws WrongMappingException, NoneOfTypeException {
		Object o = getObject(index, required);
		if (o instanceof Integer) return (Integer) o;
		else if (required) throw NoneOfTypeException.createTypeException(getPathOf(index), "int");
		return 0;
	}

}
