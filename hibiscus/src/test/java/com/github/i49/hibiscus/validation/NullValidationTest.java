package com.github.i49.hibiscus.validation;

import static com.github.i49.hibiscus.schema.types.JsonTypes.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.i49.hibiscus.schema.TypeId;
import com.github.i49.hibiscus.schema.problems.Problem;
import com.github.i49.hibiscus.schema.problems.TypeMismatchProblem;
import com.github.i49.hibiscus.schema.types.JsonType;

import java.io.StringReader;

public class NullValidationTest {
	
	private static Logger log = LoggerFactory.getLogger(NullValidationTest.class);

	@Test
	public void testValidateNull() {
		String json = "[null]";
		JsonType schema = array(nullValue());
		JsonValidator validator = new JsonValidator(schema);
		ValidationResult result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}

	@Test
	public void testTypeMismatch() {
		String json = "[0]";
		JsonType schema = array(bool());
		JsonValidator validator = new JsonValidator(schema);
		ValidationResult result = validator.validate(new StringReader(json));

		assertEquals(1, result.getProblems().size());
		Problem p = result.getProblems().get(0);
		assertTrue(p instanceof TypeMismatchProblem);
		assertEquals(TypeId.INTEGER, ((TypeMismatchProblem)p).getInstanceType());
		log.debug(p.toString());
	}
}
