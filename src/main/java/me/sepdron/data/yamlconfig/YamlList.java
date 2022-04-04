package me.sepdron.data.yamlconfig;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import me.sepdron.utils.CollectionUtils;

public class YamlList extends YamlSection implements List<Object> {

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

	@Override
	public int size() {
		return data.size();
	}

	@Override
	public boolean isEmpty() {
		return data.isEmpty();
	}

	@Override
	public Iterator<Object> iterator() {
		return data.iterator();
	}

	@Override
	public Object[] toArray() {
		return data.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return data.toArray(a);
	}

	@Override
	public boolean add(Object e) {
		return data.add(e);
	}

	@Override
	public boolean remove(Object o) {
		return data.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return data.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends Object> c) {
		return data.addAll(c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends Object> c) {
		return data.addAll(c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return data.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return data.retainAll(c);
	}

	@Override
	public void clear() {
		data.clear();
	}

	@Override
	public Object get(int index) {
		return data.get(index);
	}

	@Override
	public Object set(int index, Object element) {
		return data.set(index, element);
	}

	@Override
	public void add(int index, Object element) {
		data.add(index, element);
	}

	@Override
	public Object remove(int index) {
		return data.remove(index);
	}

	@Override
	public int indexOf(Object o) {
		return data.indexOf(o);
	}

	@Override
	public int lastIndexOf(Object o) {
		return data.lastIndexOf(o);
	}

	@Override
	public ListIterator<Object> listIterator() {
		return data.listIterator();
	}

	@Override
	public ListIterator<Object> listIterator(int index) {
		return data.listIterator(index);
	}

	@Override
	public List<Object> subList(int fromIndex, int toIndex) {
		return data.subList(fromIndex, toIndex);
	}

}
