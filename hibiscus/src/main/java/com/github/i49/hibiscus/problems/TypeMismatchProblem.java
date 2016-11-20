package com.github.i49.hibiscus.problems;

import java.util.Locale;
import java.util.Set;

import com.github.i49.hibiscus.common.TypeId;

/**
 * Problem that type of value in JSON instance does not match type specified in schema. 
 */
public class TypeMismatchProblem extends AbstractProblem {
	
	private final TypeId actualType;
	private final Set<TypeId> expectedType;

	/**
	 * Constructs this problem.
	 * @param actualType the actual type found in JSON instance.
	 * @param expectedType the expected types specified in schema.
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
	public String buildMessage(Locale locale) {
		return Messages.TYPE_MISMATCH(locale, getActualType(), getExpectedTypes());
	}
}
