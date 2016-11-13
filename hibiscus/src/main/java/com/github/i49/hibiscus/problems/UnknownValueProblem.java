package com.github.i49.hibiscus.problems;

import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import javax.json.JsonValue;

/**
 * Problem that instance value is not found in values allowed for the type.
 */
public class UnknownValueProblem extends ValueProblem<JsonValue> {

	private final Set<JsonValue> expected;
	
	/**
	 * Constructs this problem.
	 * @param value the value in JSON instance.
	 * @param expected expected set of values.
	 */
	public UnknownValueProblem(JsonValue value, Set<JsonValue> expected) {
		super(value);
		this.expected = expected;
	}
	
	/**
	 * Returns allowed values for the type.
	 * @return set of values.
	 */
	public Set<JsonValue> getExpectedValues() {
		return expected;
	}
	
	@Override
	public String getMessage(Locale locale) {
		String allowedValues = getExpectedValues().stream().map(JsonValue::toString).collect(Collectors.joining(", "));
		return localize(locale, getActualValue(), allowedValues);
	}
}
