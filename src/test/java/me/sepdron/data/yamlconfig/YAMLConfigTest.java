package me.sepdron.data.yamlconfig;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.FileSystems;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

/*
TODO:

*/

public class YAMLConfigTest {

	public static void main(String[] args) throws IOException {
		new YAMLConfigTest();
	}

	private YAMLConfig topConfig;
	private YAMLConfig listConfig;

	private YAMLConfigTest() throws IOException {
		File file = FileSystems.getDefault().getPath("data.yaml").toFile();

		topConfig = YAMLConfig.load(file);
		System.out.println(topConfig);

		listConfig = (YAMLConfig) topConfig.getObject("list");

		saveConfig();
	}

	//toString() manually tested
	private void saveConfig() throws IOException {
		File file = FileSystems.getDefault().getPath("transformed data.yaml").toFile();
		topConfig.save(file);
	}

	private int map_int = 121;
	private long map_long = 1723612312127267231L;
	private BigInteger map_bigInteger = new BigInteger("1723612312127267231172361231212726723117236123121272672311723612312127267231172361231212726723117236123121272672311723612312127267231");
	private double map_float = 10.32;
	private double map_double = 97312.12732e+300;
	private String map_string = "sydtshya6+128";
	private Object map_nul = null;
	private boolean map_boolean = false;

	@Test
	void mapMapping_getObject_String_Test() {
		assertEquals(topConfig.getObject("hyd"       ), null);
		assertEquals(topConfig.getObject("int"       ), map_int);
		assertEquals(topConfig.getObject("long"      ), map_long);
		assertEquals(topConfig.getObject("bigInteger"), map_bigInteger);
		assertEquals(topConfig.getObject("float"     ), map_float);
		assertEquals(topConfig.getObject("double"    ), map_double);
		assertEquals(topConfig.getObject("string"    ), map_string);
		assertEquals(topConfig.getObject("nul"       ), map_nul);
		assertEquals(topConfig.getObject("boolean"   ), map_boolean);

		WrongMappingException exception = WrongMappingException.NOT_A_LIST_EXCEPTION;
		assertThrowsException(exception, () -> topConfig.getObject(-19));
		assertThrowsException(exception, () -> topConfig.getObject(0  ));
		assertThrowsException(exception, () -> topConfig.getObject(726));
	}
	@Test
	void mapMapping_getObjectOrDefault_String_Object_Test() {
		assertEquals(topConfig.getObjectOrDefault("hyd"       , "172"), "172");
		assertEquals(topConfig.getObjectOrDefault("int"       , "172"), map_int);
		assertEquals(topConfig.getObjectOrDefault("long"      , "172"), map_long);
		assertEquals(topConfig.getObjectOrDefault("bigInteger", "172"), map_bigInteger);
		assertEquals(topConfig.getObjectOrDefault("float"     , "172"), map_float);
		assertEquals(topConfig.getObjectOrDefault("double"    , "172"), map_double);
		assertEquals(topConfig.getObjectOrDefault("string"    , "172"), map_string);
		assertEquals(topConfig.getObjectOrDefault("nul"       , "172"), map_nul);
		assertEquals(topConfig.getObjectOrDefault("boolean"   , "172"), map_boolean);

		WrongMappingException exception = WrongMappingException.NOT_A_LIST_EXCEPTION;
		assertThrowsException(exception, () -> topConfig.getObjectOrDefault(-19, "172"));
		assertThrowsException(exception, () -> topConfig.getObjectOrDefault(0  , "172"));
		assertThrowsException(exception, () -> topConfig.getObjectOrDefault(726, "172"));
	}
	@Test
	void mapMapping_getObject_String_boolean_Test() {
		assertEquals(topConfig.getObject("hyd"       , false), null);
		assertEquals(topConfig.getObject("int"       , false), map_int);
		assertEquals(topConfig.getObject("long"      , false), map_long);
		assertEquals(topConfig.getObject("bigInteger", false), map_bigInteger);
		assertEquals(topConfig.getObject("float"     , false), map_float);
		assertEquals(topConfig.getObject("double"    , false), map_double);
		assertEquals(topConfig.getObject("string"    , false), map_string);
		assertEquals(topConfig.getObject("nul"       , false), map_nul);
		assertEquals(topConfig.getObject("boolean"   , false), map_boolean);

		assertEquals(topConfig.getObject("int"       , true ), map_int);
		assertEquals(topConfig.getObject("long"      , true ), map_long);
		assertEquals(topConfig.getObject("bigInteger", true ), map_bigInteger);
		assertEquals(topConfig.getObject("float"     , true ), map_float);
		assertEquals(topConfig.getObject("double"    , true ), map_double);
		assertEquals(topConfig.getObject("string"    , true ), map_string);
		assertEquals(topConfig.getObject("nul"       , true ), map_nul);
		assertEquals(topConfig.getObject("boolean"   , true ), map_boolean);

		Class<NoneOfTypeException> exceptionClass = NoneOfTypeException.class;
		assertThrows(exceptionClass, () -> topConfig.getObject("hyd", true));

		WrongMappingException exception = WrongMappingException.NOT_A_LIST_EXCEPTION;
		assertThrowsException(exception, () -> topConfig.getObject(-19, false));
		assertThrowsException(exception, () -> topConfig.getObject(0  , false));
		assertThrowsException(exception, () -> topConfig.getObject(726, false));

		assertThrowsException(exception, () -> topConfig.getObject(-19, true ));
		assertThrowsException(exception, () -> topConfig.getObject(0  , true ));
		assertThrowsException(exception, () -> topConfig.getObject(726, true ));
	}

	private int list_0 = -121;
	private long list_1 = -1723612312127267231L;
	private BigInteger list_2 = new BigInteger("-1723612312127267231172361231212726723117236123121272672311723612312127267231172361231212726723117236123121272672311723612312127267231");
	private double list_3 = -10.32;
	private double list_4 = -97312.12732e-30;
	private String list_5 = "systemOutPrintln()";
	private Object list_6 = null;
	private boolean list_7 = true;

