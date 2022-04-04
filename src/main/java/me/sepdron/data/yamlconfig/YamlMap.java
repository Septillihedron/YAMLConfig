package me.sepdron.data.yamlconfig;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import me.sepdron.utils.CollectionUtils;

public class YamlMap extends YamlSection implements Map<String, Object> {

	private Map<String, Object> data;

	public YamlMap(YamlSection parent, String path, Map<?, ?> data) {
		super(parent, path);

		this.data = CollectionUtils.getMapAs(data, String.class, Object.class);
		this.data.replaceAll((key, value) -> convert(key, value));
	}

	@Override
	public String toString() {
		if (data.size() == 0) return "{}";
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, Object> item : data.entrySet()) {
			sb.append(item.getKey());
			sb.append(": ");
			String itemString = ObjectToString(item.getValue());
			sb.append(itemString);
			sb.append("\r\n");
		}
		sb.delete(sb.length()-2, sb.length());
		return sb.toString();
	}

	@Override
	public boolean contains(Object key) {
		return data.containsKey(key);
	}

	@Override
	public Object getObject(Object key) {
		return data.get(key);
	}

	@Override
	public int size() {
		return data.size();
	}

	@Override
	public boolean isEmpty() {
		return data.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return data.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return data.containsValue(value);
	}

	@Override
	public Object get(Object key) {
		return data.get(key);
	}

	@Override
	public Object put(String key, Object value) {
		return data.put(key, value);
	}

	@Override
	public Object remove(Object key) {
		return data.remove(key);
	}

	@Override
	public void putAll(Map<? extends String, ? extends Object> m) {
		data.putAll(m);
	}

	@Override
	public void clear() {
		data.clear();
	}

	@Override
	public Set<String> keySet() {
		return data.keySet();
	}

	@Override
	public Collection<Object> values() {
		return data.values();
	}

	@Override
	public Set<Entry<String, Object>> entrySet() {
		return data.entrySet();
	}

}
