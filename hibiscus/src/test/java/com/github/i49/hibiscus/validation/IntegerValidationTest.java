package com.github.i49.hibiscus.validation;

import static com.github.i49.hibiscus.schema.JsonTypes.*;
import static org.junit.Assert.*;
import org.junit.Test;

import com.github.i49.hibiscus.common.Range;
import com.github.i49.hibiscus.common.TypeId;
import com.github.i49.hibiscus.problems.LessThanMinimumProblem;
import com.github.i49.hibiscus.problems.MoreThanMaximumProblem;
import com.github.i49.hibiscus.problems.NotLessThanMaximumProblem;
import com.github.i49.hibiscus.problems.NotMoreThanMinimumProblem;
import com.github.i49.hibiscus.problems.TypeMismatchProblem;
import com.github.i49.hibiscus.problems.UnknownValueProblem;
import com.github.i49.hibiscus.schema.JsonType;

import java.io.StringReader;
import java.math.BigDecimal;
import java.util.Set;

import javax.json.JsonNumber;
import javax.json.JsonValue;

public class IntegerValidationTest extends BaseValidationTest {

	@Test
	public void normalInteger() {
		String json = "[123]";
		JsonType schema = array(integer());
		JsonValidator validator = new JsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}
	
	@Test
	public void integerOfMaxInteger() {
		String json = "[2147483647]";
		JsonType schema = array(integer());
		JsonValidator validator = new JsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}

	@Test
	public void integerOfMinInteger() {
		String json = "[-2147483648]";
		JsonType schema = array(integer());
		JsonValidator validator = new JsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}

	@Test
	public void integerOfMaxLong() {
		String json = "[9223372036854775807]";
		JsonType schema = array(integer());
		JsonValidator validator = new JsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}

	@Test
	public void integerOfMinLong() {
		String json = "[-9223372036854775808]";
		JsonType schema = array(integer());
		JsonValidator validator = new JsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}
	
	@Test
	public void notIntegerButNumber() {
		String json = "[123.45]";
		JsonType schema = array(integer());
		JsonValidator validator = new JsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertEquals(1, result.getProblems().size());
		assertTrue(result.getProblems().get(0) instanceof TypeMismatchProblem);
		TypeMismatchProblem p = (TypeMismatchProblem)result.getProblems().get(0);
		assertEquals(TypeId.NUMBER, p.getActualType());
		assertNotNull(p.getMessage());
	}

	@Test
	public void notIntegerButString() {
		String json = "[\"123\"]";
		JsonType schema = array(integer());
		JsonValidator validator = new JsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertEquals(1, result.getProblems().size());
		assertTrue(result.getProblems().get(0) instanceof TypeMismatchProblem);
		TypeMismatchProblem p = (TypeMismatchProblem)result.getProblems().get(0);
		assertEquals(TypeId.STRING, p.getActualType());
		assertNotNull(p.getMessage());
	}
	
	@Test
	public void integerOfAllowedValue() {
		String json = "[30]";
		JsonType schema = array(integer().values(28, 30, 31));
		JsonValidator validator = new JsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}

	@Test
	public void integerOfNotAllowedValue() {
		String json = "[29]";
		JsonType schema = array(integer().values(28, 30, 31));
		JsonValidator validator = new JsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertEquals(1, result.getProblems().size());
		assertTrue(result.getProblems().get(0) instanceof UnknownValueProblem);
		UnknownValueProblem p = (UnknownValueProblem)result.getProblems().get(0);
		assertEquals(29, ((JsonNumber)p.getActualValue()).intValue());
		Set<JsonValue> expected = p.getExpectedValues();
		assertEquals(3, expected.size());
		assertNotNull(p.getMessage());
	}
	
	@Test
	public void integerOfMinimum() {
		String json = "[28]";
		JsonType schema = array(integer().min(28));
		
		JsonValidator validator = new JsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}