	@Test
	void listMapping_getObject_String_Test() {
		assertEquals(listConfig.getObject(-19), null  ); // out of bounds
		assertEquals(listConfig.getObject(0  ), list_0); // int
		assertEquals(listConfig.getObject(1  ), list_1); // long
		assertEquals(listConfig.getObject(2  ), list_2); // bigInteger
		assertEquals(listConfig.getObject(3  ), list_3); // double
		assertEquals(listConfig.getObject(4  ), list_4); // double
		assertEquals(listConfig.getObject(5  ), list_5); // string
		assertEquals(listConfig.getObject(6  ), list_6); // null
		assertEquals(listConfig.getObject(7  ), list_7); // boolean
		assertEquals(listConfig.getObject(726), null  ); // out of bounds

		WrongMappingException exception = WrongMappingException.NOT_A_MAP_EXCEPTION;
		assertThrowsException(exception, () -> listConfig.getObject("hyd"));
		assertThrowsException(exception, () -> listConfig.getObject("int"));
	}
	@Test
	void listMapping_getObjectOrDefault_String_Object_Test() {
		assertEquals(listConfig.getObjectOrDefault(-19, "172"), "172" ); // out of bounds
		assertEquals(listConfig.getObjectOrDefault(0  , "172"), list_0); // int
		assertEquals(listConfig.getObjectOrDefault(1  , "172"), list_1); // long
		assertEquals(listConfig.getObjectOrDefault(2  , "172"), list_2); // bigInteger
		assertEquals(listConfig.getObjectOrDefault(3  , "172"), list_3); // double
		assertEquals(listConfig.getObjectOrDefault(4  , "172"), list_4); // double
		assertEquals(listConfig.getObjectOrDefault(5  , "172"), list_5); // string
		assertEquals(listConfig.getObjectOrDefault(6  , "172"), list_6); // null
		assertEquals(listConfig.getObjectOrDefault(7  , "172"), list_7); // boolean
		assertEquals(listConfig.getObjectOrDefault(726, "172"), "172" ); // out of bounds

		WrongMappingException exception = WrongMappingException.NOT_A_MAP_EXCEPTION;
		assertThrowsException(exception, () -> listConfig.getObjectOrDefault("hyd", "172"));
		assertThrowsException(exception, () -> listConfig.getObjectOrDefault("int", "172"));
	}
	@Test
	void listMapping_getObject_String_boolean_Test() {
		assertEquals(listConfig.getObject(-19, false), null  ); // out of bounds
		assertEquals(listConfig.getObject(0  , false), list_0); // int
		assertEquals(listConfig.getObject(1  , false), list_1); // long
		assertEquals(listConfig.getObject(2  , false), list_2); // bigInteger
		assertEquals(listConfig.getObject(3  , false), list_3); // double
		assertEquals(listConfig.getObject(4  , false), list_4); // double
		assertEquals(listConfig.getObject(5  , false), list_5); // string
		assertEquals(listConfig.getObject(6  , false), list_6); // null
		assertEquals(listConfig.getObject(7  , false), list_7); // boolean
		assertEquals(listConfig.getObject(726, false), null  ); // out of bounds

		assertEquals(listConfig.getObject(0  , true ), list_0); // int
		assertEquals(listConfig.getObject(1  , true ), list_1); // long
		assertEquals(listConfig.getObject(2  , true ), list_2); // bigInteger
		assertEquals(listConfig.getObject(3  , true ), list_3); // double
		assertEquals(listConfig.getObject(4  , true ), list_4); // double
		assertEquals(listConfig.getObject(5  , true ), list_5); // string
		assertEquals(listConfig.getObject(6  , true ), list_6); // null
		assertEquals(listConfig.getObject(7  , true ), list_7); // boolean

		Class<NoneOfTypeException> exceptionClass = NoneOfTypeException.class;
		assertThrows(exceptionClass, () -> listConfig.getObject(-19, true));
		assertThrows(exceptionClass, () -> listConfig.getObject(726, true));

		WrongMappingException exception = WrongMappingException.NOT_A_MAP_EXCEPTION;
		assertThrowsException(exception, () -> listConfig.getObject("hyd", false));
		assertThrowsException(exception, () -> listConfig.getObject("int", false));

		assertThrowsException(exception, () -> listConfig.getObject("hyd", true ));
		assertThrowsException(exception, () -> listConfig.getObject("int", true ));
	}

	@Test
	void mapMapping_getString_String_Test() {
		assertEquals(topConfig.getString("hyd"       ), null);
		assertEquals(topConfig.getString("int"       ), null);
		assertEquals(topConfig.getString("long"      ), null);
		assertEquals(topConfig.getString("bigInteger"), null);
		assertEquals(topConfig.getString("float"     ), null);
		assertEquals(topConfig.getString("double"    ), null);
		assertEquals(topConfig.getString("string"    ), map_string);
		assertEquals(topConfig.getString("nul"       ), null);
		assertEquals(topConfig.getString("boolean"   ), null);
	}
	@Test
	void mapMapping_getStringOrDefault_String_String_Test() {
		assertEquals(topConfig.getStringOrDefault("hyd"       , "aaa"), "aaa");
		assertEquals(topConfig.getStringOrDefault("int"       , "aaa"), "aaa");
		assertEquals(topConfig.getStringOrDefault("long"      , "aaa"), "aaa");
		assertEquals(topConfig.getStringOrDefault("bigInteger", "aaa"), "aaa");
		assertEquals(topConfig.getStringOrDefault("float"     , "aaa"), "aaa");
		assertEquals(topConfig.getStringOrDefault("double"    , "aaa"), "aaa");
		assertEquals(topConfig.getStringOrDefault("string"    , "aaa"), map_string);
		assertEquals(topConfig.getStringOrDefault("nul"       , "aaa"), "aaa");
		assertEquals(topConfig.getStringOrDefault("boolean"   , "aaa"), "aaa");
	}
	@Test
	void mapMapping_getString_String_boolean_Test() {
		assertEquals(topConfig.getString("hyd"       , false), null);
		assertEquals(topConfig.getString("int"       , false), null);
		assertEquals(topConfig.getString("long"      , false), null);
		assertEquals(topConfig.getString("bigInteger", false), null);
		assertEquals(topConfig.getString("float"     , false), null);
		assertEquals(topConfig.getString("double"    , false), null);
		assertEquals(topConfig.getString("string"    , false), map_string);
		assertEquals(topConfig.getString("nul"       , false), null);
		assertEquals(topConfig.getString("boolean"   , false), null);

		assertEquals(topConfig.getString("string"    , true ), map_string);

		Class<NoneOfTypeException> exceptionClass = NoneOfTypeException.class;
		assertThrows(exceptionClass, () -> topConfig.getString("hyd"       , true));
		assertThrows(exceptionClass, () -> topConfig.getString("int"       , true));
		assertThrows(exceptionClass, () -> topConfig.getString("long"      , true));
		assertThrows(exceptionClass, () -> topConfig.getString("bigInteger", true));
		assertThrows(exceptionClass, () -> topConfig.getString("float"     , true));
		assertThrows(exceptionClass, () -> topConfig.getString("double"    , true));
		assertThrows(exceptionClass, () -> topConfig.getString("nul"       , true));
		assertThrows(exceptionClass, () -> topConfig.getString("boolean"   , true));
	}

