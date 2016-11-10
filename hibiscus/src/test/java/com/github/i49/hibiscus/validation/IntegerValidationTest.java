package com.github.i49.hibiscus.validation;

import static com.github.i49.hibiscus.schema.types.JsonTypes.*;
import static org.junit.Assert.*;
import org.junit.Test;

import com.github.i49.hibiscus.schema.Range;
import com.github.i49.hibiscus.schema.TypeId;
import com.github.i49.hibiscus.schema.problems.NotMoreThanMinimumProblem;
import com.github.i49.hibiscus.schema.problems.NotLessThanMaximumProblem;
import com.github.i49.hibiscus.schema.problems.LessThanMinimumProblem;
import com.github.i49.hibiscus.schema.problems.TypeMismatchProblem;
import com.github.i49.hibiscus.schema.problems.UnknownValueProblem;
import com.github.i49.hibiscus.schema.problems.MoreThanMaximumProblem;
import com.github.i49.hibiscus.schema.types.JsonType;

import java.io.StringReader;
import java.math.BigDecimal;
import java.util.Set;

import javax.json.JsonNumber;
import javax.json.JsonValue;

public class IntegerValidationTest {

	@Test
	public void testValiadteInteger() {
		String json = "[123]";
		JsonType schema = array(integer());
		JsonValidator validator = new JsonValidator(schema);
		ValidationResult result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}
	
	@Test
	public void testValiadteMaxInt() {
		String json = "[2147483647]";
		JsonType schema = array(integer());
		JsonValidator validator = new JsonValidator(schema);
		ValidationResult result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}

	@Test
	public void testValiadteMinInt() {
		String json = "[-2147483648]";
		JsonType schema = array(integer());
		JsonValidator validator = new JsonValidator(schema);
		ValidationResult result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}

	@Test
	public void testValiadteMaxLong() {
		String json = "[9223372036854775807]";
		JsonType schema = array(integer());
		JsonValidator validator = new JsonValidator(schema);
		ValidationResult result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}

	@Test
	public void testValiadteMinLong() {
		String json = "[-9223372036854775808]";
		JsonType schema = array(integer());
		JsonValidator validator = new JsonValidator(schema);
		ValidationResult result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}
	
	@Test
	public void testValiadteNumber() {
		String json = "[123.45]";
		JsonType schema = array(integer());
		JsonValidator validator = new JsonValidator(schema);
		ValidationResult result = validator.validate(new StringReader(json));

		assertEquals(1, result.getProblems().size());
		assertTrue(result.getProblems().get(0) instanceof TypeMismatchProblem);
		TypeMismatchProblem p = (TypeMismatchProblem)result.getProblems().get(0);
		assertEquals(TypeId.NUMBER, p.getInstanceType());
	}

	@Test
	public void testTypeMismatch() {
		String json = "[\"123\"]";
		JsonType schema = array(integer());
		JsonValidator validator = new JsonValidator(schema);
		ValidationResult result = validator.validate(new StringReader(json));

		assertEquals(1, result.getProblems().size());
		assertTrue(result.getProblems().get(0) instanceof TypeMismatchProblem);
		TypeMismatchProblem p = (TypeMismatchProblem)result.getProblems().get(0);
		assertEquals(TypeId.STRING, p.getInstanceType());
	}
	
	@Test
	public void testIntegerValues() {
		String json = "[30]";
		JsonType schema = array(integer().values(28, 30, 31));
		JsonValidator validator = new JsonValidator(schema);
		ValidationResult result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}

	@Test
	public void testIntegerValuesNotAllowed() {
		String json = "[29]";
		JsonType schema = array(integer().values(28, 30, 31));
		JsonValidator validator = new JsonValidator(schema);
		ValidationResult result = validator.validate(new StringReader(json));

		assertEquals(1, result.getProblems().size());
		assertTrue(result.getProblems().get(0) instanceof UnknownValueProblem);
		UnknownValueProblem p = (UnknownValueProblem)result.getProblems().get(0);
		assertEquals(29, ((JsonNumber)p.getInstanceValue()).intValue());
		Set<JsonValue> expected = p.getExpectedValues();
		assertEquals(3, expected.size());
	}
	
	@Test
	public void testMinimum() {
		String json = "[28]";
		JsonType schema = array(integer().min(28));
		
		JsonValidator validator = new JsonValidator(schema);
		ValidationResult result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}

