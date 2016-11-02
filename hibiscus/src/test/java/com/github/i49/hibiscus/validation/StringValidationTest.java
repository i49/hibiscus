package com.github.i49.hibiscus.validation;

import static org.junit.Assert.*;

import java.io.StringReader;

import org.junit.Test;

import com.github.i49.schema.TypeId;
import com.github.i49.schema.problems.Problem;
import com.github.i49.schema.problems.TypeMismatchProblem;
import com.github.i49.schema.types.ValueType;

import static com.github.i49.hibiscus.validation.SchemaComponents.*;

public class StringValidationTest {

	@Test
	public void testValidateString() {
		String json = "[\"abc\"]";
		ValueType schema = array(string());
		JsonValidator validator = new JsonValidator(schema);
		ValidationResult result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}

	@Test
	public void testValidateBlankString() {
		String json = "[\"\"]";
		ValueType schema = array(string());
		JsonValidator validator = new JsonValidator(schema);
		ValidationResult result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}

	@Test
	public void testTypeMismatch() {
		String json = "[123]";
		ValueType schema = array(string());
		JsonValidator validator = new JsonValidator(schema);
		ValidationResult result = validator.validate(new StringReader(json));

		assertEquals(1, result.getProblems().size());
		Problem p = result.getProblems().get(0);
		assertTrue(p instanceof TypeMismatchProblem);
		assertEquals(TypeId.INTEGER, ((TypeMismatchProblem)p).getActualType());
	}
}