	@Test
	void listMapping_getString_String_Test() {
		assertEquals(listConfig.getString(-19), null  ); // out of bounds
		assertEquals(listConfig.getString(0  ), null  ); // int
		assertEquals(listConfig.getString(1  ), null  ); // long
		assertEquals(listConfig.getString(2  ), null  ); // bigInteger
		assertEquals(listConfig.getString(3  ), null  ); // double
		assertEquals(listConfig.getString(4  ), null  ); // double
		assertEquals(listConfig.getString(5  ), list_5); // string
		assertEquals(listConfig.getString(6  ), null  ); // null
		assertEquals(listConfig.getString(7  ), null  ); // boolean
		assertEquals(listConfig.getString(726), null  ); // out of bounds
	}
	@Test
	void listMapping_getStringOrDefault_String_String_Test() {
		assertEquals(listConfig.getStringOrDefault(-19, "aaa"), "aaa" ); // out of bounds
		assertEquals(listConfig.getStringOrDefault(0  , "aaa"), "aaa" ); // int
		assertEquals(listConfig.getStringOrDefault(1  , "aaa"), "aaa" ); // long
		assertEquals(listConfig.getStringOrDefault(2  , "aaa"), "aaa" ); // bigInteger
		assertEquals(listConfig.getStringOrDefault(3  , "aaa"), "aaa" ); // double
		assertEquals(listConfig.getStringOrDefault(4  , "aaa"), "aaa" ); // double
		assertEquals(listConfig.getStringOrDefault(5  , "aaa"), list_5); // string
		assertEquals(listConfig.getStringOrDefault(6  , "aaa"), "aaa" ); // null
		assertEquals(listConfig.getStringOrDefault(7  , "aaa"), "aaa" ); // boolean
		assertEquals(listConfig.getStringOrDefault(726, "aaa"), "aaa" ); // out of bounds
	}
	@Test
	void listMapping_getString_String_boolean_Test() {
		assertEquals(listConfig.getString(-19, false), null  ); // out of bounds
		assertEquals(listConfig.getString(0  , false), null  ); // int
		assertEquals(listConfig.getString(1  , false), null  ); // long
		assertEquals(listConfig.getString(2  , false), null  ); // bigInteger
		assertEquals(listConfig.getString(3  , false), null  ); // double
		assertEquals(listConfig.getString(4  , false), null  ); // double
		assertEquals(listConfig.getString(5  , false), list_5); // string
		assertEquals(listConfig.getString(6  , false), null  ); // null
		assertEquals(listConfig.getString(7  , false), null  ); // boolean
		assertEquals(listConfig.getString(726, false), null  ); // out of bounds

		assertEquals(listConfig.getString(5  , true ), list_5); // string

		Class<NoneOfTypeException> exceptionClass = NoneOfTypeException.class;
		assertThrows(exceptionClass, () -> listConfig.getString(-19, true)); // out of bounds
		assertThrows(exceptionClass, () -> listConfig.getString(0  , true)); // int
		assertThrows(exceptionClass, () -> listConfig.getString(1  , true)); // long
		assertThrows(exceptionClass, () -> listConfig.getString(2  , true)); // bigInteger
		assertThrows(exceptionClass, () -> listConfig.getString(3  , true)); // double
		assertThrows(exceptionClass, () -> listConfig.getString(4  , true)); // double
		assertThrows(exceptionClass, () -> listConfig.getString(6  , true)); // null
		assertThrows(exceptionClass, () -> listConfig.getString(7  , true)); // boolean
		assertThrows(exceptionClass, () -> listConfig.getString(726, true)); // out of bounds
	}

	@Test
	void mapMapping_getInt_String_Test() {
		assertEquals(topConfig.getInt("hyd"       ), 0);
		assertEquals(topConfig.getInt("int"       ), map_int);
		assertEquals(topConfig.getInt("long"      ), 0);
		assertEquals(topConfig.getInt("bigInteger"), 0);
		assertEquals(topConfig.getInt("float"     ), 0);
		assertEquals(topConfig.getInt("double"    ), 0);
		assertEquals(topConfig.getInt("string"    ), 0);
		assertEquals(topConfig.getInt("nul"       ), 0);
		assertEquals(topConfig.getInt("boolean"   ), 0);
	}
	@Test
	void mapMapping_getIntOrDefault_String_int_Test() {
		assertEquals(topConfig.getIntOrDefault("hyd"       , -12), -12);
		assertEquals(topConfig.getIntOrDefault("int"       , -12), map_int);
		assertEquals(topConfig.getIntOrDefault("long"      , -12), -12);
		assertEquals(topConfig.getIntOrDefault("bigInteger", -12), -12);
		assertEquals(topConfig.getIntOrDefault("float"     , -12), -12);
		assertEquals(topConfig.getIntOrDefault("double"    , -12), -12);
		assertEquals(topConfig.getIntOrDefault("string"    , -12), -12);
		assertEquals(topConfig.getIntOrDefault("nul"       , -12), -12);
		assertEquals(topConfig.getIntOrDefault("boolean"   , -12), -12);
	}
	@Test
	void mapMapping_getInt_String_boolean_Test() {
		assertEquals(topConfig.getInt("hyd"       , false), 0);
		assertEquals(topConfig.getInt("int"       , false), map_int);
		assertEquals(topConfig.getInt("long"      , false), 0);
		assertEquals(topConfig.getInt("bigInteger", false), 0);
		assertEquals(topConfig.getInt("float"     , false), 0);
		assertEquals(topConfig.getInt("double"    , false), 0);
		assertEquals(topConfig.getInt("string"    , false), 0);
		assertEquals(topConfig.getInt("nul"       , false), 0);
		assertEquals(topConfig.getInt("boolean"   , false), 0);

		assertEquals(topConfig.getInt("int"       , true ), map_int);

		Class<NoneOfTypeException> exceptionClass = NoneOfTypeException.class;
		assertThrows(exceptionClass, () -> topConfig.getInt("hyd"       , true));
		assertThrows(exceptionClass, () -> topConfig.getInt("long"      , true));
		assertThrows(exceptionClass, () -> topConfig.getInt("bigInteger", true));
		assertThrows(exceptionClass, () -> topConfig.getInt("float"     , true));
		assertThrows(exceptionClass, () -> topConfig.getInt("double"    , true));
		assertThrows(exceptionClass, () -> topConfig.getInt("string"    , true));
		assertThrows(exceptionClass, () -> topConfig.getInt("nul"       , true));
		assertThrows(exceptionClass, () -> topConfig.getInt("boolean"   , true));
	}

	@Test
	void listMapping_getInt_String_Test() {
		assertEquals(listConfig.getInt(-19), 0     ); // out of bounds
		assertEquals(listConfig.getInt(0  ), list_0); // int
		assertEquals(listConfig.getInt(1  ), 0     ); // long
		assertEquals(listConfig.getInt(2  ), 0     ); // bigInteger
		assertEquals(listConfig.getInt(3  ), 0     ); // double
		assertEquals(listConfig.getInt(4  ), 0     ); // double
		assertEquals(listConfig.getInt(5  ), 0     ); // string
		assertEquals(listConfig.getInt(6  ), 0     ); // null
		assertEquals(listConfig.getInt(7  ), 0     ); // boolean
		assertEquals(listConfig.getInt(726), 0     ); // out of bounds
	}
	@Test
	void listMapping_getIntOrDefault_String_int_Test() {
		assertEquals(listConfig.getIntOrDefault(-19, -12), -12   ); // out of bounds
		assertEquals(listConfig.getIntOrDefault(0  , -12), list_0); // int
		assertEquals(listConfig.getIntOrDefault(1  , -12), -12   ); // long
		assertEquals(listConfig.getIntOrDefault(2  , -12), -12   ); // bigInteger
		assertEquals(listConfig.getIntOrDefault(3  , -12), -12   ); // double
		assertEquals(listConfig.getIntOrDefault(4  , -12), -12   ); // double
		assertEquals(listConfig.getIntOrDefault(5  , -12), -12   ); // string
		assertEquals(listConfig.getIntOrDefault(6  , -12), -12   ); // null
		assertEquals(listConfig.getIntOrDefault(7  , -12), -12   ); // boolean
		assertEquals(listConfig.getIntOrDefault(726, -12), -12   ); // out of bounds
	}
	@Test
	void listMapping_getInt_String_boolean_Test() {
		assertEquals(listConfig.getInt(-19, false), 0     ); // out of bounds
		assertEquals(listConfig.getInt(0  , false), list_0); // int
		assertEquals(listConfig.getInt(1  , false), 0     ); // long
		assertEquals(listConfig.getInt(2  , false), 0     ); // bigInteger
		assertEquals(listConfig.getInt(3  , false), 0     ); // double
		assertEquals(listConfig.getInt(4  , false), 0     ); // double
		assertEquals(listConfig.getInt(5  , false), 0     ); // string
		assertEquals(listConfig.getInt(6  , false), 0     ); // null
		assertEquals(listConfig.getInt(7  , false), 0     ); // boolean
		assertEquals(listConfig.getInt(726, false), 0     ); // out of bounds

		assertEquals(listConfig.getInt(0  , true ), list_0); // int

		Class<NoneOfTypeException> exceptionClass = NoneOfTypeException.class;
		assertThrows(exceptionClass, () -> listConfig.getInt(-19, true));  // out of bounds
		assertThrows(exceptionClass, () -> listConfig.getInt(1  , true));  // long
		assertThrows(exceptionClass, () -> listConfig.getInt(2  , true));  // bigInteger
		assertThrows(exceptionClass, () -> listConfig.getInt(3  , true));  // double
		assertThrows(exceptionClass, () -> listConfig.getInt(4  , true));  // double
		assertThrows(exceptionClass, () -> listConfig.getInt(5  , true));  // string
		assertThrows(exceptionClass, () -> listConfig.getInt(6  , true));  // null
		assertThrows(exceptionClass, () -> listConfig.getInt(7  , true));  // boolean
		assertThrows(exceptionClass, () -> listConfig.getInt(726, true));  // out of bounds
	}

