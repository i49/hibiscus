package com.github.i49.hibiscus.validation;

import static com.github.i49.hibiscus.schema.JsonTypes.*;
import static org.junit.Assert.*;

import org.junit.Test;

import com.github.i49.hibiscus.common.TypeId;
import com.github.i49.hibiscus.problems.Problem;
import com.github.i49.hibiscus.problems.TypeMismatchProblem;
import com.github.i49.hibiscus.schema.ComplexType;

import java.io.StringReader;

public class NullValidationTest extends BaseValidationTest {
	
	@Test
	public void normalNull() {
		String json = "[null]";
		ComplexType schema = array(nullValue());
		JsonValidator validator = new BasicJsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}

	@Test
	public void notNullButInteger() {
		String json = "[0]";
		ComplexType schema = array(bool());
		JsonValidator validator = new BasicJsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertEquals(1, result.getProblems().size());
		Problem p = result.getProblems().get(0);
		assertTrue(p instanceof TypeMismatchProblem);
		assertEquals(TypeId.INTEGER, ((TypeMismatchProblem)p).getActualType());
		assertNotNull(p.getMessage());
	}
}
