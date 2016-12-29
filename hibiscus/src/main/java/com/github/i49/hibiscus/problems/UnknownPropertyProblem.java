package com.github.i49.hibiscus.problems;

import java.util.Locale;

/**
 * Problem that an object has a property which is not explicitly declared in the schema.
 */
public class UnknownPropertyProblem extends AbstractProblem {

	private final String propertyName;

	/**
	 * Constructs this problem.
	 * @param propertyName the name of the unknown property.
	 */
	public UnknownPropertyProblem(String propertyName) {
		this.propertyName = propertyName;
	}

	/**
	 * Returns the name of the unknown property which is not declared in the schema.
	 * @return the name of the unknown property which is not declared in the schema.
	 */
	public String getPropertyName() {
		return propertyName;
	}
	
	@Override
	public String buildDescription(Locale locale) {
		return Messages.UNKNOWN_PROPERTY_PROBLEM(locale, getPropertyName());
	}
}