	@Test
	void mapMapping_getLong_String_Test() {
		assertEquals(topConfig.getLong("hyd"       ), 0);
		assertEquals(topConfig.getLong("int"       ), map_int);
		assertEquals(topConfig.getLong("long"      ), map_long);
		assertEquals(topConfig.getLong("bigInteger"), 0);
		assertEquals(topConfig.getLong("float"     ), 0);
		assertEquals(topConfig.getLong("double"    ), 0);
		assertEquals(topConfig.getLong("string"    ), 0);
		assertEquals(topConfig.getLong("nul"       ), 0);
		assertEquals(topConfig.getLong("boolean"   ), 0);
	}
	@Test
	void mapMapping_getLongOrDefault_String_int_Test() {
		assertEquals(topConfig.getLongOrDefault("hyd"       , -12), -12);
		assertEquals(topConfig.getLongOrDefault("int"       , -12), map_int);
		assertEquals(topConfig.getLongOrDefault("long"      , -12), map_long);
		assertEquals(topConfig.getLongOrDefault("bigInteger", -12), -12);
		assertEquals(topConfig.getLongOrDefault("float"     , -12), -12);
		assertEquals(topConfig.getLongOrDefault("double"    , -12), -12);
		assertEquals(topConfig.getLongOrDefault("string"    , -12), -12);
		assertEquals(topConfig.getLongOrDefault("nul"       , -12), -12);
		assertEquals(topConfig.getLongOrDefault("boolean"   , -12), -12);
	}
	@Test
	void mapMapping_getLong_String_boolean_Test() {
		assertEquals(topConfig.getLong("hyd"       , false), 0);
		assertEquals(topConfig.getLong("int"       , false), map_int);
		assertEquals(topConfig.getLong("long"      , false), map_long);
		assertEquals(topConfig.getLong("bigInteger", false), 0);
		assertEquals(topConfig.getLong("float"     , false), 0);
		assertEquals(topConfig.getLong("double"    , false), 0);
		assertEquals(topConfig.getLong("string"    , false), 0);
		assertEquals(topConfig.getLong("nul"       , false), 0);
		assertEquals(topConfig.getLong("boolean"   , false), 0);

		assertEquals(topConfig.getLong("int"       , true ), map_int);
		assertEquals(topConfig.getLong("long"      , true ), map_long);

		Class<NoneOfTypeException> exceptionClass = NoneOfTypeException.class;
		assertThrows(exceptionClass, () -> topConfig.getLong("hyd"       , true));
		assertThrows(exceptionClass, () -> topConfig.getLong("bigInteger", true));
		assertThrows(exceptionClass, () -> topConfig.getLong("float"     , true));
		assertThrows(exceptionClass, () -> topConfig.getLong("double"    , true));
		assertThrows(exceptionClass, () -> topConfig.getLong("string"    , true));
		assertThrows(exceptionClass, () -> topConfig.getLong("nul"       , true));
		assertThrows(exceptionClass, () -> topConfig.getLong("boolean"   , true));
	}

	@Test
	void listMapping_getLong_String_Test() {
		assertEquals(listConfig.getLong(-19), 0     ); // out of bounds
		assertEquals(listConfig.getLong(0  ), list_0); // int
		assertEquals(listConfig.getLong(1  ), list_1); // long
		assertEquals(listConfig.getLong(2  ), 0     ); // bigInteger
		assertEquals(listConfig.getLong(3  ), 0     ); // double
		assertEquals(listConfig.getLong(4  ), 0     ); // double
		assertEquals(listConfig.getLong(5  ), 0     ); // string
		assertEquals(listConfig.getLong(6  ), 0     ); // null
		assertEquals(listConfig.getLong(7  ), 0     ); // boolean
		assertEquals(listConfig.getLong(726), 0     ); // out of bounds
	}
	@Test
	void listMapping_getLongOrDefault_String_int_Test() {
		assertEquals(listConfig.getLongOrDefault(-19, -12), -12   ); // out of bounds
		assertEquals(listConfig.getLongOrDefault(0  , -12), list_0); // int
		assertEquals(listConfig.getLongOrDefault(1  , -12), list_1); // long
		assertEquals(listConfig.getLongOrDefault(2  , -12), -12   ); // bigInteger
		assertEquals(listConfig.getLongOrDefault(3  , -12), -12   ); // double
		assertEquals(listConfig.getLongOrDefault(4  , -12), -12   ); // double
		assertEquals(listConfig.getLongOrDefault(5  , -12), -12   ); // string
		assertEquals(listConfig.getLongOrDefault(6  , -12), -12   ); // null
		assertEquals(listConfig.getLongOrDefault(7  , -12), -12   ); // boolean
		assertEquals(listConfig.getLongOrDefault(726, -12), -12   ); // out of bounds
	}
	@Test
	void listMapping_getLong_String_boolean_Test() {
		assertEquals(listConfig.getLong(-19, false), 0     ); // out of bounds
		assertEquals(listConfig.getLong(0  , false), list_0); // int
		assertEquals(listConfig.getLong(1  , false), list_1); // long
		assertEquals(listConfig.getLong(2  , false), 0     ); // bigInteger
		assertEquals(listConfig.getLong(3  , false), 0     ); // double
		assertEquals(listConfig.getLong(4  , false), 0     ); // double
		assertEquals(listConfig.getLong(5  , false), 0     ); // string
		assertEquals(listConfig.getLong(6  , false), 0     ); // null
		assertEquals(listConfig.getLong(7  , false), 0     ); // boolean
		assertEquals(listConfig.getLong(726, false), 0     ); // out of bounds

		assertEquals(listConfig.getLong(0  , true ), list_0); // int
		assertEquals(listConfig.getLong(1  , true ), list_1); // long

		Class<NoneOfTypeException> exceptionClass = NoneOfTypeException.class;
		assertThrows(exceptionClass, () -> listConfig.getLong(-19, true));  // out of bounds
		assertThrows(exceptionClass, () -> listConfig.getLong(2  , true));  // bigInteger
		assertThrows(exceptionClass, () -> listConfig.getLong(3  , true));  // double
		assertThrows(exceptionClass, () -> listConfig.getLong(4  , true));  // double
		assertThrows(exceptionClass, () -> listConfig.getLong(5  , true));  // string
		assertThrows(exceptionClass, () -> listConfig.getLong(6  , true));  // null
		assertThrows(exceptionClass, () -> listConfig.getLong(7  , true));  // boolean
		assertThrows(exceptionClass, () -> listConfig.getLong(726, true));  // out of bounds
	}

