package com.github.i49.hibiscus.validation;

import static com.github.i49.hibiscus.schema.types.JsonTypes.*;
import static org.junit.Assert.*;

import java.io.StringReader;
import java.util.Set;

import javax.json.JsonValue;

import org.junit.Test;

import com.github.i49.hibiscus.schema.TypeId;
import com.github.i49.hibiscus.schema.problems.StringPatternProblem;
import com.github.i49.hibiscus.schema.problems.StringTooLongProblem;
import com.github.i49.hibiscus.schema.problems.StringTooShortProblem;
import com.github.i49.hibiscus.schema.problems.TypeMismatchProblem;
import com.github.i49.hibiscus.schema.problems.UnknownValueProblem;
import com.github.i49.hibiscus.schema.types.JsonType;

public class StringValidationTest extends BaseValidationTest {

	@Test
	public void normalString() {
		String json = "[\"abc\"]";
		JsonType schema = array(string());
		JsonValidator validator = new JsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}

	@Test
	public void emptyString() {
		String json = "[\"\"]";
		JsonType schema = array(string());
		JsonValidator validator = new JsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}

	@Test
	public void notStringButInteger() {
		String json = "[123]";
		JsonType schema = array(string());
		JsonValidator validator = new JsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertEquals(1, result.getProblems().size());
		assertTrue(result.getProblems().get(0) instanceof TypeMismatchProblem);
		TypeMismatchProblem p = (TypeMismatchProblem)result.getProblems().get(0);
		assertEquals(TypeId.INTEGER, p.getActualType());
		assertNotNull(p.getMessage());
	}
	
	@Test
	public void stringOfAllowedValue() {
		String json = "[\"Spring\"]";
		JsonType schema = array(string().values("Spring", "Summer", "Autumn", "Winter"));
		JsonValidator validator = new JsonValidator(schema);
		result = validator.validate(new StringReader(json));
		
		assertFalse(result.hasProblems());
	}
	
	@Test
	public void stringOfNotAllowedValue() {
		String json = "[\"Q2\"]";
		JsonType schema = array(string().values("Spring", "Summer", "Autumn", "Winter"));
		JsonValidator validator = new JsonValidator(schema);
		result = validator.validate(new StringReader(json));
		
		assertEquals(1, result.getProblems().size());
		assertTrue(result.getProblems().get(0) instanceof UnknownValueProblem);
		UnknownValueProblem p = (UnknownValueProblem)result.getProblems().get(0);
		assertEquals("\"Q2\"", p.getActualValue().toString());
		Set<JsonValue> expected = p.getExpectedValues();
		assertEquals(4, expected.size());
		assertNotNull(p.getMessage());
	}

	@Test
	public void stringOfMinLength() {
		String json = "[\"abc\"]";
		JsonType schema = array(string().minLength(3));
		JsonValidator validator = new JsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}

	@Test
	public void stringOfLengthLessThanMin() {
		
		String json = "[\"ab\"]";
		JsonType schema = array(string().minLength(3));
		JsonValidator validator = new JsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertEquals(1, result.getProblems().size());
		assertTrue(result.getProblems().get(0) instanceof StringTooShortProblem);
		StringTooShortProblem p = (StringTooShortProblem)result.getProblems().get(0);
		assertEquals(2, p.getActualLength());
		assertEquals(3, p.getExpectedRange().getMinimum());
		assertNotNull(p.getMessage());
	}

	@Test
	public void stringOftMaxLength() {
		
		String json = "[\"abcd\"]";
		JsonType schema = array(string().maxLength(4));
		JsonValidator validator = new JsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}

	@Test
	public void stringOfLengthMoreThantMax() {
		
		String json = "[\"abcde\"]";
		JsonType schema = array(string().maxLength(4));
		JsonValidator validator = new JsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertEquals(1, result.getProblems().size());
		assertTrue(result.getProblems().get(0) instanceof StringTooLongProblem);
		StringTooLongProblem p = (StringTooLongProblem)result.getProblems().get(0);
		assertEquals(5, p.getActualLength());
		assertEquals(4, p.getExpectedRange().getMaximum());
		assertNotNull(p.getMessage());
	}
	
	@Test
	public void stringOfLengthBetweentMinAndMax() {
		String json = "[\"abcd\"]";
		JsonType schema = array(string().minLength(3).maxLength(4));
		JsonValidator validator = new JsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}
	
	@Test
	public void stringOfValidPattern() {
		String json = "[\"123-45-6789\"]";
		JsonType schema = array(string().pattern("\\d{3}-?\\d{2}-?\\d{4}"));
		JsonValidator validator = new JsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}

	@Test
	public void stringOfInvalidPattern() {
		String json = "[\"9876-54-321\"]";
		JsonType schema = array(string().pattern("\\d{3}-?\\d{2}-?\\d{4}"));
		JsonValidator validator = new JsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertEquals(1, result.getProblems().size());
		assertTrue(result.getProblems().get(0) instanceof StringPatternProblem);
		StringPatternProblem p = (StringPatternProblem)result.getProblems().get(0);
		assertEquals("9876-54-321", p.getActualValue().getString());
		assertNotNull(p.getMessage());
	}
}
