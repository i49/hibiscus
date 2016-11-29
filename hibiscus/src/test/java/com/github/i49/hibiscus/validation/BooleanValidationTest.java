package com.github.i49.hibiscus.validation;

import static com.github.i49.hibiscus.schema.SchemaComponents.*;
import static org.junit.Assert.*;
import org.junit.Test;

import com.github.i49.hibiscus.common.TypeId;
import com.github.i49.hibiscus.problems.TypeMismatchProblem;
import com.github.i49.hibiscus.problems.UnknownValueProblem;
import com.github.i49.hibiscus.schema.Schema;

import java.io.StringReader;
import java.util.Set;

import javax.json.JsonValue;

public class BooleanValidationTest extends BaseValidationTest {

	@Test
	public void booleanOfTrue() {
		String json = "[true]";
		Schema schema = schema(array(bool()));
		JsonValidator validator = new BasicJsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}

	@Test
	public void booleanOfFalse() {
		String json = "[false]";
		Schema schema = schema(array(bool()));
		JsonValidator validator = new BasicJsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}

	@Test
	public void notBooleanButString() {
		String json = "[\"true\"]";
		Schema schema = schema(array(bool()));
		JsonValidator validator = new BasicJsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertEquals(1, result.getProblems().size());
		assertTrue(result.getProblems().get(0) instanceof TypeMismatchProblem);
		TypeMismatchProblem p = (TypeMismatchProblem)result.getProblems().get(0);
		assertEquals(TypeId.STRING, p.getActualType());
		assertNotNull(p.getDescription());
	}
	
	public static class EnumerationTest extends BaseValidationTest {

		@Test
		public void trueOfNone() {
			String json = "[true]";
			Schema schema = schema(array(bool().enumeration()));
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
	
			assertEquals(1, result.getProblems().size());
			assertTrue(result.getProblems().get(0) instanceof UnknownValueProblem);
			UnknownValueProblem p = (UnknownValueProblem)result.getProblems().get(0);
			assertEquals(JsonValue.TRUE, p.getActualValue());
			Set<JsonValue> expected = p.getExpectedValues();
			assertEquals(0, expected.size());
			assertNotNull(p.getDescription());
		}

		@Test
		public void trueOfTrue() {
			String json = "[true]";
			Schema schema = schema(array(bool().enumeration(true)));
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
	
			assertFalse(result.hasProblems());
		}
	
		@Test
		public void trueOfFalse() {
			String json = "[true]";
			Schema schema = schema(array(bool().enumeration(false)));
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
	
			assertEquals(1, result.getProblems().size());
			assertTrue(result.getProblems().get(0) instanceof UnknownValueProblem);
			UnknownValueProblem p = (UnknownValueProblem)result.getProblems().get(0);
			assertEquals(JsonValue.TRUE, p.getActualValue());
			Set<JsonValue> expected = p.getExpectedValues();
			assertEquals(1, expected.size());
			assertTrue(expected.contains(JsonValue.FALSE));
			assertNotNull(p.getDescription());
		}

		@Test
		public void trueOfBoth() {
			String json = "[true]";
			Schema schema = schema(array(bool().enumeration(true, false)));
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
	
			assertFalse(result.hasProblems());
		}

		@Test
		public void falseOfNone() {
			String json = "[false]";
			Schema schema = schema(array(bool().enumeration()));
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
	
			assertEquals(1, result.getProblems().size());
			assertTrue(result.getProblems().get(0) instanceof UnknownValueProblem);
			UnknownValueProblem p = (UnknownValueProblem)result.getProblems().get(0);
			assertEquals(JsonValue.FALSE, p.getActualValue());
			Set<JsonValue> expected = p.getExpectedValues();
			assertEquals(0, expected.size());
			assertNotNull(p.getDescription());
		}

		@Test
		public void falseOfTrue() {
			String json = "[false]";
			Schema schema = schema(array(bool().enumeration(true)));
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
	
			assertEquals(1, result.getProblems().size());
			assertTrue(result.getProblems().get(0) instanceof UnknownValueProblem);
			UnknownValueProblem p = (UnknownValueProblem)result.getProblems().get(0);
			assertEquals(JsonValue.FALSE, p.getActualValue());
			Set<JsonValue> expected = p.getExpectedValues();
			assertEquals(1, expected.size());
			assertTrue(expected.contains(JsonValue.TRUE));
			assertNotNull(p.getDescription());
		}

		@Test
		public void falseOfFalse() {
			String json = "[false]";
			Schema schema = schema(array(bool().enumeration(false)));
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
	
			assertFalse(result.hasProblems());
		}

		@Test
		public void falseOfBoth() {
			String json = "[false]";
			Schema schema = schema(array(bool().enumeration(true, false)));
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
	
			assertFalse(result.hasProblems());
		}
	}
}