	@Test
	void mapMapping_getBigInteger_String_Test() {
		BigInteger zero = BigInteger.ZERO;
		BigInteger bi_map_int = BigInteger.valueOf(map_int);
		BigInteger bi_map_long = BigInteger.valueOf(map_long);

		assertEquals(topConfig.getBigInteger("hyd"       ), zero);
		assertEquals(topConfig.getBigInteger("int"       ), bi_map_int);
		assertEquals(topConfig.getBigInteger("long"      ), bi_map_long);
		assertEquals(topConfig.getBigInteger("bigInteger"), map_bigInteger);
		assertEquals(topConfig.getBigInteger("float"     ), zero);
		assertEquals(topConfig.getBigInteger("double"    ), zero);
		assertEquals(topConfig.getBigInteger("string"    ), zero);
		assertEquals(topConfig.getBigInteger("nul"       ), zero);
		assertEquals(topConfig.getBigInteger("boolean"   ), zero);
	}
	@Test
	void mapMapping_getBigIntegerOrDefault_String_int_Test() {
		BigInteger neg12 = BigInteger.valueOf(-12);
		BigInteger bi_map_int = BigInteger.valueOf(map_int);
		BigInteger bi_map_long = BigInteger.valueOf(map_long);

		assertEquals(topConfig.getBigIntegerOrDefault("hyd"       , neg12), neg12);
		assertEquals(topConfig.getBigIntegerOrDefault("int"       , neg12), bi_map_int);
		assertEquals(topConfig.getBigIntegerOrDefault("long"      , neg12), bi_map_long);
		assertEquals(topConfig.getBigIntegerOrDefault("bigInteger", neg12), map_bigInteger);
		assertEquals(topConfig.getBigIntegerOrDefault("float"     , neg12), neg12);
		assertEquals(topConfig.getBigIntegerOrDefault("double"    , neg12), neg12);
		assertEquals(topConfig.getBigIntegerOrDefault("string"    , neg12), neg12);
		assertEquals(topConfig.getBigIntegerOrDefault("nul"       , neg12), neg12);
		assertEquals(topConfig.getBigIntegerOrDefault("boolean"   , neg12), neg12);
	}
	@Test
	void mapMapping_getBigInteger_String_boolean_Test() {
		BigInteger zero = BigInteger.ZERO;
		BigInteger bi_map_int = BigInteger.valueOf(map_int);
		BigInteger bi_map_long = BigInteger.valueOf(map_long);

		assertEquals(topConfig.getBigInteger("hyd"       , false), zero);
		assertEquals(topConfig.getBigInteger("int"       , false), bi_map_int);
		assertEquals(topConfig.getBigInteger("long"      , false), bi_map_long);
		assertEquals(topConfig.getBigInteger("bigInteger", false), map_bigInteger);
		assertEquals(topConfig.getBigInteger("float"     , false), zero);
		assertEquals(topConfig.getBigInteger("double"    , false), zero);
		assertEquals(topConfig.getBigInteger("string"    , false), zero);
		assertEquals(topConfig.getBigInteger("nul"       , false), zero);
		assertEquals(topConfig.getBigInteger("boolean"   , false), zero);

		assertEquals(topConfig.getBigInteger("int"       , true ), bi_map_int);
		assertEquals(topConfig.getBigInteger("long"      , true ), bi_map_long);
		assertEquals(topConfig.getBigInteger("bigInteger", true ), map_bigInteger);

		Class<NoneOfTypeException> exceptionClass = NoneOfTypeException.class;
		assertThrows(exceptionClass, () -> topConfig.getBigInteger("hyd"       , true));
		assertThrows(exceptionClass, () -> topConfig.getBigInteger("float"     , true));
		assertThrows(exceptionClass, () -> topConfig.getBigInteger("double"    , true));
		assertThrows(exceptionClass, () -> topConfig.getBigInteger("string"    , true));
		assertThrows(exceptionClass, () -> topConfig.getBigInteger("nul"       , true));
		assertThrows(exceptionClass, () -> topConfig.getBigInteger("boolean"   , true));
	}

	@Test
	void listMapping_getBigInteger_String_Test() {
		BigInteger zero = BigInteger.ZERO;
		BigInteger bi_list_0 = BigInteger.valueOf(list_0);
		BigInteger bi_list_1 = BigInteger.valueOf(list_1);

		assertEquals(listConfig.getBigInteger(-19), zero     ); // out of bounds
		assertEquals(listConfig.getBigInteger(0  ), bi_list_0); // int
		assertEquals(listConfig.getBigInteger(1  ), bi_list_1); // long
		assertEquals(listConfig.getBigInteger(2  ), list_2   ); // bigInteger
		assertEquals(listConfig.getBigInteger(3  ), zero     ); // double
		assertEquals(listConfig.getBigInteger(4  ), zero     ); // double
		assertEquals(listConfig.getBigInteger(5  ), zero     ); // string
		assertEquals(listConfig.getBigInteger(6  ), zero     ); // null
		assertEquals(listConfig.getBigInteger(7  ), zero     ); // boolean
		assertEquals(listConfig.getBigInteger(726), zero     ); // out of bounds
	}
	@Test
	void listMapping_getBigIntegerOrDefault_String_int_Test() {
		BigInteger neg12 = BigInteger.valueOf(-12);
		BigInteger bi_list_0 = BigInteger.valueOf(list_0);
		BigInteger bi_list_1 = BigInteger.valueOf(list_1);

		assertEquals(listConfig.getBigIntegerOrDefault(-19, neg12), neg12    ); // out of bounds
		assertEquals(listConfig.getBigIntegerOrDefault(0  , neg12), bi_list_0); // int
		assertEquals(listConfig.getBigIntegerOrDefault(1  , neg12), bi_list_1); // long
		assertEquals(listConfig.getBigIntegerOrDefault(2  , neg12), list_2   ); // bigInteger
		assertEquals(listConfig.getBigIntegerOrDefault(3  , neg12), neg12    ); // double
		assertEquals(listConfig.getBigIntegerOrDefault(4  , neg12), neg12    ); // double
		assertEquals(listConfig.getBigIntegerOrDefault(5  , neg12), neg12    ); // string
		assertEquals(listConfig.getBigIntegerOrDefault(6  , neg12), neg12    ); // null
		assertEquals(listConfig.getBigIntegerOrDefault(7  , neg12), neg12    ); // boolean
		assertEquals(listConfig.getBigIntegerOrDefault(726, neg12), neg12    ); // out of bounds
	}
	@Test
	void listMapping_getBigInteger_String_boolean_Test() {
		BigInteger zero = BigInteger.ZERO;
		BigInteger bi_list_0 = BigInteger.valueOf(list_0);
		BigInteger bi_list_1 = BigInteger.valueOf(list_1);

		assertEquals(listConfig.getBigInteger(-19, false), zero     ); // out of bounds
		assertEquals(listConfig.getBigInteger(0  , false), bi_list_0); // int
		assertEquals(listConfig.getBigInteger(1  , false), bi_list_1); // long
		assertEquals(listConfig.getBigInteger(2  , false), list_2   ); // bigInteger
		assertEquals(listConfig.getBigInteger(3  , false), zero     ); // double
		assertEquals(listConfig.getBigInteger(4  , false), zero     ); // double
		assertEquals(listConfig.getBigInteger(5  , false), zero     ); // string
		assertEquals(listConfig.getBigInteger(6  , false), zero     ); // null
		assertEquals(listConfig.getBigInteger(7  , false), zero     ); // boolean
		assertEquals(listConfig.getBigInteger(726, false), zero     ); // out of bounds

		assertEquals(listConfig.getBigInteger(0  , true ), bi_list_0); // int
		assertEquals(listConfig.getBigInteger(1  , true ), bi_list_1); // long
		assertEquals(listConfig.getBigInteger(2  , true ), list_2); // long

		Class<NoneOfTypeException> exceptionClass = NoneOfTypeException.class;
		assertThrows(exceptionClass, () -> listConfig.getBigInteger(-19, true));  // out of bounds
		assertThrows(exceptionClass, () -> listConfig.getBigInteger(3  , true));  // double
		assertThrows(exceptionClass, () -> listConfig.getBigInteger(4  , true));  // double
		assertThrows(exceptionClass, () -> listConfig.getBigInteger(5  , true));  // string
		assertThrows(exceptionClass, () -> listConfig.getBigInteger(6  , true));  // null
		assertThrows(exceptionClass, () -> listConfig.getBigInteger(7  , true));  // boolean
		assertThrows(exceptionClass, () -> listConfig.getBigInteger(726, true));  // out of bounds
	}

