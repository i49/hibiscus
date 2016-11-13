package com.github.i49.hibiscus.validation;

import static com.github.i49.hibiscus.schema.JsonTypes.*;
import static org.junit.Assert.*;
import org.junit.Test;

import com.github.i49.hibiscus.problems.ArrayTooLongProblem;
import com.github.i49.hibiscus.problems.ArrayTooShortProblem;
import com.github.i49.hibiscus.schema.JsonType;

import java.io.StringReader;

public class ArrayValidationTest extends BaseValidationTest {

	@Test
	public void emptyArray() {
		String json = "[]";
		JsonType schema = array();
		JsonValidator validator = new JsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}

	@Test
	public void arrayOfBooleans() {
		String json = "[true, false, true]";
		JsonType schema = array(bool());
		JsonValidator validator = new JsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}

	@Test
	public void arrayOfIntegers() {
		String json = "[1, 2, 3, 4, 5]";
		JsonType schema = array(integer());
		JsonValidator validator = new JsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}

	@Test
	public void arrayOfNumbers() {
		String json = "[1.2, 3.4, 5.6]";
		JsonType schema = array(number());
		JsonValidator validator = new JsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}

	@Test
	public void arrayOfNulls() {
		String json = "[null, null, null]";
		JsonType schema = array(nullValue());
		JsonValidator validator = new JsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}

	@Test
	public void arrayOfStrings() {
		String json = "[\"abc\", \"xyz\", \"123\"]";
		JsonType schema = array(string());
		JsonValidator validator = new JsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}

	@Test
	public void arrayOfMixiedItems() {
		String json = "[123, \"abc\", 456, \"xyz\"]";
		JsonType schema = array(integer(), string());
		JsonValidator validator = new JsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}
	
	@Test
	public void arrayOfArrays() {
		String json = "[[1, 2, 3], [4, 5, 6]]";
		JsonType schema = array(array(integer()));
		JsonValidator validator = new JsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}
	
	@Test
	public void arrayOfObjects() {
		String json = "[{}, {}, {}]";
		JsonType schema = array(object());
		JsonValidator validator = new JsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}
	
	@Test
	public void arrayOfMinItems() {
		String json = "[1, 2, 3]";
		JsonType schema = array(integer()).minItems(3);
		JsonValidator validator = new JsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}

	@Test
	public void arrayOfItemsMoreThanMin() {
		String json = "[1, 2, 3, 4]";
		JsonType schema = array(integer()).minItems(3);
		JsonValidator validator = new JsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}

	@Test
	public void arrayOfItemsLessThanMin() {
		String json = "[1, 2]";
		JsonType schema = array(integer()).minItems(3);
		JsonValidator validator = new JsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertEquals(1, result.getProblems().size());
		assertTrue(result.getProblems().get(0) instanceof ArrayTooShortProblem);
		ArrayTooShortProblem p = (ArrayTooShortProblem)result.getProblems().get(0);
		assertEquals(2, p.getActualSize());
		assertEquals(3, p.getExpectedRange().getMinimum());
		assertNotNull(p.getMessage());
	}

	@Test
	public void arrayOfMaxItems() {
		String json = "[1, 2, 3, 4]";
		JsonType schema = array(integer()).maxItems(4);
		JsonValidator validator = new JsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}

	@Test
	public void arrayOfItemsLessThanMax() {
		String json = "[1, 2, 3]";
		JsonType schema = array(integer()).maxItems(4);
		JsonValidator validator = new JsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}
	
	/*
	 * Problem that the array is too long than required.
	 */
	@Test
	public void arrayOfItemsMoreThanMax() {
		String json = "[1, 2, 3, 4, 5]";
		JsonType schema = array(integer()).maxItems(4);
		JsonValidator validator = new JsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertEquals(1, result.getProblems().size());
		assertTrue(result.getProblems().get(0) instanceof ArrayTooLongProblem);
		ArrayTooLongProblem p = (ArrayTooLongProblem)result.getProblems().get(0);
		assertEquals(5, p.getActualSize());
		assertEquals(4, p.getExpectedRange().getMaximum());
		assertNotNull(p.getMessage());
	}
	
	@Test
	public void arrayOfItemsBetweenMinAndMax() {
		String json = "[1, 2, 3, 4]";
		JsonType schema = array(integer()).minItems(3).maxItems(4);
		JsonValidator validator = new JsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}
}
