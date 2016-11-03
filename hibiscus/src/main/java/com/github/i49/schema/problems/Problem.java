package com.github.i49.schema.problems;

import javax.json.stream.JsonLocation;

/**
 * The superclass of all problems to be detected during JSON validation.
 */
public abstract class Problem {

	private JsonLocation location;
	
	/**
	 * Constructs this problem.
	 */
	protected Problem() {
	}
	
	/**
	 * Returns location where this problem was found.
	 * @return location location object defined in JSON Processing API.
	 */
	public JsonLocation getLocation() {
		return location;
	}

	/**
	 * Assigns location where this problem was found.
	 * @param location location object defined in JSON Processing API.
	 * @return this problem.
	 */
	public Problem setLocation(JsonLocation location) {
		this.location = location;
		return this;
	}
	
	/**
	 * Returns a string representation of this type, including location and error message.
	 * @return a string representation of the object. 
	 */
	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		JsonLocation location = getLocation();
		if (location != null) {
			b.append("Line ").append(location.getLineNumber());
			b.append(", column ").append(location.getColumnNumber());
		} else {
			b.append("(unknown)");
		}
		b.append(": ").append(getMessage());
		return b.toString();
	}

	/**
	 * Builds and returns an error message of this problem.
	 * @return error message.
	 */
	public abstract String getMessage();
}
