package com.github.i49.hibiscus.validation;

import static com.github.i49.hibiscus.validation.SchemaComponents.*;
import static org.junit.Assert.*;

import java.io.StringReader;

import org.junit.Test;

import com.github.i49.hibiscus.validation.JsonValidator;
import com.github.i49.hibiscus.validation.ValidationResult;
import com.github.i49.hibiscus.validation.ValueType;

public class ObjectValiadtionTest {

	@Test
	public void testEmptyOject() {
		String json = "{}";
		ValueType schema = object();
		JsonValidator validator = new JsonValidator(schema);
		ValidationResult result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}
}
