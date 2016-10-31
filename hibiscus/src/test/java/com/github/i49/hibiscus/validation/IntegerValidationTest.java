package com.github.i49.hibiscus.validation;

import static org.junit.Assert.*;
import org.junit.Test;
import java.io.StringReader;
import static com.github.i49.hibiscus.validation.SchemaComponents.*;

public class IntegerValidationTest {

	@Test
	public void testValiadteInteger() {
		String json = "[123]";
		ValueType schema = array(integer());
		JsonValidator validator = new JsonValidator(schema);
		ValidationResult result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}
	
	@Test
	public void testValiadteMaxInt() {
		String json = "[2147483647]";
		ValueType schema = array(integer());
		JsonValidator validator = new JsonValidator(schema);
		ValidationResult result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}

	@Test
	public void testValiadteMinInt() {
		String json = "[-2147483648]";
		ValueType schema = array(integer());
		JsonValidator validator = new JsonValidator(schema);
		ValidationResult result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}

	@Test
	public void testValiadteMaxLong() {
		String json = "[9223372036854775807]";
		ValueType schema = array(integer());
		JsonValidator validator = new JsonValidator(schema);
		ValidationResult result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}

	@Test
	public void testValiadteMinLong() {
		String json = "[-9223372036854775808]";
		ValueType schema = array(integer());
		JsonValidator validator = new JsonValidator(schema);
		ValidationResult result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}
	
	@Test
	public void testValiadteNumber() {
		String json = "[123.45]";
		ValueType schema = array(integer());
		JsonValidator validator = new JsonValidator(schema);
		ValidationResult result = validator.validate(new StringReader(json));

		assertEquals(1, result.getProblems().size());
		Problem p = result.getProblems().get(0);
		assertTrue(p instanceof TypeMismatchProblem);
		assertEquals(TypeId.NUMBER, ((TypeMismatchProblem)p).getActualType());
	}

	@Test
	public void testTypeMismatch() {
		String json = "[\"123\"]";
		ValueType schema = array(integer());
		JsonValidator validator = new JsonValidator(schema);
		ValidationResult result = validator.validate(new StringReader(json));

		assertEquals(1, result.getProblems().size());
		Problem p = result.getProblems().get(0);
		assertTrue(p instanceof TypeMismatchProblem);
		assertEquals(TypeId.STRING, ((TypeMismatchProblem)p).getActualType());
	}
}
