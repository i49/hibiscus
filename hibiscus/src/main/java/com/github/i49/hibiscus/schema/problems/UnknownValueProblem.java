package com.github.i49.hibiscus.schema.problems;

import java.util.Set;
import java.util.stream.Collectors;

import javax.json.JsonValue;

public class UnknownValueProblem extends Problem {

	private final Set<JsonValue> expected;
	private final JsonValue actual;
	
	public UnknownValueProblem(Set<JsonValue> expected, JsonValue actual) {
		this.expected = expected;
		this.actual = actual;
	}
	
	public Set<JsonValue> getExpectedValues() {
		return expected;
	}
	
	public JsonValue getActualValue() {
		return actual;
	}

	@Override
	public String getMessage() {
		StringBuilder b = new StringBuilder();
		b.append(getActualValue()).append(" is not allowed. ");
		b.append("Expected values are: ");
		b.append(getExpectedValues().stream().map(JsonValue::toString).collect(Collectors.joining(", ")));
		b.append(".");
		return b.toString();
	}
}
