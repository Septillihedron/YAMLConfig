package me.sepdron.data.yamlconfig;

import java.util.Map;

import me.sepdron.utils.CollectionUtils;

public class YamlMap extends YamlSection {

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

}