	@Test
	void mapMapping_getFloat_String_Test() {
		assertEquals(topConfig.getFloat("hyd"       ),         0);
		assertEquals(topConfig.getFloat("int"       ),         map_int);
		assertEquals(topConfig.getFloat("long"      ),         map_long);
		assertEquals(topConfig.getFloat("bigInteger"),         map_bigInteger.floatValue());
		assertEquals(topConfig.getFloat("float"     ), (float) map_float);
		assertEquals(topConfig.getFloat("double"    ), (float) map_double);
		assertEquals(topConfig.getFloat("string"    ),         0);
		assertEquals(topConfig.getFloat("nul"       ),         0);
		assertEquals(topConfig.getFloat("boolean"   ),         0);
	}
	@Test
	void mapMapping_getFloatOrDefault_String_int_Test() {
		assertEquals(topConfig.getFloatOrDefault("hyd"       , (float) -12.8), (float) -12.8);
		assertEquals(topConfig.getFloatOrDefault("int"       , (float) -12.8),         map_int);
		assertEquals(topConfig.getFloatOrDefault("long"      , (float) -12.8),         map_long);
		assertEquals(topConfig.getFloatOrDefault("bigInteger", (float) -12.8),         map_bigInteger.floatValue());
		assertEquals(topConfig.getFloatOrDefault("float"     , (float) -12.8), (float) map_float);
		assertEquals(topConfig.getFloatOrDefault("double"    , (float) -12.8), (float) map_double);
		assertEquals(topConfig.getFloatOrDefault("string"    , (float) -12.8), (float) -12.8);
		assertEquals(topConfig.getFloatOrDefault("nul"       , (float) -12.8), (float) -12.8);
		assertEquals(topConfig.getFloatOrDefault("boolean"   , (float) -12.8), (float) -12.8);
	}
	@Test
	void mapMapping_getFloat_String_boolean_Test() {
		assertEquals(topConfig.getFloat("hyd"       , false),         0);
		assertEquals(topConfig.getFloat("int"       , false),         map_int);
		assertEquals(topConfig.getFloat("long"      , false),         map_long);
		assertEquals(topConfig.getFloat("bigInteger", false),         map_bigInteger.floatValue());
		assertEquals(topConfig.getFloat("float"     , false), (float) map_float);
		assertEquals(topConfig.getFloat("double"    , false), (float) map_double);
		assertEquals(topConfig.getFloat("string"    , false),         0);
		assertEquals(topConfig.getFloat("nul"       , false),         0);
		assertEquals(topConfig.getFloat("boolean"   , false),         0);

		assertEquals(topConfig.getFloat("int"       , true ),         map_int);
		assertEquals(topConfig.getFloat("long"      , true ),         map_long);
		assertEquals(topConfig.getFloat("bigInteger", true ),         map_bigInteger.floatValue());
		assertEquals(topConfig.getFloat("float"     , true ), (float) map_float);
		assertEquals(topConfig.getFloat("double"    , true ), (float) map_double);

		Class<NoneOfTypeException> exceptionClass = NoneOfTypeException.class;
		assertThrows(exceptionClass, () -> topConfig.getFloat("hyd"       , true));
		assertThrows(exceptionClass, () -> topConfig.getFloat("string"    , true));
		assertThrows(exceptionClass, () -> topConfig.getFloat("nul"       , true));
		assertThrows(exceptionClass, () -> topConfig.getFloat("boolean"   , true));
	}

	@Test
	void listMapping_getFloat_String_Test() {
		assertEquals(listConfig.getFloat(-19),         0                  ); // out of bounds
		assertEquals(listConfig.getFloat(0  ), (float) list_0             ); // int
		assertEquals(listConfig.getFloat(1  ), (float) list_1             ); // long
		assertEquals(listConfig.getFloat(2  ), (float) list_2.floatValue()); // bigInteger
		assertEquals(listConfig.getFloat(3  ), (float) list_3             ); // double
		assertEquals(listConfig.getFloat(4  ), (float) list_4             ); // double
		assertEquals(listConfig.getFloat(5  ),         0                  ); // string
		assertEquals(listConfig.getFloat(6  ),         0                  ); // null
		assertEquals(listConfig.getFloat(7  ),         0                  ); // boolean
		assertEquals(listConfig.getFloat(726),         0                  ); // out of bounds
	}
	@Test
	void listMapping_getFloatOrDefault_String_int_Test() {
		assertEquals(listConfig.getFloatOrDefault(-19, (float) -12.8), (float) -12.8              ); // out of bounds
		assertEquals(listConfig.getFloatOrDefault(0  , (float) -12.8), (float) list_0             ); // int
		assertEquals(listConfig.getFloatOrDefault(1  , (float) -12.8), (float) list_1             ); // long
		assertEquals(listConfig.getFloatOrDefault(2  , (float) -12.8), (float) list_2.floatValue()); // bigInteger
		assertEquals(listConfig.getFloatOrDefault(3  , (float) -12.8), (float) list_3             ); // double
		assertEquals(listConfig.getFloatOrDefault(4  , (float) -12.8), (float) list_4             ); // double
		assertEquals(listConfig.getFloatOrDefault(5  , (float) -12.8), (float) -12.8              ); // string
		assertEquals(listConfig.getFloatOrDefault(6  , (float) -12.8), (float) -12.8              ); // null
		assertEquals(listConfig.getFloatOrDefault(7  , (float) -12.8), (float) -12.8              ); // boolean
		assertEquals(listConfig.getFloatOrDefault(726, (float) -12.8), (float) -12.8              ); // out of bounds
	}
	@Test
	void listMapping_getFloat_String_boolean_Test() {
		assertEquals(listConfig.getFloat(-19, false),         0                  ); // out of bounds
		assertEquals(listConfig.getFloat(0  , false), (float) list_0             ); // int
		assertEquals(listConfig.getFloat(1  , false), (float) list_1             ); // long
		assertEquals(listConfig.getFloat(2  , false), (float) list_2.floatValue()); // bigInteger
		assertEquals(listConfig.getFloat(3  , false), (float) list_3             ); // double
		assertEquals(listConfig.getFloat(4  , false), (float) list_4             ); // double
		assertEquals(listConfig.getFloat(5  , false),         0                  ); // string
		assertEquals(listConfig.getFloat(6  , false),         0                  ); // null
		assertEquals(listConfig.getFloat(7  , false),         0                  ); // boolean
		assertEquals(listConfig.getFloat(726, false),         0                  ); // out of bounds

		assertEquals(listConfig.getFloat(0  , true ), (float) list_0             ); // int
		assertEquals(listConfig.getFloat(1  , true ), (float) list_1             ); // long
		assertEquals(listConfig.getFloat(2  , true ), (float) list_2.floatValue()); // bigInteger
		assertEquals(listConfig.getFloat(3  , true ), (float) list_3             ); // double
		assertEquals(listConfig.getFloat(4  , true ), (float) list_4             ); // double

		Class<NoneOfTypeException> exceptionClass = NoneOfTypeException.class;
		assertThrows(exceptionClass, () -> listConfig.getFloat(-19, true));  // out of bounds
		assertThrows(exceptionClass, () -> listConfig.getFloat(5  , true));  // string
		assertThrows(exceptionClass, () -> listConfig.getFloat(6  , true));  // null
		assertThrows(exceptionClass, () -> listConfig.getFloat(7  , true));  // boolean
		assertThrows(exceptionClass, () -> listConfig.getFloat(726, true));  // out of bounds
	}

