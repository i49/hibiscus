package com.github.i49.hibiscus.validation;

import static com.github.i49.hibiscus.schema.JsonTypes.*;
import static org.junit.Assert.*;
import org.junit.Test;

import com.github.i49.hibiscus.common.Bound;
import com.github.i49.hibiscus.common.TypeId;
import com.github.i49.hibiscus.problems.LessThanMinimumProblem;
import com.github.i49.hibiscus.problems.MoreThanMaximumProblem;
import com.github.i49.hibiscus.problems.NotLessThanMaximumProblem;
import com.github.i49.hibiscus.problems.NotMoreThanMinimumProblem;
import com.github.i49.hibiscus.problems.TypeMismatchProblem;
import com.github.i49.hibiscus.problems.UnknownValueProblem;
import com.github.i49.hibiscus.schema.ComplexType;

import java.io.StringReader;
import java.math.BigDecimal;
import java.util.Set;

import javax.json.JsonNumber;
import javax.json.JsonValue;

public class IntegerValidationTest extends BaseValidationTest {

	@Test
	public void normalInteger() {
		String json = "[123]";
		ComplexType schema = array(integer());
		JsonValidator validator = new BasicJsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}
	
	@Test
	public void integerOfMaxInteger() {
		String json = "[2147483647]";
		ComplexType schema = array(integer());
		JsonValidator validator = new BasicJsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}

	@Test
	public void integerOfMinInteger() {
		String json = "[-2147483648]";
		ComplexType schema = array(integer());
		JsonValidator validator = new BasicJsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}

	@Test
	public void integerOfMaxLong() {
		String json = "[9223372036854775807]";
		ComplexType schema = array(integer());
		JsonValidator validator = new BasicJsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}

	@Test
	public void integerOfMinLong() {
		String json = "[-9223372036854775808]";
		ComplexType schema = array(integer());
		JsonValidator validator = new BasicJsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}
	
	@Test
	public void notIntegerButNumber() {
		String json = "[123.45]";
		ComplexType schema = array(integer());
		JsonValidator validator = new BasicJsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertEquals(1, result.getProblems().size());
		assertTrue(result.getProblems().get(0) instanceof TypeMismatchProblem);
		TypeMismatchProblem p = (TypeMismatchProblem)result.getProblems().get(0);
		assertEquals(TypeId.NUMBER, p.getActualType());
		assertNotNull(p.getDescription());
	}

	@Test
	public void notIntegerButString() {
		String json = "[\"123\"]";
		ComplexType schema = array(integer());
		JsonValidator validator = new BasicJsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertEquals(1, result.getProblems().size());
		assertTrue(result.getProblems().get(0) instanceof TypeMismatchProblem);
		TypeMismatchProblem p = (TypeMismatchProblem)result.getProblems().get(0);
		assertEquals(TypeId.STRING, p.getActualType());
		assertNotNull(p.getDescription());
	}
	
	@Test
	public void integerOfAllowedValue() {
		String json = "[30]";
		ComplexType schema = array(integer().values(28, 30, 31));
		JsonValidator validator = new BasicJsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}

	@Test
	public void integerOfNotAllowedValue() {
		String json = "[29]";
		ComplexType schema = array(integer().values(28, 30, 31));
		JsonValidator validator = new BasicJsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertEquals(1, result.getProblems().size());
		assertTrue(result.getProblems().get(0) instanceof UnknownValueProblem);
		UnknownValueProblem p = (UnknownValueProblem)result.getProblems().get(0);
		assertEquals(29, ((JsonNumber)p.getActualValue()).intValue());
		Set<JsonValue> expected = p.getExpectedValues();
		assertEquals(3, expected.size());
		assertNotNull(p.getDescription());
	}
	
	@Test
	public void integerOfMinimum() {
		String json = "[28]";
		ComplexType schema = array(integer().minInclusive(28));
		
		JsonValidator validator = new BasicJsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}

	@Test
	public void integerLessThanMinimum() {
		String json = "[27]";
		ComplexType schema = array(integer().minInclusive(28));
		
		JsonValidator validator = new BasicJsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertEquals(1, result.getProblems().size());
		assertTrue(result.getProblems().get(0) instanceof LessThanMinimumProblem);
		LessThanMinimumProblem p = (LessThanMinimumProblem)result.getProblems().get(0);
		assertEquals(27, p.getActualValue().intValue());
		Bound<BigDecimal> bound = p.getBound();
		assertFalse(bound.isExclusive());
		assertEquals(28, bound.getValue().intValue());
		assertNotNull(p.getDescription());
	}

	@Test
	public void integerMoreThanExclusiveMinimum() {
		String json = "[29]";
		ComplexType schema = array(integer().minExclusive(28));
		
		JsonValidator validator = new BasicJsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}

	@Test
	public void integerEqualToExclusiveMinimum() {
		String json = "[28]";
		ComplexType schema = array(integer().minExclusive(28));
		
		JsonValidator validator = new BasicJsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertEquals(1, result.getProblems().size());
		assertTrue(result.getProblems().get(0) instanceof NotMoreThanMinimumProblem);
		NotMoreThanMinimumProblem p = (NotMoreThanMinimumProblem)result.getProblems().get(0);
		assertEquals(28, p.getActualValue().intValue());
		Bound<BigDecimal> bound = p.getBound();
		assertTrue(bound.isExclusive());
		assertEquals(28, bound.getValue().intValue());
		assertNotNull(p.getDescription());
	}

	@Test
	public void integerOfMaximum() {
		String json = "[31]";
		ComplexType schema = array(integer().maxInclusive(31));
		
		JsonValidator validator = new BasicJsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}

	@Test
	public void integerGreaterThanMaximum() {
		String json = "[32]";
		ComplexType schema = array(integer().maxInclusive(31));
		
		JsonValidator validator = new BasicJsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertEquals(1, result.getProblems().size());
		assertTrue(result.getProblems().get(0) instanceof MoreThanMaximumProblem);
		MoreThanMaximumProblem p = (MoreThanMaximumProblem)result.getProblems().get(0);
		assertEquals(32, p.getActualValue().intValue());
		Bound<BigDecimal> bound = p.getBound();
		assertFalse(bound.isExclusive());
		assertEquals(31, bound.getValue().intValue());
		assertNotNull(p.getDescription());
	}

	@Test
	public void integerLessThanExclusiveMaximum() {
		String json = "[30]";
		ComplexType schema = array(integer().maxExclusive(31));
		
		JsonValidator validator = new BasicJsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}

	@Test
	public void integerEqualToExclusiveMaximum() {
		String json = "[31]";
		ComplexType schema = array(integer().maxExclusive(31));
		
		JsonValidator validator = new BasicJsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertEquals(1, result.getProblems().size());
		assertTrue(result.getProblems().get(0) instanceof NotLessThanMaximumProblem);
		NotLessThanMaximumProblem p = (NotLessThanMaximumProblem)result.getProblems().get(0);
		assertEquals(31, p.getActualValue().intValue());
		Bound<BigDecimal> bound = p.getBound();
		assertTrue(bound.isExclusive());
		assertEquals(31, bound.getValue().intValue());
		assertNotNull(p.getDescription());
	}
}
