package com.github.i49.hibiscus.problems;

import javax.json.stream.JsonLocation;

import com.github.i49.hibiscus.TypeId;

public class TypeMismatchProblem extends Problem {
	
	private final TypeId expected;
	private final TypeId actual;

	public TypeMismatchProblem(TypeId expected, TypeId actual, JsonLocation location) {
		super(location);
		this.expected = expected;
		this.actual = actual;
	}
	
	public TypeId getExpectedType() {
		return expected;
	}

	public TypeId getActualType() {
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
