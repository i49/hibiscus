package com.github.i49.hibiscus.validation;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

import javax.json.JsonObject;

import org.junit.Before;
import org.junit.Test;

import com.github.i49.hibiscus.schema.types.ObjectType;
import static com.github.i49.hibiscus.schema.types.SchemaComponents.*;

public class JsonValidatorTest {

	private JsonValidator validator;
	
	@Before
	public void setUp() {

		ObjectType schema = object(
				required("firstName", string()),
				required("lastName", string()),
				optional("age", integer()),
				optional("hobbies", array(string()))
			);

		validator = new JsonValidator(schema);
	}
	
	@Test
	public void testValidateJsonFromReader() throws IOException {
		ValidationResult result = null; 
		try (Reader reader = openReader("person.json")) {
			result = validator.validate(reader);
		}

		assertFalse(result.hasProblems());
		assertTrue(result.getValue() instanceof JsonObject);
	}

	@Test
	public void testValidateJsonFromInputStream() throws IOException {
		ValidationResult result = null; 
		try (InputStream stream = openStream("person.json")) {
			result = validator.validate(stream, StandardCharsets.UTF_8);
		}

		assertFalse(result.hasProblems());
		assertTrue(result.getValue() instanceof JsonObject);
	}
	
	private static InputStream openStream(String name) {
		return JsonValidatorTest.class.getResourceAsStream(name);
	}
	
	private static Reader openReader(String name) {
		InputStream stream = openStream(name);
		return new InputStreamReader(stream, StandardCharsets.UTF_8);
	}
}
