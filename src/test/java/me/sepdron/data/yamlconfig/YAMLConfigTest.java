package me.sepdron.data.yamlconfig;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
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

		listConfig = (YAMLConfig) ((YAMLConfig) topConfig.getObject("map")).getObject("list");
	}

	@Test
	void mapMapping_getObject_String_Test() {
		assertEquals((Integer) topConfig.getObject("int"), 121);
		assertNull(topConfig.getObject("hyd"));
		assertThrowsException(WrongMappingException.NOT_A_LIST_EXCEPTION, () -> topConfig.getObject(0));
	}
	@Test
	void mapMapping_getObject_String_Object_Test() {
		assertEquals((Integer) topConfig.getObject("int", 7), 121);
		assertEquals((Integer) topConfig.getObject("hyd", 7), 7);
	}
	@Test
	void mapMapping_getObject_String_boolean_Test() {
		assertEquals((Integer) topConfig.getObject("int", false), 121);
		assertDoesNotThrow(() -> topConfig.getObject("hyd", false));
		assertEquals((Integer) topConfig.getObject("int", true), 121);
		assertThrows(NoneOfTypeException.class, () -> topConfig.getObject("hyd", true));
	}

	@Test
	void listMapping_getObject_String_Test() {
		assertEquals((Integer) listConfig.getObject(2), 1283);
		assertNull(listConfig.getObject(72));
		assertNull(listConfig.getObject(-19));
		assertThrowsException(WrongMappingException.NOT_A_MAP_EXCEPTION, () -> listConfig.getObject("jsd"));
	}
	@Test
	void listMapping_getObject_String_Object_Test() {
		assertEquals((Integer) listConfig.getObject(2, 7), 1283);
		assertEquals((Integer) listConfig.getObject(72, 7), 7);
		assertEquals((Integer) listConfig.getObject(-19, 7), 7);
	}
	@Test
	void listMapping_getObject_String_boolean_Test() {
		assertEquals((Integer) listConfig.getObject(2, false), 1283);
		assertDoesNotThrow(() -> listConfig.getObject(72, false));
		assertDoesNotThrow(() -> listConfig.getObject(-19, false));
		assertEquals((Integer) listConfig.getObject(2, true), 1283);
		assertThrows(NoneOfTypeException.class, () -> listConfig.getObject(72, true));
		assertThrows(NoneOfTypeException.class, () -> listConfig.getObject(-19, true));
	}

	@Test
	void mapMapping_getString_String_Test() {
		assertEquals(topConfig.getString("string"), "sydtshya6+128");
		assertNull(topConfig.getString("hyd"));
		assertNull(topConfig.getString("int"));
	}
	@Test
	void mapMapping_getString_String_String_Test() {
		assertEquals(topConfig.getString("string", "aaa"), "sydtshya6+128");
		assertEquals(topConfig.getString("hyd", "aaa"), "aaa");
		assertEquals(topConfig.getString("int", "aaa"), "aaa");
	}
	@Test
	void mapMapping_getString_String_boolean_Test() {
		assertEquals(topConfig.getString("string", false), "sydtshya6+128");
		assertDoesNotThrow(() -> topConfig.getString("hyd", false));
		assertDoesNotThrow(() -> topConfig.getString("int", false));
		assertEquals(topConfig.getString("string", true), "sydtshya6+128");
		assertThrows(NoneOfTypeException.class, () -> topConfig.getString("hyd", true));
		assertThrows(NoneOfTypeException.class, () -> topConfig.getString("int", true));
	}

	@Test
	void listMapping_getString_String_Test() {
		assertEquals(listConfig.getString(3), "hsgda");
		assertNull(listConfig.getString(72));
		assertNull(listConfig.getString(2));
	}
	@Test
	void listMapping_getString_String_String_Test() {
		assertEquals(listConfig.getString(3, "aaa"), "hsgda");
		assertEquals(listConfig.getString(72, "aaa"), "aaa");
		assertEquals(listConfig.getString(2, "aaa"), "aaa");
	}
	@Test
	void listMapping_getString_String_boolean_Test() {
		assertEquals(listConfig.getString(3, false), "hsgda");
		assertDoesNotThrow(() -> listConfig.getString(72, false));
		assertDoesNotThrow(() -> listConfig.getString(2, false));
		assertEquals(listConfig.getString(3, true), "hsgda");
		assertThrows(NoneOfTypeException.class, () -> listConfig.getString(72, true));
		assertThrows(NoneOfTypeException.class, () -> listConfig.getString(2, true));
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
