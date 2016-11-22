package com.github.i49.hibiscus.problems;

import java.util.Locale;
import java.util.Set;

import javax.json.JsonValue;

/**
 * Problem that value in JSON instance does not match any value allowed for the type.
 */
public class UnknownValueProblem extends ValueProblem<JsonValue> {

	private final Set<JsonValue> expected;
	
	/**
	 * Constructs this problem.
	 * @param value the value in JSON instance.
	 * @param expected the set of values allowed for the type.
	 */
	public UnknownValueProblem(JsonValue value, Set<JsonValue> expected) {
		super(value);
		this.expected = expected;
	}
	
	/**
	 * Returns set of values allowed for the type.
	 * @return set of values.
	 */
	public Set<JsonValue> getExpectedValues() {
		return expected;
	}
	
	@Override
	public String buildMessage(Locale locale) {
		return Messages.UNKNOWN_VALUE(locale, getActualValue(), getExpectedValues());
	}
}
