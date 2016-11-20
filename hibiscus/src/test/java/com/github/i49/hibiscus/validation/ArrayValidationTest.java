package com.github.i49.hibiscus.validation;

import static com.github.i49.hibiscus.schema.JsonTypes.*;
import static org.junit.Assert.*;
import org.junit.Test;

import com.github.i49.hibiscus.problems.ArrayTooLongProblem;
import com.github.i49.hibiscus.problems.ArrayTooShortProblem;
import com.github.i49.hibiscus.schema.ComplexType;

import java.io.StringReader;

public class ArrayValidationTest extends BaseValidationTest {

	@Test
	public void emptyArray() {
		String json = "[]";
		ComplexType schema = array();
		JsonValidator validator = new BasicJsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}

	@Test
	public void arrayOfBooleans() {
		String json = "[true, false, true]";
		ComplexType schema = array(bool());
		JsonValidator validator = new BasicJsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}

	@Test
	public void arrayOfIntegers() {
		String json = "[1, 2, 3, 4, 5]";
		ComplexType schema = array(integer());
		JsonValidator validator = new BasicJsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}

	@Test
	public void arrayOfNumbers() {
		String json = "[1.2, 3.4, 5.6]";
		ComplexType schema = array(number());
		JsonValidator validator = new BasicJsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}

	@Test
	public void arrayOfNulls() {
		String json = "[null, null, null]";
		ComplexType schema = array(nullValue());
		JsonValidator validator = new BasicJsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}

	@Test
	public void arrayOfStrings() {
		String json = "[\"abc\", \"xyz\", \"123\"]";
		ComplexType schema = array(string());
		JsonValidator validator = new BasicJsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}

	@Test
	public void arrayOfMixiedItems() {
		String json = "[123, \"abc\", 456, \"xyz\"]";
		ComplexType schema = array(integer(), string());
		JsonValidator validator = new BasicJsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}
	
	@Test
	public void arrayOfArrays() {
		String json = "[[1, 2, 3], [4, 5, 6]]";
		ComplexType schema = array(array(integer()));
		JsonValidator validator = new BasicJsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}
	
	@Test
	public void arrayOfObjects() {
		String json = "[{}, {}, {}]";
		ComplexType schema = array(object());
		JsonValidator validator = new BasicJsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}
	
	@Test
	public void arrayOfMinItems() {
		String json = "[1, 2, 3]";
		ComplexType schema = array(integer()).minItems(3);
		JsonValidator validator = new BasicJsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}

	@Test
	public void arrayOfItemsMoreThanMin() {
		String json = "[1, 2, 3, 4]";
		ComplexType schema = array(integer()).minItems(3);
		JsonValidator validator = new BasicJsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}

	@Test
	public void arrayOfItemsLessThanMin() {
		String json = "[1, 2]";
		ComplexType schema = array(integer()).minItems(3);
		JsonValidator validator = new BasicJsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertEquals(1, result.getProblems().size());
		assertTrue(result.getProblems().get(0) instanceof ArrayTooShortProblem);
		ArrayTooShortProblem p = (ArrayTooShortProblem)result.getProblems().get(0);
		assertEquals(2, p.getActualSize());
		assertEquals(3, p.getLimitSize());
		assertNotNull(p.getMessage());
	}

	@Test
	public void arrayOfMaxItems() {
		String json = "[1, 2, 3, 4]";
		ComplexType schema = array(integer()).maxItems(4);
		JsonValidator validator = new BasicJsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}

	@Test
	public void arrayOfItemsLessThanMax() {
		String json = "[1, 2, 3]";
		ComplexType schema = array(integer()).maxItems(4);
		JsonValidator validator = new BasicJsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}
	
	/*
	 * Problem that the array is too long than required.
	 */
	@Test
	public void arrayOfItemsMoreThanMax() {
		String json = "[1, 2, 3, 4, 5]";
		ComplexType schema = array(integer()).maxItems(4);
		JsonValidator validator = new BasicJsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertEquals(1, result.getProblems().size());
		assertTrue(result.getProblems().get(0) instanceof ArrayTooLongProblem);
		ArrayTooLongProblem p = (ArrayTooLongProblem)result.getProblems().get(0);
		assertEquals(5, p.getActualSize());
		assertEquals(4, p.getLimitSize());
		assertNotNull(p.getMessage());
	}
	
	@Test
	public void arrayOfItemsBetweenMinAndMax() {
		String json = "[1, 2, 3, 4]";
		ComplexType schema = array(integer()).minItems(3).maxItems(4);
		JsonValidator validator = new BasicJsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}
}
