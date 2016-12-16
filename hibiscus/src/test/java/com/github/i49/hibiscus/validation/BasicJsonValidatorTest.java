package com.github.i49.hibiscus.validation;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

import javax.json.JsonObject;
import javax.json.JsonString;
import javax.json.stream.JsonParsingException;

import org.junit.Test;

import com.github.i49.hibiscus.schema.Schema;

import static com.github.i49.hibiscus.schema.SchemaComponents.*;
import static com.github.i49.hibiscus.validation.Resources.*;

public class BasicJsonValidatorTest {

	private static Schema personSchema() {
		return schema(
			object(
				required("firstName", string()),
				required("lastName", string()),
				optional("age", integer()),
				optional("hobbies", array(string()))
			)
		);
	}
	
	public static class ConstructorTest {

		@Test(expected = IllegalArgumentException.class)
		public void schemaIsNull() {
			new BasicJsonValidator(null);
		}
	}
	
	public static class ReaderTest {

		@Test
		public void readerIsValid() throws IOException {
			JsonValidator validator = new BasicJsonValidator(personSchema());
			ValidationResult result = null; 
			try (Reader reader = newReader("person.json")) {
				result = validator.validate(reader);
			}
		
			assertFalse(result.hasProblems());
			assertTrue(result.getValue() instanceof JsonObject);
		}
		
		@Test(expected = IllegalArgumentException.class)
		public void readerIsNull() {
			JsonValidator validator = new BasicJsonValidator(personSchema());
			Reader reader = null;
			validator.validate(reader);
		}
	}
	
	public static class InputStreamTest {
	
		@Test
		public void utf16le() throws IOException {
			JsonValidator validator = new BasicJsonValidator(personSchema());
			ValidationResult result = null; 
			try (InputStream stream = newInputStream("person-utf16le.json")) {
				result = validator.validate(stream);
			}
			assertFalse(result.hasProblems());
			assertTrue(result.getValue() instanceof JsonObject);
		}

		@Test
		public void utf16be() throws IOException {
			JsonValidator validator = new BasicJsonValidator(personSchema());
			ValidationResult result = null; 
			try (InputStream stream = newInputStream("person-utf16be.json")) {
				result = validator.validate(stream);
			}
			assertFalse(result.hasProblems());
			assertTrue(result.getValue() instanceof JsonObject);
		}
	}
	
	public static class InputStreamWithCharsetTest {

		@Test
		public void inputStreamIsValid() throws IOException {
			JsonValidator validator = new BasicJsonValidator(personSchema());
			ValidationResult result = null; 
			try (InputStream stream = newInputStream("person.json")) {
				result = validator.validate(stream, StandardCharsets.UTF_8);
			}
	
			assertFalse(result.hasProblems());
			assertTrue(result.getValue() instanceof JsonObject);
		}
	
		@Test(expected = IllegalArgumentException.class)
		public void inputStreamIsNull() {
			JsonValidator validator = new BasicJsonValidator(personSchema());
			InputStream stream = null;
			validator.validate(stream, StandardCharsets.UTF_8);
		}
	
		@Test(expected = IllegalArgumentException.class)
		public void charsetIsNull() throws IOException {
			JsonValidator validator = new BasicJsonValidator(personSchema());
			try (InputStream stream = newInputStream("person.json")) {
				validator.validate(stream, null);
			}
		}
	}
	
	public static class NotWellFormedTest {

		@Test(expected = JsonParsingException.class)
		public void notWellFormed() throws IOException {
			JsonValidator validator = new BasicJsonValidator(personSchema());
			try (InputStream stream = newInputStream("person-not-well-formed.json")) {
				validator.validate(stream, StandardCharsets.UTF_8);
			} catch (Exception e) {
				throw e;
			}
		}

		@Test(expected = JsonParsingException.class)
		public void empty() throws IOException {
			JsonValidator validator = new BasicJsonValidator(personSchema());
			try (InputStream stream = newInputStream("empty.json")) {
				validator.validate(stream, StandardCharsets.UTF_8);
			} catch (Exception e) {
				throw e;
			}
		}

		@Test
		public void invalidRoot() throws IOException {
			JsonValidator validator = new BasicJsonValidator(schema(string()));
			ValidationResult result = null; 
			try (InputStream stream = newInputStream("invalid-root.json")) {
				result = validator.validate(stream, StandardCharsets.UTF_8);
				assertFalse(result.hasProblems());
				assertTrue(result.getValue() instanceof JsonString);
			} catch (JsonParsingException e) {
				// We ignore the exception thrown by the reference implementation.
			}
		}
	}
}
