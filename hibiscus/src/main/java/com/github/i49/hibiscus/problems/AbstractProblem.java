package com.github.i49.hibiscus.problems;

import java.util.Locale;

import javax.json.stream.JsonLocation;

/**
 * Skeletal implementation of Problem.
 */
abstract class AbstractProblem implements Problem {

	private JsonLocation location;

	@Override
	public JsonLocation getLocation() {
		return location;
	}

	@Override
	public Problem setLocation(JsonLocation location) {
		if (location == null) {
			throw new IllegalArgumentException("location is null.");
		}
		this.location = location;
		return this;
	}

	@Override
	public String getMessage(Locale locale) {
		if (locale == null) {
			locale = Locale.getDefault();
		}
		return buildMessage(locale);
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
	 * Builds message for this problem.
	 * @param locale the locale for the message. Cannot be null.
	 * @return built message.
	 */
	protected abstract String buildMessage(Locale locale); 
}