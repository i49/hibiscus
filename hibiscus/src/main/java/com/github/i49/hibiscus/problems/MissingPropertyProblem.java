package com.github.i49.hibiscus.problems;

import java.util.Locale;

import javax.json.JsonObject;

/**
 * Problem that an object does not have a property which is specified as mandatory in the schema.
 * 
 * <p>This problem can be caused by {@code object()} type only.</p>
 */
public class MissingPropertyProblem extends TypedProblem<JsonObject> {

	private final String propertyName;
	
	/**
	 * Constructs this problem.
	 * @param propertyName the name of the missing property.
	 */
	public MissingPropertyProblem(String propertyName) {
		this.propertyName = propertyName;
	}

	/**
	 * Returns the name of the missing property.
	 * @return the name of the missing property.
	 */
	public String getPropertyName() {
		return propertyName;
	}

	@Override
	public String buildDescription(Locale locale) {
		return Messages.MISSING_PROPERTY_PROBLEM(locale, getPropertyName());
	}
}
