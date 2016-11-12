package com.github.i49.hibiscus.validation;

import static com.github.i49.hibiscus.schema.types.JsonTypes.*;
import static org.junit.Assert.*;
import org.junit.Test;

import com.github.i49.hibiscus.schema.TypeId;
import com.github.i49.hibiscus.schema.problems.TypeMismatchProblem;
import com.github.i49.hibiscus.schema.problems.UnknownValueProblem;
import com.github.i49.hibiscus.schema.types.JsonType;

import java.io.StringReader;
import java.util.Set;

import javax.json.JsonValue;

public class BooleanValidationTest extends BaseValidationTest {

	@Test
	public void booleanOfTrue() {
		String json = "[true]";
		JsonType schema = array(bool());
		JsonValidator validator = new JsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}

	@Test
	public void booleanOfFalse() {
		String json = "[false]";
		JsonType schema = array(bool());
		JsonValidator validator = new JsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}

	@Test
	public void notBooleanButString() {
		String json = "[\"true\"]";
		JsonType schema = array(bool());
		JsonValidator validator = new JsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertEquals(1, result.getProblems().size());
		assertTrue(result.getProblems().get(0) instanceof TypeMismatchProblem);
		TypeMismatchProblem p = (TypeMismatchProblem)result.getProblems().get(0);
		assertEquals(TypeId.STRING, p.getActualType());
		assertNotNull(p.getMessage());
	}
	
	@Test
	public void booleanOfAllowedValue() {
		String json = "[true]";
		JsonType schema = array(bool().values(true));
		JsonValidator validator = new JsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}

	@Test
	public void booleanOfNotAllowedValue() {
		String json = "[false]";
		JsonType schema = array(bool().values(true));
		JsonValidator validator = new JsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertEquals(1, result.getProblems().size());
		assertTrue(result.getProblems().get(0) instanceof UnknownValueProblem);
		UnknownValueProblem p = (UnknownValueProblem)result.getProblems().get(0);
		assertEquals(JsonValue.FALSE, p.getActualValue());
		Set<JsonValue> expected = p.getExpectedValues();
		assertEquals(1, expected.size());
		assertNotNull(p.getMessage());
	}
}
