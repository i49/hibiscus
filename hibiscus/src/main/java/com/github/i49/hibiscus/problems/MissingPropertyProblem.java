package com.github.i49.hibiscus.problems;

import java.util.Locale;

/**
 * Problem that object does not have a property which is specified as mandatory.
 */
public class MissingPropertyProblem extends AbstractProblem {

	private final String propertyName;
	
	/**
	 * Constructs this problem.
	 * @param propertyName name of missing property.
	 */
	public MissingPropertyProblem(String propertyName) {
		this.propertyName = propertyName;
	}

	/**
	 * Returns name of missing property.
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
