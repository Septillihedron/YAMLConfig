package me.sepdron.data.yamlconfig;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.FileSystems;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

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
	void mapMapping_getObject_String_Object_Test() {
		assertEquals(topConfig.getObject("hyd"       , "172"), "172");
		assertEquals(topConfig.getObject("int"       , "172"), map_int);
		assertEquals(topConfig.getObject("long"      , "172"), map_long);
		assertEquals(topConfig.getObject("bigInteger", "172"), map_bigInteger);
		assertEquals(topConfig.getObject("float"     , "172"), map_float);
		assertEquals(topConfig.getObject("double"    , "172"), map_double);
		assertEquals(topConfig.getObject("string"    , "172"), map_string);
		assertEquals(topConfig.getObject("nul"       , "172"), map_nul);
		assertEquals(topConfig.getObject("boolean"   , "172"), map_boolean);

		WrongMappingException exception = WrongMappingException.NOT_A_LIST_EXCEPTION;
		assertThrowsException(exception, () -> topConfig.getObject(-19, "172"));
		assertThrowsException(exception, () -> topConfig.getObject(0  , "172"));
		assertThrowsException(exception, () -> topConfig.getObject(726, "172"));
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
	void listMapping_getObject_String_Object_Test() {
		assertEquals(listConfig.getObject(-19, "172"), "172" ); // out of bounds
		assertEquals(listConfig.getObject(0  , "172"), list_0); // int
		assertEquals(listConfig.getObject(1  , "172"), list_1); // long
		assertEquals(listConfig.getObject(2  , "172"), list_2); // bigInteger
		assertEquals(listConfig.getObject(3  , "172"), list_3); // double
		assertEquals(listConfig.getObject(4  , "172"), list_4); // double
		assertEquals(listConfig.getObject(5  , "172"), list_5); // string
		assertEquals(listConfig.getObject(6  , "172"), list_6); // null
		assertEquals(listConfig.getObject(7  , "172"), list_7); // boolean
		assertEquals(listConfig.getObject(726, "172"), "172" ); // out of bounds

		WrongMappingException exception = WrongMappingException.NOT_A_MAP_EXCEPTION;
		assertThrowsException(exception, () -> listConfig.getObject("hyd", "172"));
		assertThrowsException(exception, () -> listConfig.getObject("int", "172"));
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
	void mapMapping_getString_String_String_Test() {
		assertEquals(topConfig.getString("hyd"       , "aaa"), "aaa");
		assertEquals(topConfig.getString("int"       , "aaa"), "aaa");
		assertEquals(topConfig.getString("long"      , "aaa"), "aaa");
		assertEquals(topConfig.getString("bigInteger", "aaa"), "aaa");
		assertEquals(topConfig.getString("float"     , "aaa"), "aaa");
		assertEquals(topConfig.getString("double"    , "aaa"), "aaa");
		assertEquals(topConfig.getString("string"    , "aaa"), map_string);
		assertEquals(topConfig.getString("nul"       , "aaa"), "aaa");
		assertEquals(topConfig.getString("boolean"   , "aaa"), "aaa");
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
	void listMapping_getString_String_String_Test() {
		assertEquals(listConfig.getString(-19, "aaa"), "aaa" ); // out of bounds
		assertEquals(listConfig.getString(0  , "aaa"), "aaa" ); // int
		assertEquals(listConfig.getString(1  , "aaa"), "aaa" ); // long
		assertEquals(listConfig.getString(2  , "aaa"), "aaa" ); // bigInteger
		assertEquals(listConfig.getString(3  , "aaa"), "aaa" ); // double
		assertEquals(listConfig.getString(4  , "aaa"), "aaa" ); // double
		assertEquals(listConfig.getString(5  , "aaa"), list_5); // string
		assertEquals(listConfig.getString(6  , "aaa"), "aaa" ); // null
		assertEquals(listConfig.getString(7  , "aaa"), "aaa" ); // boolean
		assertEquals(listConfig.getString(726, "aaa"), "aaa" ); // out of bounds
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
	void mapMapping_getInt_String_int_Test() {
		assertEquals(topConfig.getInt("hyd"       , -12), -12);
		assertEquals(topConfig.getInt("int"       , -12), map_int);
		assertEquals(topConfig.getInt("long"      , -12), -12);
		assertEquals(topConfig.getInt("bigInteger", -12), -12);
		assertEquals(topConfig.getInt("float"     , -12), -12);
		assertEquals(topConfig.getInt("double"    , -12), -12);
		assertEquals(topConfig.getInt("string"    , -12), -12);
		assertEquals(topConfig.getInt("nul"       , -12), -12);
		assertEquals(topConfig.getInt("boolean"   , -12), -12);
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
	void listMapping_getInt_String_int_Test() {
		assertEquals(listConfig.getInt(-19, -12), -12   ); // out of bounds
		assertEquals(listConfig.getInt(0  , -12), list_0); // int
		assertEquals(listConfig.getInt(1  , -12), -12   ); // long
		assertEquals(listConfig.getInt(2  , -12), -12   ); // bigInteger
		assertEquals(listConfig.getInt(3  , -12), -12   ); // double
		assertEquals(listConfig.getInt(4  , -12), -12   ); // double
		assertEquals(listConfig.getInt(5  , -12), -12   ); // string
		assertEquals(listConfig.getInt(6  , -12), -12   ); // null
		assertEquals(listConfig.getInt(7  , -12), -12   ); // boolean
		assertEquals(listConfig.getInt(726, -12), -12   ); // out of bounds
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
	void mapMapping_getLong_String_int_Test() {
		assertEquals(topConfig.getLong("hyd"       , -12), -12);
		assertEquals(topConfig.getLong("int"       , -12), map_int);
		assertEquals(topConfig.getLong("long"      , -12), map_long);
		assertEquals(topConfig.getLong("bigInteger", -12), -12);
		assertEquals(topConfig.getLong("float"     , -12), -12);
		assertEquals(topConfig.getLong("double"    , -12), -12);
		assertEquals(topConfig.getLong("string"    , -12), -12);
		assertEquals(topConfig.getLong("nul"       , -12), -12);
		assertEquals(topConfig.getLong("boolean"   , -12), -12);
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
	void listMapping_getLong_String_int_Test() {
		assertEquals(listConfig.getLong(-19, -12), -12   ); // out of bounds
		assertEquals(listConfig.getLong(0  , -12), list_0); // int
		assertEquals(listConfig.getLong(1  , -12), list_1); // long
		assertEquals(listConfig.getLong(2  , -12), -12   ); // bigInteger
		assertEquals(listConfig.getLong(3  , -12), -12   ); // double
		assertEquals(listConfig.getLong(4  , -12), -12   ); // double
		assertEquals(listConfig.getLong(5  , -12), -12   ); // string
		assertEquals(listConfig.getLong(6  , -12), -12   ); // null
		assertEquals(listConfig.getLong(7  , -12), -12   ); // boolean
		assertEquals(listConfig.getLong(726, -12), -12   ); // out of bounds
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
	void mapMapping_getBigInteger_String_int_Test() {
		BigInteger neg12 = BigInteger.valueOf(-12);
		BigInteger bi_map_int = BigInteger.valueOf(map_int);
		BigInteger bi_map_long = BigInteger.valueOf(map_long);

		assertEquals(topConfig.getBigInteger("hyd"       , neg12), neg12);
		assertEquals(topConfig.getBigInteger("int"       , neg12), bi_map_int);
		assertEquals(topConfig.getBigInteger("long"      , neg12), bi_map_long);
		assertEquals(topConfig.getBigInteger("bigInteger", neg12), map_bigInteger);
		assertEquals(topConfig.getBigInteger("float"     , neg12), neg12);
		assertEquals(topConfig.getBigInteger("double"    , neg12), neg12);
		assertEquals(topConfig.getBigInteger("string"    , neg12), neg12);
		assertEquals(topConfig.getBigInteger("nul"       , neg12), neg12);
		assertEquals(topConfig.getBigInteger("boolean"   , neg12), neg12);
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
	void listMapping_getBigInteger_String_int_Test() {
		BigInteger neg12 = BigInteger.valueOf(-12);
		BigInteger bi_list_0 = BigInteger.valueOf(list_0);
		BigInteger bi_list_1 = BigInteger.valueOf(list_1);

		assertEquals(listConfig.getBigInteger(-19, neg12), neg12    ); // out of bounds
		assertEquals(listConfig.getBigInteger(0  , neg12), bi_list_0); // int
		assertEquals(listConfig.getBigInteger(1  , neg12), bi_list_1); // long
		assertEquals(listConfig.getBigInteger(2  , neg12), list_2   ); // bigInteger
		assertEquals(listConfig.getBigInteger(3  , neg12), neg12    ); // double
		assertEquals(listConfig.getBigInteger(4  , neg12), neg12    ); // double
		assertEquals(listConfig.getBigInteger(5  , neg12), neg12    ); // string
		assertEquals(listConfig.getBigInteger(6  , neg12), neg12    ); // null
		assertEquals(listConfig.getBigInteger(7  , neg12), neg12    ); // boolean
		assertEquals(listConfig.getBigInteger(726, neg12), neg12    ); // out of bounds
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
	void mapMapping_getFloat_String_int_Test() {
		assertEquals(topConfig.getFloat("hyd"       , (float) -12.8), (float) -12.8);
		assertEquals(topConfig.getFloat("int"       , (float) -12.8),         map_int);
		assertEquals(topConfig.getFloat("long"      , (float) -12.8),         map_long);
		assertEquals(topConfig.getFloat("bigInteger", (float) -12.8),         map_bigInteger.floatValue());
		assertEquals(topConfig.getFloat("float"     , (float) -12.8), (float) map_float);
		assertEquals(topConfig.getFloat("double"    , (float) -12.8), (float) map_double);
		assertEquals(topConfig.getFloat("string"    , (float) -12.8), (float) -12.8);
		assertEquals(topConfig.getFloat("nul"       , (float) -12.8), (float) -12.8);
		assertEquals(topConfig.getFloat("boolean"   , (float) -12.8), (float) -12.8);
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
	void listMapping_getFloat_String_int_Test() {
		assertEquals(listConfig.getFloat(-19, (float) -12.8), (float) -12.8              ); // out of bounds
		assertEquals(listConfig.getFloat(0  , (float) -12.8), (float) list_0             ); // int
		assertEquals(listConfig.getFloat(1  , (float) -12.8), (float) list_1             ); // long
		assertEquals(listConfig.getFloat(2  , (float) -12.8), (float) list_2.floatValue()); // bigInteger
		assertEquals(listConfig.getFloat(3  , (float) -12.8), (float) list_3             ); // double
		assertEquals(listConfig.getFloat(4  , (float) -12.8), (float) list_4             ); // double
		assertEquals(listConfig.getFloat(5  , (float) -12.8), (float) -12.8              ); // string
		assertEquals(listConfig.getFloat(6  , (float) -12.8), (float) -12.8              ); // null
		assertEquals(listConfig.getFloat(7  , (float) -12.8), (float) -12.8              ); // boolean
		assertEquals(listConfig.getFloat(726, (float) -12.8), (float) -12.8              ); // out of bounds
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
