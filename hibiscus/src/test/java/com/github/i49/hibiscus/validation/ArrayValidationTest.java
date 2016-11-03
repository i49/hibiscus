package com.github.i49.hibiscus.validation;

import static com.github.i49.schema.types.SchemaComponents.*;
import static org.junit.Assert.*;
import org.junit.Test;

import com.github.i49.schema.problems.ArraySizeProblem;
import com.github.i49.schema.types.ValueType;

import java.io.StringReader;

public class ArrayValidationTest {

	@Test
	public void testEmptyArray() {
		String json = "[]";
		ValueType schema = array();
		JsonValidator validator = new JsonValidator(schema);
		ValidationResult result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}

	@Test
	public void testBooleans() {
		String json = "[true, false, true]";
		ValueType schema = array(bool());
		JsonValidator validator = new JsonValidator(schema);
		ValidationResult result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}

	@Test
	public void testIntegers() {
		String json = "[1, 2, 3, 4, 5]";
		ValueType schema = array(integer());
		JsonValidator validator = new JsonValidator(schema);
		ValidationResult result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}

	@Test
	public void testNumbers() {
		String json = "[1.2, 3.4, 5.6]";
		ValueType schema = array(number());
		JsonValidator validator = new JsonValidator(schema);
		ValidationResult result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}

	@Test
	public void testNulls() {
		String json = "[null, null, null]";
		ValueType schema = array(nullValue());
		JsonValidator validator = new JsonValidator(schema);
		ValidationResult result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}

	@Test
	public void testStrings() {
		String json = "[\"abc\", \"xyz\", \"123\"]";
		ValueType schema = array(string());
		JsonValidator validator = new JsonValidator(schema);
		ValidationResult result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}

	@Test
	public void testMixed() {
		String json = "[123, \"abc\", 456, \"xyz\"]";
		ValueType schema = array(integer(), string());
		JsonValidator validator = new JsonValidator(schema);
		ValidationResult result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}
	
	@Test
	public void testArrays() {
		String json = "[[1, 2, 3], [4, 5, 6]]";
		ValueType schema = array(array(integer()));
		JsonValidator validator = new JsonValidator(schema);
		ValidationResult result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}
	
	@Test
	public void testObjects() {
		String json = "[{}, {}, {}]";
		ValueType schema = array(object());
		JsonValidator validator = new JsonValidator(schema);
		ValidationResult result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}
	
	@Test
	public void testMinItems() {
		String json = "[1, 2, 3]";
		ValueType schema = array(integer()).minItems(3);
		JsonValidator validator = new JsonValidator(schema);
		ValidationResult result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}

	@Test
	public void testMinItems2() {
		String json = "[1, 2]";
		ValueType schema = array(integer()).minItems(3);
		JsonValidator validator = new JsonValidator(schema);
		ValidationResult result = validator.validate(new StringReader(json));

		assertEquals(1, result.getProblems().size());
		assertTrue(result.getProblems().get(0) instanceof ArraySizeProblem);
		ArraySizeProblem p = (ArraySizeProblem)result.getProblems().get(0);
		assertEquals(3, p.getThreshold());
		assertEquals(2, p.getActualSize());
	}

	@Test
	public void testMaxItems() {
		String json = "[1, 2, 3, 4]";
		ValueType schema = array(integer()).maxItems(4);
		JsonValidator validator = new JsonValidator(schema);
		ValidationResult result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}

	@Test
	public void testMaxItems2() {
		String json = "[1, 2, 3, 4, 5]";
		ValueType schema = array(integer()).maxItems(4);
		JsonValidator validator = new JsonValidator(schema);
		ValidationResult result = validator.validate(new StringReader(json));

		assertEquals(1, result.getProblems().size());
		assertTrue(result.getProblems().get(0) instanceof ArraySizeProblem);
		ArraySizeProblem p = (ArraySizeProblem)result.getProblems().get(0);
		assertEquals(4, p.getThreshold());
		assertEquals(5, p.getActualSize());
	}
	
	@Test
	public void testMinAndMaxItems() {
		String json = "[1, 2, 3, 4]";
		ValueType schema = array(integer()).minItems(3).maxItems(4);
		JsonValidator validator = new JsonValidator(schema);
		ValidationResult result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}
}
