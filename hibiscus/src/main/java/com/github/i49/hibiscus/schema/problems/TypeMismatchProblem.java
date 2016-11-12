package com.github.i49.hibiscus.schema.problems;

import java.util.Locale;
import java.util.Set;

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
	 * Returns expected types specified in schema.
	 * @return expected types. 
	 */
	public Set<TypeId> getExpectedTypes() {
		return expectedType;
	}

	/**
	 * Returns actual type found in JSON instance.
	 * @return actual type.
	 */
	public TypeId getActualType() {
		return actualType;
	}

	@Override
	public String getMessage(Locale locale) {
		String actualType = getActualType().toString().toLowerCase();
		String expectedType = getExpectedTypes().toString().toLowerCase();
		return localize(locale, actualType, expectedType);
	}
}
