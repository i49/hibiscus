package com.github.i49.hibiscus.validation;

import java.util.Set;

import javax.json.stream.JsonLocation;

public class TypeMismatchProblem extends Problem {
	
	private final Set<TypeId> expected;
	private final TypeId actual;

	public TypeMismatchProblem(Set<TypeId> expected, TypeId actual, JsonLocation location) {
		super(location);
		this.expected = expected;
		this.actual = actual;
	}
	
	public Set<TypeId> getExpectedTypes() {
		return expected;
	}

	public TypeId getActualType() {
		return actual;
	}

	@Override
	public String getMessage() {
		StringBuilder b = new StringBuilder();
		b.append("Type mismatch. ");
		b.append("expected: ").append(getExpectedTypes().toString().toLowerCase());
		b.append(", actual: ").append(getActualType().toString().toLowerCase());
		return b.toString();
	}
}
