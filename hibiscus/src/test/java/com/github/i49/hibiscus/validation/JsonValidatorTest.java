package com.github.i49.hibiscus.validation;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

import javax.json.JsonObject;

import org.junit.Before;
import org.junit.Test;

import com.github.i49.hibiscus.schema.ObjectType;

import static com.github.i49.hibiscus.schema.JsonTypes.*;
import static com.github.i49.hibiscus.validation.Resources.*;

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
	public void jsonFromReader() throws IOException {
		ValidationResult result = null; 
		try (Reader reader = newReader("person.json")) {
			result = validator.validate(reader);
		}

		assertFalse(result.hasProblems());
		assertTrue(result.getValue() instanceof JsonObject);
	}

	@Test
	public void jsonFromInputStream() throws IOException {
		ValidationResult result = null; 
		try (InputStream stream = newInputStream("person.json")) {
			result = validator.validate(stream, StandardCharsets.UTF_8);
		}

		assertFalse(result.hasProblems());
		assertTrue(result.getValue() instanceof JsonObject);
	}
}
