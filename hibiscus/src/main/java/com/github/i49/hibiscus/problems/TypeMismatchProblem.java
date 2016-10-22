package com.github.i49.hibiscus.problems;

import javax.json.stream.JsonLocation;

import com.github.i49.hibiscus.ValueType;

public class TypeMismatchProblem extends Problem {
	
	private final ValueType.Type expected;
	private final ValueType.Type actual;

	public TypeMismatchProblem(ValueType.Type expected, ValueType.Type actual, JsonLocation location) {
		super(location);
		this.expected = expected;
		this.actual = actual;
	}
	
	public ValueType.Type getExpectedType() {
		return expected;
	}

	public ValueType.Type getActualType() {
		return actual;
	}

	@Override
	public String getMessage() {
		StringBuilder b = new StringBuilder();
		b.append("Type mismatch. ");
		b.append("expected: ").append(getExpectedType().toString().toLowerCase());
		b.append(", actual: ").append(getActualType().toString().toLowerCase());
		return b.toString();
	}
}
