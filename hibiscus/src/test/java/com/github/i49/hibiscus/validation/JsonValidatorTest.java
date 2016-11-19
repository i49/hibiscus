package com.github.i49.hibiscus.validation;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

import javax.json.JsonObject;
import javax.json.stream.JsonParsingException;

import org.junit.Before;
import org.junit.Test;

import com.github.i49.hibiscus.schema.ObjectType;

import static com.github.i49.hibiscus.schema.JsonTypes.*;
import static com.github.i49.hibiscus.validation.Resources.*;

public class JsonValidatorTest {

	private ObjectType schema;
	
	@Before
	public void setUp() {
		this.schema = object(
				required("firstName", string()),
				required("lastName", string()),
				optional("age", integer()),
				optional("hobbies", array(string()))
			);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void schemaIsNull() {
		try {
			new JsonValidator(null);
		} catch (IllegalArgumentException e) {
			throw e;
		}
	}
	
	@Test
	public void readerIsValid() throws IOException {
		JsonValidator validator = new JsonValidator(schema);
		ValidationResult result = null; 
		try (Reader reader = newReader("person.json")) {
			result = validator.validate(reader);
		}

		assertFalse(result.hasProblems());
		assertTrue(result.getValue() instanceof JsonObject);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void readerIsNull() {
		JsonValidator validator = new JsonValidator(schema);
		try {
			validator.validate(null);
		} catch (IllegalArgumentException e) {
			throw e;
		}
	}

	@Test
	public void inputStreamIsValid() throws IOException {
		JsonValidator validator = new JsonValidator(schema);
		ValidationResult result = null; 
		try (InputStream stream = newInputStream("person.json")) {
			result = validator.validate(stream, StandardCharsets.UTF_8);
		}

		assertFalse(result.hasProblems());
		assertTrue(result.getValue() instanceof JsonObject);
	}

	@Test(expected = IllegalArgumentException.class)
	public void inputStreamIsNull() {
		JsonValidator validator = new JsonValidator(schema);
		try {
			validator.validate(null, StandardCharsets.UTF_8);
		} catch (IllegalArgumentException e) {
			throw e;
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void charsetIsNull() throws IOException {
		JsonValidator validator = new JsonValidator(schema);
		try {
			try (InputStream stream = newInputStream("person.json")) {
				validator.validate(stream, null);
			}
		} catch (IllegalArgumentException e) {
			throw e;
		}
	}

	@Test(expected = JsonParsingException.class)
	public void notWellFormed() throws IOException {
		JsonValidator validator = new JsonValidator(schema);
		try (InputStream stream = newInputStream("person-not-well-formed.json")) {
			validator.validate(stream, StandardCharsets.UTF_8);
		} catch (JsonParsingException e) {
			throw e;
		}
	}
}
