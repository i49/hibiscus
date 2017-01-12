package com.github.i49.hibiscus.problems;

import java.util.Locale;

import javax.json.JsonObject;

/**
 * Problem that an object has a property which is not explicitly declared in the schema.
 *
 * <p>This problem can be caused by {@code object()} type only.</p>
 */
public class UnknownPropertyProblem extends TypedJsonValueProblem<JsonObject> {

	private final String propertyName;

	/**
	 * Constructs this problem.
	 * @param propertyName the name of the unknown property which caused this problem.
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
