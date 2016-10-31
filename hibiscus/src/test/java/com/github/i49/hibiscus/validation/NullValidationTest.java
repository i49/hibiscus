package com.github.i49.hibiscus.validation;

import static org.junit.Assert.*;
import org.junit.Test;
import java.io.StringReader;
import static com.github.i49.hibiscus.validation.SchemaComponents.*;

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
