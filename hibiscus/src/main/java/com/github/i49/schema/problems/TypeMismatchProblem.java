package com.github.i49.schema.problems;

import java.util.Set;

import com.github.i49.schema.TypeId;

/**
 * Problem that type of JSON instance does not match type specified in schema. 
 */
public class TypeMismatchProblem extends Problem {
	
	private final Set<TypeId> expected;
	private final TypeId actual;

	/**
	 * Constructs this problem.
	 * @param expected expected types specified in schema.
	 * @param actual actual type found in JSON instance.
	 */
	public TypeMismatchProblem(Set<TypeId> expected, TypeId actual) {
		this.expected = expected;
		this.actual = actual;
	}
	
	/**
	 * Returns expected types specified in schema.
	 * @return expected types. 
	 */
	public Set<TypeId> getExpectedTypes() {
		return expected;
	}

	/**
	 * Returns actual type found in JSON instance.
	 * @return actual type.
	 */
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
