package com.github.i49.hibiscus.problems;

import java.util.Locale;

/**
 * Problem that object does not have a property which is specified as mandatory.
 */
public class MissingPropertyProblem extends AbstractProblem {

	private final String propertyName;
	
	/**
	 * Constructs this problem.
	 * @param propertyName the name of missing property.
	 */
	public MissingPropertyProblem(String propertyName) {
		this.propertyName = propertyName;
	}

	/**
	 * Returns the name of the missing property.
	 * @return name of property.
	 */
	public String getPropertyName() {
		return propertyName;
	}

	@Override
	public String buildMessage(Locale locale) {
		return Messages.MISSING_PROPERTY(locale, getPropertyName());
	}
}
