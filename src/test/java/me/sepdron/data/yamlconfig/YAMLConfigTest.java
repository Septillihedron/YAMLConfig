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

	private BigInteger mapMapping_bigIntegerValue = new BigInteger("1723612312127267231172361231212726723117236123121272672311723612312127267231172361231212726723117236123121272672311723612312127267231");

	@Test
	void mapMapping_getObject_String_Test() {
		assertEquals(topConfig.getObject("hyd"       ), null);
		assertEquals(topConfig.getObject("int"       ), 121);
		assertEquals(topConfig.getObject("long"      ), 1723612312127267231L);
		assertEquals(topConfig.getObject("bigInteger"), mapMapping_bigIntegerValue);
		assertEquals(topConfig.getObject("float"     ), 10.32);
		assertEquals(topConfig.getObject("double"    ), 97312.12732e+300);
		assertEquals(topConfig.getObject("string"    ), "sydtshya6+128");
		assertEquals(topConfig.getObject("nul"       ), null);
		assertEquals(topConfig.getObject("boolean"   ), false);

		WrongMappingException exception = WrongMappingException.NOT_A_LIST_EXCEPTION;
		assertThrowsException(exception, () -> topConfig.getObject(-19));
		assertThrowsException(exception, () -> topConfig.getObject(0  ));
		assertThrowsException(exception, () -> topConfig.getObject(726));
	}
	@Test
	void mapMapping_getObject_String_Object_Test() {
		assertEquals(topConfig.getObject("hyd"       , "172"), "172");
		assertEquals(topConfig.getObject("int"       , "172"), 121);
		assertEquals(topConfig.getObject("long"      , "172"), 1723612312127267231L);
		assertEquals(topConfig.getObject("bigInteger", "172"), mapMapping_bigIntegerValue);
		assertEquals(topConfig.getObject("float"     , "172"), 10.32);
		assertEquals(topConfig.getObject("double"    , "172"), 97312.12732e+300);
		assertEquals(topConfig.getObject("string"    , "172"), "sydtshya6+128");
		assertEquals(topConfig.getObject("nul"       , "172"), null);
		assertEquals(topConfig.getObject("boolean"   , "172"), false);

		WrongMappingException exception = WrongMappingException.NOT_A_LIST_EXCEPTION;
		assertThrowsException(exception, () -> topConfig.getObject(-19, "172"));
		assertThrowsException(exception, () -> topConfig.getObject(0  , "172"));
		assertThrowsException(exception, () -> topConfig.getObject(726, "172"));
	}
	@Test
	void mapMapping_getObject_String_boolean_Test() {
		assertEquals(topConfig.getObject("hyd"       , false), null);
		assertEquals(topConfig.getObject("int"       , false), 121);
		assertEquals(topConfig.getObject("long"      , false), 1723612312127267231L);
		assertEquals(topConfig.getObject("bigInteger", false), mapMapping_bigIntegerValue);
		assertEquals(topConfig.getObject("float"     , false), 10.32);
		assertEquals(topConfig.getObject("double"    , false), 97312.12732e+300);
		assertEquals(topConfig.getObject("string"    , false), "sydtshya6+128");
		assertEquals(topConfig.getObject("nul"       , false), null);
		assertEquals(topConfig.getObject("boolean"   , false), false);

		assertEquals(topConfig.getObject("int"       , true ), 121);
		assertEquals(topConfig.getObject("long"      , true ), 1723612312127267231L);
		assertEquals(topConfig.getObject("bigInteger", true ), mapMapping_bigIntegerValue);
		assertEquals(topConfig.getObject("float"     , true ), 10.32);
		assertEquals(topConfig.getObject("double"    , true ), 97312.12732e+300);
		assertEquals(topConfig.getObject("string"    , true ), "sydtshya6+128");
		assertEquals(topConfig.getObject("nul"       , true ), null);
		assertEquals(topConfig.getObject("boolean"   , true ), false);

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

	private BigInteger listMapping_bigIntegerValue = new BigInteger("-1723612312127267231172361231212726723117236123121272672311723612312127267231172361231212726723117236123121272672311723612312127267231");

	@Test
	void listMapping_getObject_String_Test() {
		assertEquals(listConfig.getObject(-19), null);                        // out of bounds
		assertEquals(listConfig.getObject(0  ), -121);                        // int
		assertEquals(listConfig.getObject(1  ), -1723612312127267231L);       // long
		assertEquals(listConfig.getObject(2  ), listMapping_bigIntegerValue); // bigInteger
		assertEquals(listConfig.getObject(3  ), -10.32);                      // double
		assertEquals(listConfig.getObject(4  ), -97312.12732e-30);            // double
		assertEquals(listConfig.getObject(5  ), "systemOutPrintln()");        // string
		assertEquals(listConfig.getObject(6  ), null);                        // null
		assertEquals(listConfig.getObject(7  ), true);                        // boolean
		assertEquals(listConfig.getObject(726), null);                        // out of bounds

		WrongMappingException exception = WrongMappingException.NOT_A_MAP_EXCEPTION;
		assertThrowsException(exception, () -> listConfig.getObject("hyd"));
		assertThrowsException(exception, () -> listConfig.getObject("int"));
	}
	@Test
	void listMapping_getObject_String_Object_Test() {
		assertEquals(listConfig.getObject(-19, "172"), "172");                       // out of bounds
		assertEquals(listConfig.getObject(0  , "172"), -121);                        // int
		assertEquals(listConfig.getObject(1  , "172"), -1723612312127267231L);       // long
		assertEquals(listConfig.getObject(2  , "172"), listMapping_bigIntegerValue); // bigInteger
		assertEquals(listConfig.getObject(3  , "172"), -10.32);                      // double
		assertEquals(listConfig.getObject(4  , "172"), -97312.12732e-30);            // double
		assertEquals(listConfig.getObject(5  , "172"), "systemOutPrintln()");        // string
		assertEquals(listConfig.getObject(6  , "172"), null);                        // null
		assertEquals(listConfig.getObject(7  , "172"), true);                        // boolean
		assertEquals(listConfig.getObject(726, "172"), "172");                       // out of bounds

		WrongMappingException exception = WrongMappingException.NOT_A_MAP_EXCEPTION;
		assertThrowsException(exception, () -> listConfig.getObject("hyd", "172"));
		assertThrowsException(exception, () -> listConfig.getObject("int", "172"));
	}
	@Test
	void listMapping_getObject_String_boolean_Test() {
		assertEquals(listConfig.getObject(-19, false), null);                        // out of bounds
		assertEquals(listConfig.getObject(0  , false), -121);                        // int
		assertEquals(listConfig.getObject(1  , false), -1723612312127267231L);       // long
		assertEquals(listConfig.getObject(2  , false), listMapping_bigIntegerValue); // bigInteger
		assertEquals(listConfig.getObject(3  , false), -10.32);                      // double
		assertEquals(listConfig.getObject(4  , false), -97312.12732e-30);            // double
		assertEquals(listConfig.getObject(5  , false), "systemOutPrintln()");        // string
		assertEquals(listConfig.getObject(6  , false), null);                        // null
		assertEquals(listConfig.getObject(7  , false), true);                        // boolean
		assertEquals(listConfig.getObject(726, false), null);                        // out of bounds

		assertEquals(listConfig.getObject(0  , true ), -121);                        // int
		assertEquals(listConfig.getObject(1  , true ), -1723612312127267231L);       // long
		assertEquals(listConfig.getObject(2  , true ), listMapping_bigIntegerValue); // bigInteger
		assertEquals(listConfig.getObject(3  , true ), -10.32);                      // double
		assertEquals(listConfig.getObject(4  , true ), -97312.12732e-30);            // double
		assertEquals(listConfig.getObject(5  , true ), "systemOutPrintln()");        // string
		assertEquals(listConfig.getObject(6  , true ), null);                        // null
		assertEquals(listConfig.getObject(7  , true ), true);                        // boolean

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
		assertEquals(topConfig.getString("string"    ), "sydtshya6+128");
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
		assertEquals(topConfig.getString("string"    , "aaa"), "sydtshya6+128");
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
		assertEquals(topConfig.getString("string"    , false), "sydtshya6+128");
		assertEquals(topConfig.getString("nul"       , false), null);
		assertEquals(topConfig.getString("boolean"   , false), null);

		assertEquals(topConfig.getString("string"    , true ), "sydtshya6+128");

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
		assertEquals(listConfig.getString(-19), null);                 // out of bounds
		assertEquals(listConfig.getString(0  ), null);                 // int
		assertEquals(listConfig.getString(1  ), null);                 // long
		assertEquals(listConfig.getString(2  ), null);                 // bigInteger
		assertEquals(listConfig.getString(3  ), null);                 // double
		assertEquals(listConfig.getString(4  ), null);                 // double
		assertEquals(listConfig.getString(5  ), "systemOutPrintln()"); // string
		assertEquals(listConfig.getString(6  ), null);                 // null
		assertEquals(listConfig.getString(7  ), null);                 // boolean
		assertEquals(listConfig.getString(726), null);                 // out of bounds
	}
	@Test
	void listMapping_getString_String_String_Test() {
		assertEquals(listConfig.getString(-19, "aaa"), "aaa");                 // out of bounds
		assertEquals(listConfig.getString(0  , "aaa"), "aaa");                 // int
		assertEquals(listConfig.getString(1  , "aaa"), "aaa");                 // long
		assertEquals(listConfig.getString(2  , "aaa"), "aaa");                 // bigInteger
		assertEquals(listConfig.getString(3  , "aaa"), "aaa");                 // double
		assertEquals(listConfig.getString(4  , "aaa"), "aaa");                 // double
		assertEquals(listConfig.getString(5  , "aaa"), "systemOutPrintln()");  // string
		assertEquals(listConfig.getString(6  , "aaa"), "aaa");                 // null
		assertEquals(listConfig.getString(7  , "aaa"), "aaa");                 // boolean
		assertEquals(listConfig.getString(726, "aaa"), "aaa");                 // out of bounds
	}
	@Test
	void listMapping_getString_String_boolean_Test() {
		assertEquals(listConfig.getString(-19, false), null);                 // out of bounds
		assertEquals(listConfig.getString(0  , false), null);                 // int
		assertEquals(listConfig.getString(1  , false), null);                 // long
		assertEquals(listConfig.getString(2  , false), null);                 // bigInteger
		assertEquals(listConfig.getString(3  , false), null);                 // double
		assertEquals(listConfig.getString(4  , false), null);                 // double
		assertEquals(listConfig.getString(5  , false), "systemOutPrintln()"); // string
		assertEquals(listConfig.getString(6  , false), null);                 // null
		assertEquals(listConfig.getString(7  , false), null);                 // boolean
		assertEquals(listConfig.getString(726, false), null);                 // out of bounds

		assertEquals(listConfig.getString(5  , true ), "systemOutPrintln()"); // string

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
		assertEquals(topConfig.getInt("int"       ), 121);
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
		assertEquals(topConfig.getInt("int"       , -12), 121);
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
		assertEquals(topConfig.getInt("int"       , false), 121);
		assertEquals(topConfig.getInt("long"      , false), 0);
		assertEquals(topConfig.getInt("bigInteger", false), 0);
		assertEquals(topConfig.getInt("float"     , false), 0);
		assertEquals(topConfig.getInt("double"    , false), 0);
		assertEquals(topConfig.getInt("string"    , false), 0);
		assertEquals(topConfig.getInt("nul"       , false), 0);
		assertEquals(topConfig.getInt("boolean"   , false), 0);

		assertEquals(topConfig.getInt("int"       , true ), 121);

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
		assertEquals(listConfig.getInt(-19), 0);    // out of bounds
		assertEquals(listConfig.getInt(0  ), -121); // int
		assertEquals(listConfig.getInt(1  ), 0);    // long
		assertEquals(listConfig.getInt(2  ), 0);    // bigInteger
		assertEquals(listConfig.getInt(3  ), 0);    // double
		assertEquals(listConfig.getInt(4  ), 0);    // double
		assertEquals(listConfig.getInt(5  ), 0);    // string
		assertEquals(listConfig.getInt(6  ), 0);    // null
		assertEquals(listConfig.getInt(7  ), 0);    // boolean
		assertEquals(listConfig.getInt(726), 0);    // out of bounds
	}
	@Test
	void listMapping_getInt_String_int_Test() {
		assertEquals(listConfig.getInt(-19, -12), -12);  // out of bounds
		assertEquals(listConfig.getInt(0  , -12), -121); // int
		assertEquals(listConfig.getInt(1  , -12), -12);  // long
		assertEquals(listConfig.getInt(2  , -12), -12);  // bigInteger
		assertEquals(listConfig.getInt(3  , -12), -12);  // double
		assertEquals(listConfig.getInt(4  , -12), -12);  // double
		assertEquals(listConfig.getInt(5  , -12), -12);  // string
		assertEquals(listConfig.getInt(6  , -12), -12);  // null
		assertEquals(listConfig.getInt(7  , -12), -12);  // boolean
		assertEquals(listConfig.getInt(726, -12), -12);  // out of bounds
	}
	@Test
	void listMapping_getInt_String_boolean_Test() {
		assertEquals(listConfig.getInt(-19, false), 0);    // out of bounds
		assertEquals(listConfig.getInt(0  , false), -121); // int
		assertEquals(listConfig.getInt(1  , false), 0);    // long
		assertEquals(listConfig.getInt(2  , false), 0);    // bigInteger
		assertEquals(listConfig.getInt(3  , false), 0);    // double
		assertEquals(listConfig.getInt(4  , false), 0);    // double
		assertEquals(listConfig.getInt(5  , false), 0);    // string
		assertEquals(listConfig.getInt(6  , false), 0);    // null
		assertEquals(listConfig.getInt(7  , false), 0);    // boolean
		assertEquals(listConfig.getInt(726, false), 0);    // out of bounds

		assertEquals(listConfig.getInt(0  , true ), -121); // int

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
