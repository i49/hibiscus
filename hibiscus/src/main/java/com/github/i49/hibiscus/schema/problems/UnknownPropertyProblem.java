package com.github.i49.hibiscus.schema.problems;

import java.util.Locale;

/**
 * Problem that object has a property not defined in schema.
 */
public class UnknownPropertyProblem extends Problem {

	private final String propertyName;

	/**
	 * Constructs this problem.
	 * @param propertyName name of unknown property.
	 */
	public UnknownPropertyProblem(String propertyName) {
		this.propertyName = propertyName;
	}

	/**
	 * Returns name of unknown property.
	 * @return property name.
	 */
	public String getPropertyName() {
		return propertyName;
	}
	
	@Override
	public String getMessage(Locale locale) {
		return localize(locale, getPropertyName());
	}
}
