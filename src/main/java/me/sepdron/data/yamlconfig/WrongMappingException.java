package me.sepdron.data.yamlconfig;

public class WrongMappingException extends RuntimeException {

	protected static final WrongMappingException NOT_A_MAP_EXCEPTION;
	protected static final WrongMappingException NOT_A_LIST_EXCEPTION;

	static {
		NOT_A_MAP_EXCEPTION = new WrongMappingException("Mapping is not a map");
		NOT_A_LIST_EXCEPTION = new WrongMappingException("Mapping is not a list");
	}
	
	private WrongMappingException(String s) {
		super(s);
	}

}
