package com.github.i49.hibiscus.validation;

import static com.github.i49.hibiscus.schema.SchemaComponents.*;
import static org.junit.Assert.*;
import org.junit.Test;

import com.github.i49.hibiscus.common.TypeId;
import com.github.i49.hibiscus.problems.TypeMismatchProblem;
import com.github.i49.hibiscus.problems.NoSuchEnumeratorProblem;
import com.github.i49.hibiscus.schema.Schema;

import java.io.StringReader;
import java.util.Set;

import javax.json.JsonValue;

public class BooleanValidationTest {

	/**
	 * Tests of various kinds of values.
	 */
	public static class BooleanValueTest extends BaseValidationTest {

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
	}

	public static class TypeMismatchTest extends BaseValidationTest {
	
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
	}
	
	public static class EnumerationTest extends BaseValidationTest {

		@Test
		public void trueToNone() {
			String json = "[true]";
			Schema schema = schema(array(bool().enumeration()));
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
	
			assertEquals(1, result.getProblems().size());
			assertTrue(result.getProblems().get(0) instanceof NoSuchEnumeratorProblem);
			NoSuchEnumeratorProblem p = (NoSuchEnumeratorProblem)result.getProblems().get(0);
			assertEquals(JsonValue.TRUE, p.getActualValue());
			Set<Object> expected = p.getEnumerators();
			assertEquals(0, expected.size());
			assertNotNull(p.getDescription());
		}

		@Test
		public void trueToTrue() {
			String json = "[true]";
			Schema schema = schema(array(bool().enumeration(true)));
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
	
			assertFalse(result.hasProblems());
		}
	
		@Test
		public void trueToFalse() {
			String json = "[true]";
			Schema schema = schema(array(bool().enumeration(false)));
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
	
			assertEquals(1, result.getProblems().size());
			assertTrue(result.getProblems().get(0) instanceof NoSuchEnumeratorProblem);
			NoSuchEnumeratorProblem p = (NoSuchEnumeratorProblem)result.getProblems().get(0);
			assertEquals(JsonValue.TRUE, p.getActualValue());
			Set<Object> expected = p.getEnumerators();
			assertEquals(1, expected.size());
			assertTrue(expected.contains(Boolean.FALSE));
			assertNotNull(p.getDescription());
		}

		@Test
		public void trueToBoth() {
			String json = "[true]";
			Schema schema = schema(array(bool().enumeration(true, false)));
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
	
			assertFalse(result.hasProblems());
		}

		@Test
		public void falseToNone() {
			String json = "[false]";
			Schema schema = schema(array(bool().enumeration()));
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
	
			assertEquals(1, result.getProblems().size());
			assertTrue(result.getProblems().get(0) instanceof NoSuchEnumeratorProblem);
			NoSuchEnumeratorProblem p = (NoSuchEnumeratorProblem)result.getProblems().get(0);
			assertEquals(JsonValue.FALSE, p.getActualValue());
			Set<Object> expected = p.getEnumerators();
			assertEquals(0, expected.size());
			assertNotNull(p.getDescription());
		}

		@Test
		public void falseToTrue() {
			String json = "[false]";
			Schema schema = schema(array(bool().enumeration(true)));
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
	
			assertEquals(1, result.getProblems().size());
			assertTrue(result.getProblems().get(0) instanceof NoSuchEnumeratorProblem);
			NoSuchEnumeratorProblem p = (NoSuchEnumeratorProblem)result.getProblems().get(0);
			assertEquals(JsonValue.FALSE, p.getActualValue());
			Set<Object> expected = p.getEnumerators();
			assertEquals(1, expected.size());
			assertTrue(expected.contains(Boolean.TRUE));
			assertNotNull(p.getDescription());
		}

		@Test
		public void falseToFalse() {
			String json = "[false]";
			Schema schema = schema(array(bool().enumeration(false)));
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
	
			assertFalse(result.hasProblems());
		}

		@Test
		public void falseToBoth() {
			String json = "[false]";
			Schema schema = schema(array(bool().enumeration(true, false)));
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
	
			assertFalse(result.hasProblems());
		}
	}
}
