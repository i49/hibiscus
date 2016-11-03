package com.github.i49.schema.problems;

/**
 * Problem that object does not have a property which is specified as mandatory.
 */
public class MissingPropertyProblem extends Problem {

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
	public String getMessage() {
		return "Property \"" + getPropertyName() + "\" is required.";
	}
}