	@Test
	public void integerLessThanMinimum() {
		String json = "[27]";
		JsonType schema = array(integer().min(28));
		
		JsonValidator validator = new JsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertEquals(1, result.getProblems().size());
		assertTrue(result.getProblems().get(0) instanceof LessThanMinimumProblem);
		LessThanMinimumProblem p = (LessThanMinimumProblem)result.getProblems().get(0);
		assertEquals(27, p.getActualValue().intValue());
		Range<BigDecimal> range = p.getAllowedRange();
		assertTrue(range.hasMinimum());
		assertFalse(range.hasExlusiveMinimum());
		assertFalse(range.hasMaximum());
		assertFalse(range.hasExclusiveMaximum());
		assertEquals(28, range.getMinimum().intValue());
		assertNotNull(p.getMessage());
	}

	@Test
	public void integerMoreThanExclusiveMinimum() {
		String json = "[29]";
		JsonType schema = array(integer().min(28).exclusiveMin(true));
		
		JsonValidator validator = new JsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}

	@Test
	public void integerEqualToExclusiveMinimum() {
		String json = "[28]";
		JsonType schema = array(integer().min(28).exclusiveMin(true));
		
		JsonValidator validator = new JsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertEquals(1, result.getProblems().size());
		assertTrue(result.getProblems().get(0) instanceof NotMoreThanMinimumProblem);
		NotMoreThanMinimumProblem p = (NotMoreThanMinimumProblem)result.getProblems().get(0);
		assertEquals(28, p.getActualValue().intValue());
		Range<BigDecimal> range = p.getAllowedRange();
		assertTrue(range.hasMinimum());
		assertTrue(range.hasExlusiveMinimum());
		assertFalse(range.hasMaximum());
		assertFalse(range.hasExclusiveMaximum());
		assertEquals(28, range.getMinimum().intValue());
		assertNotNull(p.getMessage());
	}

	@Test
	public void integerOfMaximum() {
		String json = "[31]";
		JsonType schema = array(integer().max(31));
		
		JsonValidator validator = new JsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}

	@Test
	public void integerGreaterThanMaximum() {
		String json = "[32]";
		JsonType schema = array(integer().max(31));
		
		JsonValidator validator = new JsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertEquals(1, result.getProblems().size());
		assertTrue(result.getProblems().get(0) instanceof MoreThanMaximumProblem);
		MoreThanMaximumProblem p = (MoreThanMaximumProblem)result.getProblems().get(0);
		assertEquals(32, p.getActualValue().intValue());
		Range<BigDecimal> range = p.getAllowedRange();
		assertFalse(range.hasMinimum());
		assertFalse(range.hasExlusiveMinimum());
		assertTrue(range.hasMaximum());
		assertFalse(range.hasExclusiveMaximum());
		assertEquals(31, range.getMaximum().intValue());
		assertNotNull(p.getMessage());
	}

	@Test
	public void integerLessThanExclusiveMaximum() {
		String json = "[30]";
		JsonType schema = array(integer().max(31).exclusiveMax(true));
		
		JsonValidator validator = new JsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}

	@Test
	public void integerEqualToExclusiveMaximum() {
		String json = "[31]";
		JsonType schema = array(integer().max(31).exclusiveMax(true));
		
		JsonValidator validator = new JsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertEquals(1, result.getProblems().size());
		assertTrue(result.getProblems().get(0) instanceof NotLessThanMaximumProblem);
		NotLessThanMaximumProblem p = (NotLessThanMaximumProblem)result.getProblems().get(0);
		assertEquals(31, p.getActualValue().intValue());
		Range<BigDecimal> range = p.getAllowedRange();
		assertFalse(range.hasMinimum());
		assertFalse(range.hasExlusiveMinimum());
		assertTrue(range.hasMaximum());
		assertTrue(range.hasExclusiveMaximum());
		assertEquals(31, range.getMaximum().intValue());
		assertNotNull(p.getMessage());
	}
}