	@Test
	void mapMapping_getDouble_String_Test() {
		assertEquals(topConfig.getDouble("hyd"       ), 0);
		assertEquals(topConfig.getDouble("int"       ), map_int);
		assertEquals(topConfig.getDouble("long"      ), map_long);
		assertEquals(topConfig.getDouble("bigInteger"), map_bigInteger.doubleValue());
		assertEquals(topConfig.getDouble("float"     ), map_float);
		assertEquals(topConfig.getDouble("double"    ), map_double);
		assertEquals(topConfig.getDouble("string"    ), 0);
		assertEquals(topConfig.getDouble("nul"       ), 0);
		assertEquals(topConfig.getDouble("boolean"   ), 0);
	}
	@Test
	void mapMapping_getDoubleOrDefault_String_int_Test() {
		assertEquals(topConfig.getDoubleOrDefault("hyd"       , -12.8), -12.8);
		assertEquals(topConfig.getDoubleOrDefault("int"       , -12.8), map_int);
		assertEquals(topConfig.getDoubleOrDefault("long"      , -12.8), map_long);
		assertEquals(topConfig.getDoubleOrDefault("bigInteger", -12.8), map_bigInteger.doubleValue());
		assertEquals(topConfig.getDoubleOrDefault("float"     , -12.8), map_float);
		assertEquals(topConfig.getDoubleOrDefault("double"    , -12.8), map_double);
		assertEquals(topConfig.getDoubleOrDefault("string"    , -12.8), -12.8);
		assertEquals(topConfig.getDoubleOrDefault("nul"       , -12.8), -12.8);
		assertEquals(topConfig.getDoubleOrDefault("boolean"   , -12.8), -12.8);
	}
	@Test
	void mapMapping_getDouble_String_boolean_Test() {
		assertEquals(topConfig.getDouble("hyd"       , false), 0);
		assertEquals(topConfig.getDouble("int"       , false), map_int);
		assertEquals(topConfig.getDouble("long"      , false), map_long);
		assertEquals(topConfig.getDouble("bigInteger", false), map_bigInteger.doubleValue());
		assertEquals(topConfig.getDouble("float"     , false), map_float);
		assertEquals(topConfig.getDouble("double"    , false), map_double);
		assertEquals(topConfig.getDouble("string"    , false), 0);
		assertEquals(topConfig.getDouble("nul"       , false), 0);
		assertEquals(topConfig.getDouble("boolean"   , false), 0);

		assertEquals(topConfig.getDouble("int"       , true ), map_int);
		assertEquals(topConfig.getDouble("long"      , true ), map_long);
		assertEquals(topConfig.getDouble("bigInteger", true ), map_bigInteger.doubleValue());
		assertEquals(topConfig.getDouble("float"     , true ), map_float);
		assertEquals(topConfig.getDouble("double"    , true ), map_double);

		Class<NoneOfTypeException> exceptionClass = NoneOfTypeException.class;
		assertThrows(exceptionClass, () -> topConfig.getDouble("hyd"       , true));
		assertThrows(exceptionClass, () -> topConfig.getDouble("string"    , true));
		assertThrows(exceptionClass, () -> topConfig.getDouble("nul"       , true));
		assertThrows(exceptionClass, () -> topConfig.getDouble("boolean"   , true));
	}

	@Test
	void listMapping_getDouble_String_Test() {
		assertEquals(listConfig.getDouble(-19), 0                   ); // out of bounds
		assertEquals(listConfig.getDouble(0  ), list_0              ); // int
		assertEquals(listConfig.getDouble(1  ), list_1              ); // long
		assertEquals(listConfig.getDouble(2  ), list_2.doubleValue()); // bigInteger
		assertEquals(listConfig.getDouble(3  ), list_3              ); // double
		assertEquals(listConfig.getDouble(4  ), list_4              ); // double
		assertEquals(listConfig.getDouble(5  ), 0                   ); // string
		assertEquals(listConfig.getDouble(6  ), 0                   ); // null
		assertEquals(listConfig.getDouble(7  ), 0                   ); // boolean
		assertEquals(listConfig.getDouble(726), 0                   ); // out of bounds
	}
	@Test
	void listMapping_getDoubleOrDefault_String_int_Test() {
		assertEquals(listConfig.getDoubleOrDefault(-19, -12.8), -12.8               ); // out of bounds
		assertEquals(listConfig.getDoubleOrDefault(0  , -12.8), list_0              ); // int
		assertEquals(listConfig.getDoubleOrDefault(1  , -12.8), list_1              ); // long
		assertEquals(listConfig.getDoubleOrDefault(2  , -12.8), list_2.doubleValue()); // bigInteger
		assertEquals(listConfig.getDoubleOrDefault(3  , -12.8), list_3              ); // double
		assertEquals(listConfig.getDoubleOrDefault(4  , -12.8), list_4              ); // double
		assertEquals(listConfig.getDoubleOrDefault(5  , -12.8), -12.8               ); // string
		assertEquals(listConfig.getDoubleOrDefault(6  , -12.8), -12.8               ); // null
		assertEquals(listConfig.getDoubleOrDefault(7  , -12.8), -12.8               ); // boolean
		assertEquals(listConfig.getDoubleOrDefault(726, -12.8), -12.8               ); // out of bounds
	}
	@Test
	void listMapping_getDouble_String_boolean_Test() {
		assertEquals(listConfig.getDouble(-19, false), 0                   ); // out of bounds
		assertEquals(listConfig.getDouble(0  , false), list_0              ); // int
		assertEquals(listConfig.getDouble(1  , false), list_1              ); // long
		assertEquals(listConfig.getDouble(2  , false), list_2.doubleValue()); // bigInteger
		assertEquals(listConfig.getDouble(3  , false), list_3              ); // double
		assertEquals(listConfig.getDouble(4  , false), list_4              ); // double
		assertEquals(listConfig.getDouble(5  , false), 0                   ); // string
		assertEquals(listConfig.getDouble(6  , false), 0                   ); // null
		assertEquals(listConfig.getDouble(7  , false), 0                   ); // boolean
		assertEquals(listConfig.getDouble(726, false), 0                   ); // out of bounds

		assertEquals(listConfig.getDouble(0  , true ), list_0              ); // int
		assertEquals(listConfig.getDouble(1  , true ), list_1              ); // long
		assertEquals(listConfig.getDouble(2  , true ), list_2.doubleValue()); // bigInteger
		assertEquals(listConfig.getDouble(3  , true ), list_3              ); // double
		assertEquals(listConfig.getDouble(4  , true ), list_4              ); // double

		Class<NoneOfTypeException> exceptionClass = NoneOfTypeException.class;
		assertThrows(exceptionClass, () -> listConfig.getDouble(-19, true));  // out of bounds
		assertThrows(exceptionClass, () -> listConfig.getDouble(5  , true));  // string
		assertThrows(exceptionClass, () -> listConfig.getDouble(6  , true));  // null
		assertThrows(exceptionClass, () -> listConfig.getDouble(7  , true));  // boolean
		assertThrows(exceptionClass, () -> listConfig.getDouble(726, true));  // out of bounds
	}

