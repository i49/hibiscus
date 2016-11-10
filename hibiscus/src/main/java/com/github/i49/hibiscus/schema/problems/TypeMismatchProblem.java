package com.github.i49.hibiscus.schema.problems;

import java.util.Locale;
import java.util.Set;

import com.github.i49.hibiscus.schema.TypeId;

/**
 * Problem that type of JSON instance does not match type specified in schema. 
 */
public class TypeMismatchProblem extends Problem {
	
	private final Set<TypeId> expectedType;
	private final TypeId instanceType;

	/**
	 * Constructs this problem.
	 * @param expectedType expected types specified in schema.
	 * @param instanceType actual type found in JSON instance.
	 */
	public TypeMismatchProblem(Set<TypeId> expectedType, TypeId instanceType) {
		this.expectedType = expectedType;
		this.instanceType = instanceType;
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
	public TypeId getInstanceType() {
		return instanceType;
	}

	@Override
	public String getMessage(Locale locale) {
		String instanceType = getInstanceType().toString().toLowerCase();
		String expectedType = getExpectedTypes().toString().toLowerCase();
		return localize(locale, instanceType, expectedType);
	}
}
