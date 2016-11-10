package com.github.i49.hibiscus.schema.problems;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.json.stream.JsonLocation;

/**
 * The superclass of all problems to be detected during JSON validation.
 */
public abstract class Problem {

	private JsonLocation location;
	
	private static final String BUNDLE_BASE_NAME = "com.github.i49.hibiscus.schema.problems.messages";
	
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
	public String getMessage() {
		return getMessage(Locale.getDefault());
	}
	
	public abstract String getMessage(Locale locale);

	protected String localize(Locale locale, Object... arguments) {
		ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_BASE_NAME, locale);
		String key = getClass().getSimpleName();
		String pattern = bundle.getString(key);
		return MessageFormat.format(pattern, arguments);
	}
}
