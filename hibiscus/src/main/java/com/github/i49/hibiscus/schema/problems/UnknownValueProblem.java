package com.github.i49.hibiscus.schema.problems;

import java.util.Set;
import java.util.stream.Collectors;

import javax.json.JsonValue;

/**
 * Problem that instance value is not found in values allowed for the type.
 */
public class UnknownValueProblem extends Problem {

	private final Set<JsonValue> expected;
	private final JsonValue instance;
	
	public UnknownValueProblem(Set<JsonValue> expected, JsonValue instance) {
		this.expected = expected;
		this.instance = instance;
	}
	
	/**
	 * Returns allowed values for the type.
	 * @return set of values.
	 */
	public Set<JsonValue> getExpectedValues() {
		return expected;
	}
	
	/**
	 * Returns value of JSON instance. 
	 * @return instance value.
	 */
	public JsonValue getInstanceValue() {
		return instance;
	}

	@Override
	public String getMessage() {
		StringBuilder b = new StringBuilder();
		b.append(getInstanceValue()).append(" is not allowed. ");
		b.append("Expected values are: ");
		b.append(getExpectedValues().stream().map(JsonValue::toString).collect(Collectors.joining(", ")));
		b.append(".");
		return b.toString();
	}
}