	@Test
	public void testLessThanMinimum() {
		String json = "[27]";
		JsonType schema = array(integer().min(28));
		
		JsonValidator validator = new JsonValidator(schema);
		ValidationResult result = validator.validate(new StringReader(json));

		assertEquals(1, result.getProblems().size());
		assertTrue(result.getProblems().get(0) instanceof LessThanMinimumProblem);
		LessThanMinimumProblem p = (LessThanMinimumProblem)result.getProblems().get(0);
		assertEquals(27, p.getInstanceValue().intValue());
		Range<BigDecimal> range = p.getAllowedRange();
		assertTrue(range.hasMinimum());
		assertFalse(range.hasExlusiveMinimum());
		assertFalse(range.hasMaximum());
		assertFalse(range.hasExclusiveMaximum());
		assertEquals(28, range.getMinimum().intValue());
	}

	@Test
	public void testExclusiveMinimum() {
		String json = "[29]";
		JsonType schema = array(integer().min(28).exclusiveMin(true));
		
		JsonValidator validator = new JsonValidator(schema);
		ValidationResult result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}

	@Test
	public void testEqualToExclusiveMinimum() {
		String json = "[28]";
		JsonType schema = array(integer().min(28).exclusiveMin(true));
		
		JsonValidator validator = new JsonValidator(schema);
		ValidationResult result = validator.validate(new StringReader(json));

		assertEquals(1, result.getProblems().size());
		assertTrue(result.getProblems().get(0) instanceof NotMoreThanMinimumProblem);
		NotMoreThanMinimumProblem p = (NotMoreThanMinimumProblem)result.getProblems().get(0);
		assertEquals(28, p.getInstanceValue().intValue());
		Range<BigDecimal> range = p.getAllowedRange();
		assertTrue(range.hasMinimum());
		assertTrue(range.hasExlusiveMinimum());
		assertFalse(range.hasMaximum());
		assertFalse(range.hasExclusiveMaximum());
		assertEquals(28, range.getMinimum().intValue());
	}

	@Test
	public void testMaximum() {
		String json = "[31]";
		JsonType schema = array(integer().max(31));
		
		JsonValidator validator = new JsonValidator(schema);
		ValidationResult result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}

	@Test
	public void testGreaterThanMaximum() {
		String json = "[32]";
		JsonType schema = array(integer().max(31));
		
		JsonValidator validator = new JsonValidator(schema);
		ValidationResult result = validator.validate(new StringReader(json));

		assertEquals(1, result.getProblems().size());
		assertTrue(result.getProblems().get(0) instanceof MoreThanMaximumProblem);
		MoreThanMaximumProblem p = (MoreThanMaximumProblem)result.getProblems().get(0);
		assertEquals(32, p.getInstanceValue().intValue());
		Range<BigDecimal> range = p.getAllowedRange();
		assertFalse(range.hasMinimum());
		assertFalse(range.hasExlusiveMinimum());
		assertTrue(range.hasMaximum());
		assertFalse(range.hasExclusiveMaximum());
		assertEquals(31, range.getMaximum().intValue());
	}

	@Test
	public void testExclusiveMaximum() {
		String json = "[30]";
		JsonType schema = array(integer().max(31).exclusiveMax(true));
		
		JsonValidator validator = new JsonValidator(schema);
		ValidationResult result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}

	@Test
	public void testEqualToExclusiveMaximum() {
		String json = "[31]";
		JsonType schema = array(integer().max(31).exclusiveMax(true));
		
		JsonValidator validator = new JsonValidator(schema);
		ValidationResult result = validator.validate(new StringReader(json));

		assertEquals(1, result.getProblems().size());
		assertTrue(result.getProblems().get(0) instanceof NotLessThanMaximumProblem);
		NotLessThanMaximumProblem p = (NotLessThanMaximumProblem)result.getProblems().get(0);
		assertEquals(31, p.getInstanceValue().intValue());
		Range<BigDecimal> range = p.getAllowedRange();
		assertFalse(range.hasMinimum());
		assertFalse(range.hasExlusiveMinimum());
		assertTrue(range.hasMaximum());
		assertTrue(range.hasExclusiveMaximum());
		assertEquals(31, range.getMaximum().intValue());
	}
}
