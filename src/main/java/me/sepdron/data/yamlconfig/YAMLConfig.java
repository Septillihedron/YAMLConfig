package me.sepdron.data.yamlconfig;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import org.apache.commons.text.StringEscapeUtils;
import org.yaml.snakeyaml.Yaml;

import me.sepdron.utils.CollectionUtils;

/*
TODO:
- add Number getters
- add YAMLConfig getters
*/

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
	public void save(File file) throws IOException {
		try (var writer = new BufferedWriter(new FileWriter(file, Charset.forName("UTF-8")))) {
			writer.append(toString());
			writer.append("\r\n");
			writer.flush();
			writer.close();
		} catch (IOException e) {
			throw e;
		}
	}

	@Override
	public String toString() {
		if (isMap) return mapToString();
		return listToString();
	}
	private String mapToString() {
		if (mapValues.size() == 0) return "{}";
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, Object> item : mapValues.entrySet()) {
			sb.append(item.getKey());
			sb.append(": ");
			String itemString = toYAMLString(item.getValue());
			sb.append(itemString);
			sb.append("\r\n");
		}
		sb.delete(sb.length()-2, sb.length());
		return sb.toString();
	}
	private String listToString() {
		if (listValues.size() == 0) return "[]";
		StringBuilder sb = new StringBuilder();
		for (Object item : listValues) {
			sb.append("- ");
			String itemString = toYAMLString(item);
			sb.append(itemString);
			sb.append("\r\n");
		}
		sb.delete(sb.length()-2, sb.length());
		return sb.toString();
	}
	private String toYAMLString(Object o) {
		if (o instanceof String) 
			return "\"" + StringEscapeUtils.escapeJson((String) o) + "\"";
		if (o instanceof YAMLConfig) return indent("\r\n" + o.toString());
		if (o == null) return "null";
		return o.toString();
	}
	private static String indent(String str) {
		return str.replaceAll("\r\n", "\r\n  ");
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
	public Object getObjectOrDefault(String key, Object defaultValue) throws WrongMappingException {
		if (!isMap) throw WrongMappingException.NOT_A_MAP_EXCEPTION;
		return mapValues.containsKey(key)? mapValues.get(key) : defaultValue;
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
	public Object getObjectOrDefault(int index, Object defaultValue) throws WrongMappingException {
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
	public String getStringOrDefault(String key, String defaultValue) throws WrongMappingException {
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
	public String getStringOrDefault(int index, String defaultValue) throws WrongMappingException {
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

	private boolean isNumber(Object o) {
		return o instanceof Number;
	}

	private boolean isInt(Object o) {
		return o instanceof Integer;
	}
	public int getInt(String key) throws WrongMappingException {
		Object o = getObject(key);
		if (isInt(o)) return (Integer) o;
		return 0;
	}
	public int getIntOrDefault(String key, int defaultValue) throws WrongMappingException {
		Object o = getObject(key);
		if (isInt(o)) return (Integer) o;
		return defaultValue;
	}
	public int getInt(String key, boolean required) throws WrongMappingException, NoneOfTypeException {
		Object o = getObject(key, required);
		if (isInt(o)) return (Integer) o;
		else if (required) throw NoneOfTypeException.createTypeException(getPathOf(key), "int");
		return 0;
	}
	public int getInt(int index) throws WrongMappingException {
		Object o = getObject(index);
		if (isInt(o)) return (Integer) o;
		return 0;
	}
	public int getIntOrDefault(int index, int defaultValue) throws WrongMappingException {
		Object o = getObject(index);
		if (isInt(o)) return (Integer) o;
		return defaultValue;
	}
	public int getInt(int index, boolean required) throws WrongMappingException, NoneOfTypeException {
		Object o = getObject(index, required);
		if (isInt(o)) return (Integer) o;
		else if (required) throw NoneOfTypeException.createTypeException(getPathOf(index), "int");
		return 0;
	}

	private boolean isLong(Object o) {
		return isInt(o) || o instanceof Long;
	}
	private long toLong(Object o) {
		return ((Number) o).longValue();
	}
	public long getLong(String key) throws WrongMappingException {
		Object o = getObject(key);
		if (isLong(o)) return toLong(o);
		return 0;
	}
	public long getLongOrDefault(String key, long defaultValue) throws WrongMappingException {
		Object o = getObject(key);
		if (isLong(o)) return toLong(o);
		return defaultValue;
	}
	public long getLong(String key, boolean required) throws WrongMappingException, NoneOfTypeException {
		Object o = getObject(key, required);
		if (isLong(o)) return toLong(o);
		else if (required) throw NoneOfTypeException.createTypeException(getPathOf(key), "int");
		return 0;
	}
	public long getLong(int index) throws WrongMappingException {
		Object o = getObject(index);
		if (isLong(o)) return toLong(o);
		return 0;
	}
	public long getLongOrDefault(int index, long defaultValue) throws WrongMappingException {
		Object o = getObject(index);
		if (isLong(o)) return toLong(o);
		return defaultValue;
	}
	public long getLong(int index, boolean required) throws WrongMappingException, NoneOfTypeException {
		Object o = getObject(index, required);
		if (isLong(o)) return toLong(o);
		else if (required) throw NoneOfTypeException.createTypeException(getPathOf(index), "int");
		return 0;
	}

	private boolean isBigInteger(Object o) {
		return isLong(o) || o instanceof BigInteger;
	}
	public BigInteger getBigInteger(String key) throws WrongMappingException {
		Object o = getObject(key);
		if (isLong(o)) return BigInteger.valueOf(toLong(o));
		else if (o instanceof BigInteger) return (BigInteger) o;
		return BigInteger.ZERO;
	}
	public BigInteger getBigIntegerOrDefault(String key, BigInteger defaultValue) throws WrongMappingException {
		Object o = getObject(key);
		if (isLong(o)) return BigInteger.valueOf(toLong(o));
		else if (o instanceof BigInteger) return (BigInteger) o;
		return defaultValue;
	}
	public BigInteger getBigInteger(String key, boolean required) throws WrongMappingException, NoneOfTypeException {
		Object o = getObject(key, required);
		if (isLong(o)) return BigInteger.valueOf(toLong(o));
		else if (o instanceof BigInteger) return (BigInteger) o;
		else if (required) throw NoneOfTypeException.createTypeException(getPathOf(key), "BigInteger");
		return BigInteger.ZERO;
	}
	public BigInteger getBigInteger(int index) throws WrongMappingException {
		Object o = getObject(index);
		if (isLong(o)) return BigInteger.valueOf(toLong(o));
		else if (o instanceof BigInteger) return (BigInteger) o;
		return BigInteger.ZERO;
	}
	public BigInteger getBigIntegerOrDefault(int index, BigInteger defaultValue) throws WrongMappingException {
		Object o = getObject(index);
		if (isLong(o)) return BigInteger.valueOf(toLong(o));
		else if (o instanceof BigInteger) return (BigInteger) o;
		return defaultValue;
	}
	public BigInteger getBigInteger(int index, boolean required) throws WrongMappingException, NoneOfTypeException {
		Object o = getObject(index, required);
		if (isLong(o)) return BigInteger.valueOf(toLong(o));
		else if (o instanceof BigInteger) return (BigInteger) o;
		else if (required) throw NoneOfTypeException.createTypeException(getPathOf(index), "BigInteger");
		return BigInteger.ZERO;
	}

	private float toFloat(Object o) {
		return ((Number) o).floatValue();
	}
	public float getFloat(String key) throws WrongMappingException {
		Object o = getObject(key);
		if (isNumber(o)) return toFloat(o);
		return 0;
	}
	public float getFloatOrDefault(String key, float defaultValue) throws WrongMappingException {
		Object o = getObject(key);
		if (isNumber(o)) return toFloat(o);
		return defaultValue;
	}
	public float getFloat(String key, boolean required) throws WrongMappingException, NoneOfTypeException {
		Object o = getObject(key, required);
		if (isNumber(o)) return toFloat(o);
		else if (required) throw NoneOfTypeException.createTypeException(getPathOf(key), "float");
		return 0;
	}
	public float getFloat(int index) throws WrongMappingException {
		Object o = getObject(index);
		if (isNumber(o)) return toFloat(o);
		return 0;
	}
	public float getFloatOrDefault(int index, float defaultValue) throws WrongMappingException {
		Object o = getObject(index);
		if (isNumber(o)) return toFloat(o);
		return defaultValue;
	}
	public float getFloat(int index, boolean required) throws WrongMappingException, NoneOfTypeException {
		Object o = getObject(index, required);
		if (isNumber(o)) return toFloat(o);
		else if (required) throw NoneOfTypeException.createTypeException(getPathOf(index), "float");
		return 0;
	}

	private double toDouble(Object o) {
		return ((Number) o).doubleValue();
	}
	public double getDouble(String key) throws WrongMappingException {
		Object o = getObject(key);
		if (isNumber(o)) return toDouble(o);
		return 0;
	}
	public double getDoubleOrDefault(String key, double defaultValue) throws WrongMappingException {
		Object o = getObject(key);
		if (isNumber(o)) return toDouble(o);
		return defaultValue;
	}
	public double getDouble(String key, boolean required) throws WrongMappingException, NoneOfTypeException {
		Object o = getObject(key, required);
		if (isNumber(o)) return toDouble(o);
		else if (required) throw NoneOfTypeException.createTypeException(getPathOf(key), "double");
		return 0;
	}
	public double getDouble(int index) throws WrongMappingException {
		Object o = getObject(index);
		if (isNumber(o)) return toDouble(o);
		return 0;
	}
	public double getDoubleOrDefault(int index, double defaultValue) throws WrongMappingException {
		Object o = getObject(index);
		if (isNumber(o)) return toDouble(o);
		return defaultValue;
	}
	public double getDouble(int index, boolean required) throws WrongMappingException, NoneOfTypeException {
		Object o = getObject(index, required);
		if (isNumber(o)) return toDouble(o);
		else if (required) throw NoneOfTypeException.createTypeException(getPathOf(index), "double");
		return 0;
	}

	private boolean isBoolean(Object o) {
		return o instanceof Boolean;
	}
	public boolean getBoolean(String key) throws WrongMappingException {
		Object o = getObject(key);
		if (isBoolean(o)) return (Boolean) o;
		return false;
	}
	public boolean getBooleanOrDefault(String key, boolean defaultValue) throws WrongMappingException {
		Object o = getObject(key);
		if (isBoolean(o)) return (Boolean) o;
		return defaultValue;
	}
	public boolean getBoolean(String key, boolean required) throws WrongMappingException, NoneOfTypeException {
		Object o = getObject(key, required);
		if (isBoolean(o)) return (Boolean) o;
		else if (required) throw NoneOfTypeException.createTypeException(getPathOf(key), "boolean");
		return false;
	}
	public boolean getBoolean(int index) throws WrongMappingException {
		Object o = getObject(index);
		if (isBoolean(o)) return (Boolean) o;
		return false;
	}
	public boolean getBooleanOrDefault(int index, boolean defaultValue) throws WrongMappingException {
		Object o = getObject(index);
		if (isBoolean(o)) return (Boolean) o;
		return defaultValue;
	}
	public boolean getBoolean(int index, boolean required) throws WrongMappingException, NoneOfTypeException {
		Object o = getObject(index, required);
		if (isBoolean(o)) return (Boolean) o;
		else if (required) throw NoneOfTypeException.createTypeException(getPathOf(index), "boolean");
		return false;
	}

}
