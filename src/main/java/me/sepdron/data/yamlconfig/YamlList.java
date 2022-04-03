package me.sepdron.data.yamlconfig;

import java.util.List;

import me.sepdron.utils.CollectionUtils;

public class YamlList extends YamlSection {

	private List<Object> data;

	public YamlList(YamlSection parent, String path, List<?> data) {
		super(parent, path);
		this.data = CollectionUtils.getListAs(data, Object.class);
		for (int i=0; i<this.data.size(); i++) {
			this.data.set(i, convert("["+i+"]", this.data.get(i)));
		}
	}

	@Override
	public String toString() {
		if (data.size() == 0) return "[]";
		StringBuilder sb = new StringBuilder();
		for (Object item : data) {
			sb.append("- ");
			String itemString = ObjectToString(item);
			sb.append(itemString);
			sb.append("\r\n");
		}
		sb.delete(sb.length()-2, sb.length());
		return sb.toString();
	}
	@Override
	protected String getPathOf(Object o) {
		if (isInt(o)) return getPathOf("["+toInt(o)+"]");
		return super.getPathOf(o);
	}

	private boolean inRange(int index) {
		return index > 0 && index < data.size();
	}

	@Override
	public boolean contains(Object key) {
		if (!isInt(key)) return false;
		return inRange(toInt(key));
	}

	@Override
	public Object getObject(Object key) {
		if (!isInt(key)) return null;
		int index = toInt(key);
		if (!inRange(index)) return null;
		return data.get(index);
	}

}
