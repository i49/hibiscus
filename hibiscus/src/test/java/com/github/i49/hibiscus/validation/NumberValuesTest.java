package com.github.i49.hibiscus.validation;

import static com.github.i49.hibiscus.schema.JsonTypes.*;
import static org.junit.Assert.*;

import java.io.StringReader;
import java.math.BigDecimal;

import javax.json.JsonNumber;

import org.junit.Test;

import com.github.i49.hibiscus.problems.UnknownValueProblem;
import com.github.i49.hibiscus.schema.ComplexType;

public class NumberValuesTest extends BaseValidationTest {

	@Test
	public void allowedValuesOfInteger() {
		String json = "[123]";
		ComplexType schema = array(number().values(123, 456));
		JsonValidator validator = new BasicJsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}

	@Test
	public void notAllowedValuesOfInteger() {
		String json = "[789]";
		ComplexType schema = array(number().values(123, 456));
		JsonValidator validator = new BasicJsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertEquals(1, result.getProblems().size());
		assertTrue(result.getProblems().get(0) instanceof UnknownValueProblem);
		UnknownValueProblem p = (UnknownValueProblem)result.getProblems().get(0);
		assertEquals(new BigDecimal("789"), ((JsonNumber)p.getActualValue()).bigDecimalValue());
		assertNotNull(p.getMessage());
	}

	@Test
	public void allowedValuesOfNumber() {
		String json = "[56.78]";
		ComplexType schema = array(number().values(new BigDecimal("12.34"), new BigDecimal("56.78")));
		JsonValidator validator = new BasicJsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}

	@Test
	public void notAllowedValuesOfNumber() {
		String json = "[56.78]";
		ComplexType schema = array(number().values(new BigDecimal("12.34"), new BigDecimal("67.89")));
		JsonValidator validator = new BasicJsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertEquals(1, result.getProblems().size());
		assertTrue(result.getProblems().get(0) instanceof UnknownValueProblem);
		UnknownValueProblem p = (UnknownValueProblem)result.getProblems().get(0);
		assertEquals(new BigDecimal("56.78"), ((JsonNumber)p.getActualValue()).bigDecimalValue());
		assertNotNull(p.getMessage());
	}
}
