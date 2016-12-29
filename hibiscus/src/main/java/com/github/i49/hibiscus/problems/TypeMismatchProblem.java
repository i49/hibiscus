package com.github.i49.hibiscus.problems;

import java.util.Locale;
import java.util.Set;

import com.github.i49.hibiscus.common.TypeId;

/**
 * Problem that the type of a value in JSON document does not match any types declared in the schema.
 * Note that the type matching is done by testing the equality of {@link TypeId} which is assigned to every type.
 * 
 * @see TypeId
 */
public class TypeMismatchProblem extends AbstractProblem {
	
	private final TypeId actualType;
	private final Set<TypeId> expectedTypes;

	/**
	 * Constructs this problem.
	 * @param actualType the actual type which caused this problem.
	 * @param expectedTypes all expected types for the value, which are declared in the schema.
	 */
	public TypeMismatchProblem(TypeId actualType, Set<TypeId> expectedTypes) {
		this.actualType = actualType;
		this.expectedTypes = expectedTypes;
	}
	
	/**
	 * Returns the actual type of the value found in JSON document, which does not match any types
	 * obtained by {@link #getExpectedTypes()}.
	 * @return the actual type found in JSON document.
	 * @see #getExpectedTypes()
	 */
	public TypeId getActualType() {
		return actualType;
	}

	/**
	 * Returns all expected types for the value, which are declared in the schema.
	 * @return all expected types for the value. 
	 */
	public Set<TypeId> getExpectedTypes() {
		return expectedTypes;
	}

	@Override
	public String buildDescription(Locale locale) {
		return Messages.TYPE_MISMATCH_PROBLEM(locale, getActualType(), getExpectedTypes());
	}
}
