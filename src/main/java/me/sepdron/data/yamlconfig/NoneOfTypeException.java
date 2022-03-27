package me.sepdron.data.yamlconfig;

public class NoneOfTypeException extends RuntimeException {

	private NoneOfTypeException(String message) {
		super(message);
	}

	protected static NoneOfTypeException createNotExistException(String path) {
		String message = String.format("%s does not exist", path);
		return new NoneOfTypeException(message);
	}
	protected static NoneOfTypeException createTypeException(String path, String type) {
		String message = String.format("%s has type other than %s", path, type);
		return new NoneOfTypeException(message);
	}
	protected static NoneOfTypeException createMapException(String path) {
		String message = String.format("%s is not a section", path);
		return new NoneOfTypeException(message);
	}
	protected static NoneOfTypeException createListException(String path) {
		String message = String.format("%s is not a list", path);
		return new NoneOfTypeException(message);
	}
}
