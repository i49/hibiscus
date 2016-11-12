package com.github.i49.hibiscus.schema.problems;

import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import com.github.i49.hibiscus.schema.TypeId;

/**
 * Problem that type of JSON instance does not match type specified in schema. 
 */
public class TypeMismatchProblem extends Problem {
	
	private final TypeId actualType;
	private final Set<TypeId> expectedType;

	/**
	 * Constructs this problem.
	 * @param actualType actual type found in JSON instance.
	 * @param expectedType expected types specified in schema.
	 */
	public TypeMismatchProblem(TypeId actualType, Set<TypeId> expectedType) {
		this.actualType = actualType;
		this.expectedType = expectedType;
	}
	
	/**
	 * Returns actual type found in JSON instance.
	 * @return actual type.
	 */
	public TypeId getActualType() {
		return actualType;
	}

	/**
	 * Returns expected types specified in schema.
	 * @return expected types. 
	 */
	public Set<TypeId> getExpectedTypes() {
		return expectedType;
	}

	@Override
	public String getMessage(Locale locale) {
		String actualType = getActualType().toLowerCase();
		String expectedType = getExpectedTypes().stream().map(TypeId::toLowerCase).collect(Collectors.joining(", "));
		return localize(locale, actualType, expectedType);
	}
}
