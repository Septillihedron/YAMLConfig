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

public abstract class YamlSection {

	private final YamlSection parent;
	private final String path;

	protected YamlSection(YamlSection parent, String path) {
		this.parent = parent;
		this.path = path;
	}

	public static YamlSection load(File file) throws IOException {
		try (var reader = new BufferedReader(new FileReader(file, Charset.forName("UTF-8")))) {
			Yaml loader = new Yaml();
			Object values = loader.load(reader);

			if (values instanceof Map) 
				return new YamlMap(null, file.getPath(), (Map<?,?>) values);
			if (values instanceof List) 
				return new YamlList(null, file.getPath(), (List<?>) values);
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

	public abstract String toString();
	protected static String ObjectToString(Object o) {
		if (o instanceof String) 
			return "\"" + StringEscapeUtils.escapeJson((String) o) + "\"";
		if (o instanceof YamlSection) return indent("\r\n" + o.toString());
		if (o == null) return "null";
		return o.toString();
	}
	protected static String indent(String str) {
		return str.replaceAll("\r\n", "\r\n  ");
	}
	protected String getPathOf(String name) {
		return path + ((parent == null)? ": " : ".") + name;
	}
	protected String getPathOf(Object o) {
		return getPathOf(o.toString());
	}
	protected Object convert(String name, Object obj) {
		if (obj instanceof Map) 
			return new YamlMap(this, getPathOf(name), (Map<?,?>) obj);
		if (obj instanceof List) 
			return new YamlList(this, getPathOf(name), (List<?>) obj);
		return obj;
	}

	public YamlSection getParent() {
		return parent;
	}
	public YamlSection getRootSection() {
		return (parent == null)? this : parent.getRootSection();
	}
	public String getPath() {
		return path;
	}

	public abstract boolean contains(Object key);

	public abstract Object getObject(Object key);
	public Object getObjectOrDefault(Object key, Object defaultValue) {
		return contains(key)? getObject(key) : defaultValue;
	}
	public Object getObject(Object key, boolean required) throws NoneOfTypeException {
		if (!contains(key)) {
			if (required) throw NoneOfTypeException.createNotExistException(getPathOf(key));
			return null;
		}
		return getObject(key);
	}

	protected static boolean isString(Object o) {
		return o instanceof String;
	}
	protected static String toString(Object o) {
		return (String) o;
	}
	public String getString(Object key) {
		Object o = getObject(key);
		if (isString(o)) return toString(o);
		return null;
	}
	public String getStringOrDefault(Object key, String defaultValue) {
		Object o = getObject(key);
		if (isString(o)) return toString(o);
		return defaultValue;
	}
	public String getString(Object key, boolean required) throws NoneOfTypeException {
		Object o = getObject(key);
		if (isString(o)) return toString(o);
		else if (required) throw NoneOfTypeException.createTypeException(getPathOf(key), "string");
		return null;
	}

	protected static boolean isNumber(Object o) {
		return o instanceof Number;
	}
	protected static Number toNumber(Object o) {
		return (Number) o;
	}

	protected static boolean isInt(Object o) {
		return o instanceof Integer;
	}
	protected static int toInt(Object o) {
		return (Integer) o;
	}
	public int getInt(Object key) {
		Object o = getObject(key);
		if (isInt(o)) return toInt(o);
		return 0;
	}
	public int getIntOrDefault(Object key, int defaultValue) {
		Object o = getObject(key);
		if (isInt(o)) return toInt(o);
		return defaultValue;
	}
	public int getInt(Object key, boolean required) throws NoneOfTypeException {
		Object o = getObject(key);
		if (isInt(o)) return toInt(o);
		else if (required) throw NoneOfTypeException.createTypeException(getPathOf(key), "int");
		return 0;
	}

	protected static boolean isLong(Object o) {
		return isInt(o) || o instanceof Long;
	}
	protected static long toLong(Object o) {
		return toNumber(o).longValue();
	}
	public long getLong(Object key) {
		Object o = getObject(key);
		if (isLong(o)) return toLong(o);
		return 0;
	}
	public long getLongOrDefault(Object key, long defaultValue) {
		Object o = getObject(key);
		if (isLong(o)) return toLong(o);
		return defaultValue;
	}
	public long getLong(Object key, boolean required) throws NoneOfTypeException {
		Object o = getObject(key);
		if (isLong(o)) return toLong(o);
		else if (required) throw NoneOfTypeException.createTypeException(getPathOf(key), "long");
		return 0;
	}

	protected static boolean isBigInteger(Object o) {
		return isLong(o) || o instanceof BigInteger;
	}
	protected static BigInteger toBigInteger(Object o) {
		if (isLong(o)) return BigInteger.valueOf(toLong(o));
		else if (o instanceof BigInteger) return (BigInteger) o;
		return null;
	}
	public BigInteger getBigInteger(Object key) {
		Object o = getObject(key);
		if (isBigInteger(o)) return toBigInteger(o);
		return BigInteger.ZERO;
	}
	public BigInteger getBigIntegerOrDefault(Object key, BigInteger defaultValue) {
		Object o = getObject(key);
		if (isBigInteger(o)) return toBigInteger(o);
		return defaultValue;
	}
	public BigInteger getBigInteger(Object key, boolean required) throws NoneOfTypeException {
		Object o = getObject(key);
		if (isBigInteger(o)) return toBigInteger(o);
		else if (required) throw NoneOfTypeException.createTypeException(getPathOf(key), "BigInteger");
		return BigInteger.ZERO;
	}

	protected static float toFloat(Object o) {
		return toNumber(o).floatValue();
	}
	public float getFloat(Object key) {
		Object o = getObject(key);
		if (isNumber(o)) return toFloat(o);
		return 0;
	}
	public float getFloatOrDefault(Object key, float defaultValue) {
		Object o = getObject(key);
		if (isNumber(o)) return toFloat(o);
		return defaultValue;
	}
	public float getFloat(Object key, boolean required) throws NoneOfTypeException {
		Object o = getObject(key);
		if (isNumber(o)) return toFloat(o);
		else if (required) throw NoneOfTypeException.createTypeException(getPathOf(key), "float");
		return 0;
	}

	protected static double toDouble(Object o) {
		return toNumber(o).doubleValue();
	}
	public double getDouble(Object key) {
		Object o = getObject(key);
		if (isNumber(o)) return toDouble(o);
		return 0;
	}
	public double getDoubleOrDefault(Object key, double defaultValue) {
		Object o = getObject(key);
		if (isNumber(o)) return toDouble(o);
		return defaultValue;
	}
	public double getDouble(Object key, boolean required) throws NoneOfTypeException {
		Object o = getObject(key);
		if (isNumber(o)) return toDouble(o);
		else if (required) throw NoneOfTypeException.createTypeException(getPathOf(key), "double");
		return 0;
	}

	protected static boolean isBoolean(Object o) {
		return o instanceof Boolean;
	}
	protected static boolean toBoolean(Object o) {
		return (Boolean) o;
	}
	public boolean getBoolean(Object key) {
		Object o = getObject(key);
		if (isBoolean(o)) return toBoolean(o);
		return false;
	}
	public boolean getBooleanOrDefault(Object key, boolean defaultValue) {
		Object o = getObject(key);
		if (isBoolean(o)) return toBoolean(o);
		return defaultValue;
	}
	public boolean getBoolean(Object key, boolean required) throws NoneOfTypeException {
		Object o = getObject(key);
		if (isBoolean(o)) return toBoolean(o);
		else if (required) throw NoneOfTypeException.createTypeException(getPathOf(key), "boolean");
		return false;
	}

	protected static boolean isConfig(Object o) {
		return o instanceof YamlSection;
	}
	protected static YamlSection toConfig(Object o) {
		return (YamlSection) o;
	}
	public YamlSection getConfig(Object key) {
		Object o = getObject(key);
		if (isConfig(o)) return toConfig(o);
		return null;
	}
	public YamlSection getConfigOrDefault(Object key, YamlSection defaultValue) {
		Object o = getObject(key);
		if (isConfig(o)) return toConfig(o);
		return defaultValue;
	}
	public YamlSection getConfig(Object key, boolean required) throws NoneOfTypeException {
		Object o = getObject(key);
		if (isConfig(o)) return toConfig(o);
		else if (required) throw NoneOfTypeException.createMapException(getPathOf(key));
		return null;
	}

	protected static boolean isMap(Object o) {
		return o instanceof YamlMap;
	}
	protected static YamlMap toMap(Object o) {
		return (YamlMap) o;
	}
	public YamlMap getMap(Object key) {
		Object o = getObject(key);
		if (isMap(o)) return toMap(o);
		return null;
	}
	public YamlMap getMapOrDefault(Object key, YamlMap defaultValue) {
		Object o = getObject(key);
		if (isMap(o)) return toMap(o);
		return defaultValue;
	}
	public YamlMap getMap(Object key, boolean required) throws NoneOfTypeException {
		Object o = getObject(key);
		if (isMap(o)) return toMap(o);
		else if (required) throw NoneOfTypeException.createMapException(getPathOf(key));
		return null;
	}

	protected static boolean isList(Object o) {
		return o instanceof YamlMap;
	}
	protected static YamlList toList(Object o) {
		return (YamlList) o;
	}
	public YamlList getList(Object key) {
		Object o = getObject(key);
		if (isList(o)) return toList(o);
		return null;
	}
	public YamlList getListOrDefault(Object key, YamlList defaultValue) {
		Object o = getObject(key);
		if (isList(o)) return toList(o);
		return defaultValue;
	}
	public YamlList getList(Object key, boolean required) throws NoneOfTypeException {
		Object o = getObject(key);
		if (isList(o)) return toList(o);
		else if (required) throw NoneOfTypeException.createListException(getPathOf(key));
		return null;
	}

}
