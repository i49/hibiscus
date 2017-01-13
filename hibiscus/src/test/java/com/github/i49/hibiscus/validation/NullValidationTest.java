package com.github.i49.hibiscus.validation;

import static com.github.i49.hibiscus.schema.SchemaComponents.*;
import static org.junit.Assert.*;

import org.junit.Test;

import com.github.i49.hibiscus.common.TypeId;
import com.github.i49.hibiscus.problems.Problem;
import com.github.i49.hibiscus.problems.TypeMismatchProblem;
import com.github.i49.hibiscus.schema.Schema;

import java.io.StringReader;

import static com.github.i49.hibiscus.validation.CustomAssertions.*;

public class NullValidationTest {
	
	public static class NullValueTest {

		@Test
		public void matchNull() {
			String json = "[null]";
			Schema schema = schema(array(nil()));
			JsonValidator validator = new BasicJsonValidator(schema);
			ValidationResult result = validator.validate(new StringReader(json));
	
			assertResultValid(result, json);
			assertFalse(result.hasProblems());
		}
	}
	
	public static class TypeMismatchTest {
	
		@Test
		public void notNullButInteger() {
			String json = "[0]";
			Schema schema = schema(array(bool()));
			JsonValidator validator = new BasicJsonValidator(schema);
			ValidationResult result = validator.validate(new StringReader(json));
	
			assertResultValid(result, json);
			assertEquals(1, result.getProblems().size());
			Problem p = result.getProblems().get(0);
			assertTrue(p instanceof TypeMismatchProblem);
			assertEquals(TypeId.INTEGER, ((TypeMismatchProblem)p).getActualType());
			assertNotNull(p.getDescription());
		}
	}
}