	@Test
	void mapMapping_getBoolean_String_Test() {
		assertEquals(topConfig.getBoolean("hyd"       ), false);
		assertEquals(topConfig.getBoolean("int"       ), false);
		assertEquals(topConfig.getBoolean("long"      ), false);
		assertEquals(topConfig.getBoolean("bigInteger"), false);
		assertEquals(topConfig.getBoolean("float"     ), false);
		assertEquals(topConfig.getBoolean("double"    ), false);
		assertEquals(topConfig.getBoolean("string"    ), false);
		assertEquals(topConfig.getBoolean("nul"       ), false);
		assertEquals(topConfig.getBoolean("boolean"   ), map_boolean);
	}
	@Test
	void mapMapping_getBooleanOrDefault_String_int_Test() {
		assertEquals(topConfig.getBooleanOrDefault("hyd"       , true), true);
		assertEquals(topConfig.getBooleanOrDefault("int"       , true), true);
		assertEquals(topConfig.getBooleanOrDefault("long"      , true), true);
		assertEquals(topConfig.getBooleanOrDefault("bigInteger", true), true);
		assertEquals(topConfig.getBooleanOrDefault("float"     , true), true);
		assertEquals(topConfig.getBooleanOrDefault("double"    , true), true);
		assertEquals(topConfig.getBooleanOrDefault("string"    , true), true);
		assertEquals(topConfig.getBooleanOrDefault("nul"       , true), true);
		assertEquals(topConfig.getBooleanOrDefault("boolean"   , true), map_boolean);
	}
	@Test
	void mapMapping_getBoolean_String_boolean_Test() {
		assertEquals(topConfig.getBoolean("hyd"       , false), false);
		assertEquals(topConfig.getBoolean("int"       , false), false);
		assertEquals(topConfig.getBoolean("long"      , false), false);
		assertEquals(topConfig.getBoolean("bigInteger", false), false);
		assertEquals(topConfig.getBoolean("float"     , false), false);
		assertEquals(topConfig.getBoolean("double"    , false), false);
		assertEquals(topConfig.getBoolean("string"    , false), false);
		assertEquals(topConfig.getBoolean("nul"       , false), false);
		assertEquals(topConfig.getBoolean("boolean"   , false), map_boolean);

		assertEquals(topConfig.getBoolean("boolean"   , true ), map_boolean);

		Class<NoneOfTypeException> exceptionClass = NoneOfTypeException.class;
		assertThrows(exceptionClass, () -> topConfig.getBoolean("hyd"       , true));
		assertThrows(exceptionClass, () -> topConfig.getBoolean("int"       , true));
		assertThrows(exceptionClass, () -> topConfig.getBoolean("long"      , true));
		assertThrows(exceptionClass, () -> topConfig.getBoolean("bigInteger", true));
		assertThrows(exceptionClass, () -> topConfig.getBoolean("float"     , true));
		assertThrows(exceptionClass, () -> topConfig.getBoolean("double"    , true));
		assertThrows(exceptionClass, () -> topConfig.getBoolean("string"    , true));
		assertThrows(exceptionClass, () -> topConfig.getBoolean("nul"       , true));
	}

	@Test
	void listMapping_getBoolean_String_Test() {
		assertEquals(listConfig.getBoolean(-19), false ); // out of bounds
		assertEquals(listConfig.getBoolean(0  ), false ); // int
		assertEquals(listConfig.getBoolean(1  ), false ); // long
		assertEquals(listConfig.getBoolean(2  ), false ); // bigInteger
		assertEquals(listConfig.getBoolean(3  ), false ); // double
		assertEquals(listConfig.getBoolean(4  ), false ); // double
		assertEquals(listConfig.getBoolean(5  ), false ); // string
		assertEquals(listConfig.getBoolean(6  ), false ); // null
		assertEquals(listConfig.getBoolean(7  ), list_7); // boolean
		assertEquals(listConfig.getBoolean(726), false ); // out of bounds
	}
	@Test
	void listMapping_getBooleanOrDefault_String_int_Test() {
		assertEquals(listConfig.getBooleanOrDefault(-19, true), true  ); // out of bounds
		assertEquals(listConfig.getBooleanOrDefault(0  , true), true  ); // int
		assertEquals(listConfig.getBooleanOrDefault(1  , true), true  ); // long
		assertEquals(listConfig.getBooleanOrDefault(2  , true), true  ); // bigInteger
		assertEquals(listConfig.getBooleanOrDefault(3  , true), true  ); // double
		assertEquals(listConfig.getBooleanOrDefault(4  , true), true  ); // double
		assertEquals(listConfig.getBooleanOrDefault(5  , true), true  ); // string
		assertEquals(listConfig.getBooleanOrDefault(6  , true), true  ); // null
		assertEquals(listConfig.getBooleanOrDefault(7  , true), list_7); // boolean
		assertEquals(listConfig.getBooleanOrDefault(726, true), true  ); // out of bounds
	}
	@Test
	void listMapping_getBoolean_String_boolean_Test() {
		assertEquals(listConfig.getBoolean(-19, false), false ); // out of bounds
		assertEquals(listConfig.getBoolean(0  , false), false ); // int
		assertEquals(listConfig.getBoolean(1  , false), false ); // long
		assertEquals(listConfig.getBoolean(2  , false), false ); // bigInteger
		assertEquals(listConfig.getBoolean(3  , false), false ); // double
		assertEquals(listConfig.getBoolean(4  , false), false ); // double
		assertEquals(listConfig.getBoolean(5  , false), false ); // string
		assertEquals(listConfig.getBoolean(6  , false), false ); // null
		assertEquals(listConfig.getBoolean(7  , false), list_7); // boolean
		assertEquals(listConfig.getBoolean(726, false), false ); // out of bounds

		assertEquals(listConfig.getBoolean(7  , true ), list_7); // boolean

		Class<NoneOfTypeException> exceptionClass = NoneOfTypeException.class;
		assertThrows(exceptionClass, () -> listConfig.getBoolean(-19, true)); // out of bounds
		assertThrows(exceptionClass, () -> listConfig.getBoolean(0  , true)); // int
		assertThrows(exceptionClass, () -> listConfig.getBoolean(1  , true)); // long
		assertThrows(exceptionClass, () -> listConfig.getBoolean(2  , true)); // bigInteger
		assertThrows(exceptionClass, () -> listConfig.getBoolean(3  , true)); // double
		assertThrows(exceptionClass, () -> listConfig.getBoolean(4  , true)); // double
		assertThrows(exceptionClass, () -> listConfig.getBoolean(5  , true)); // string
		assertThrows(exceptionClass, () -> listConfig.getBoolean(6  , true)); // null
		assertThrows(exceptionClass, () -> listConfig.getBoolean(726, true)); // out of bounds
	}

	public static void assertThrowsException(Throwable exception, Executable executable) {
		try {
			executable.execute();
			fail("Exception not thrown when it should be");
		} catch (Throwable e) {
			if (!exception.getClass().equals(e.getClass())) {
				fail("Wrong type of exception thrown");
			} else if (!exception.equals(e)) {
				fail("Wrong message thrown at exception");
			}
		}
	}

}
