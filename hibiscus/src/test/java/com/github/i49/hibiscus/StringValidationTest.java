package com.github.i49.hibiscus;

import static org.junit.Assert.*;

import java.io.StringReader;

import org.junit.Test;

import static com.github.i49.hibiscus.SchemaComponents.*;

public class StringValidationTest {

	@Test
	public void testValidate() {
		String json =  "[\"abc\"]";
		ValueType schema = array(string());
		JsonValidator validator = new JsonValidator(schema);
		ValidationResult result = validator.validate(new StringReader(json));
		assertFalse(result.hasProblems());
	}

	@Test
	public void testValidateBlank() {
		String json =  "[\"\"]";
		ValueType schema = array(string());
		JsonValidator validator = new JsonValidator(schema);
		ValidationResult result = validator.validate(new StringReader(json));
		assertFalse(result.hasProblems());
	}
}
