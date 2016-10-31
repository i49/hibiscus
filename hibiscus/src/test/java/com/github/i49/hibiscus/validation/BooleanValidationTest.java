package com.github.i49.hibiscus.validation;

import static org.junit.Assert.*;
import org.junit.Test;
import java.io.StringReader;
import static com.github.i49.hibiscus.validation.SchemaComponents.*;

public class BooleanValidationTest {

	@Test
	public void testValidateTrue() {
		String json = "[true]";
		ValueType schema = array(bool());
		JsonValidator validator = new JsonValidator(schema);
		ValidationResult result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}

	@Test
	public void testValidateFalse() {
		String json = "[false]";
		ValueType schema = array(bool());
		JsonValidator validator = new JsonValidator(schema);
		ValidationResult result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}

	@Test
	public void testTypeMismatch() {
		String json = "[\"true\"]";
		ValueType schema = array(bool());
		JsonValidator validator = new JsonValidator(schema);
		ValidationResult result = validator.validate(new StringReader(json));

		assertEquals(1, result.getProblems().size());
		Problem p = result.getProblems().get(0);
		assertTrue(p instanceof TypeMismatchProblem);
		assertEquals(TypeId.STRING, ((TypeMismatchProblem)p).getActualType());
	}
}
