package com.github.i49.hibiscus;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

import javax.json.JsonObject;
import javax.json.JsonStructure;

import org.junit.Test;

import static com.github.i49.hibiscus.SchemaObjects.*;

public class JsonValidatorTest {

	@Test
	public void test() throws Exception {

		JsonValidator v = createPersonValidator();
		JsonStructure root = null;
		try (Reader reader = openReader("person.json")) {
			root = v.validate(reader);
		}

		assertTrue(root instanceof JsonObject);
		JsonObject object = (JsonObject)root;
		assertEquals("Jason", object.getString("firstName"));
		assertEquals("Bourne", object.getString("lastName"));
		assertEquals(46, object.getInt("age"));
	}
	
	private JsonValidator createPersonValidator() {
		ObjectType rootType = object()
				.properties(
					required("firstName", string()),
					required("lastName", string()),
					optional("age", number())
				);
		return new JsonValidator(rootType);
	}

	private static Reader openReader(String name) {
		InputStream stream = JsonValidatorTest.class.getResourceAsStream(name);
		return new InputStreamReader(stream, StandardCharsets.UTF_8);
	}
}
