package com.github.i49.hibiscus.validation;

import static com.github.i49.hibiscus.validation.SchemaComponents.*;
import static org.junit.Assert.*;

import java.io.StringReader;

import org.junit.Test;

import com.github.i49.hibiscus.validation.JsonValidator;
import com.github.i49.hibiscus.validation.ValidationResult;
import com.github.i49.hibiscus.validation.ValueType;

public class NullValidationTest {

	@Test
	public void testValidateNull() {
		String json = "[null]";
		ValueType schema = array(nullValue());
		JsonValidator validator = new JsonValidator(schema);
		ValidationResult result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}

	@Test
	public void testTypeMismatch() {
		String json = "[0]";
		ValueType schema = array(bool());
		JsonValidator validator = new JsonValidator(schema);
		ValidationResult result = validator.validate(new StringReader(json));

		assertEquals(1, result.getProblems().size());
		Problem p = result.getProblems().get(0);
		assertTrue(p instanceof TypeMismatchProblem);
		assertEquals(TypeId.INTEGER, ((TypeMismatchProblem)p).getActualType());
	}
}
