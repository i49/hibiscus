package com.github.i49.hibiscus.problems;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.json.stream.JsonLocation;

/**
 * Skeletal implementation of Problem.
 */
abstract class AbstractProblem implements Problem {

	private JsonLocation location;

	private static final String BUNDLE_BASE_NAME = Problem.class.getPackage().getName() + ".messages";
	
	public JsonLocation getLocation() {
		return location;
	}

	public Problem setLocation(JsonLocation location) {
		if (location == null) {
			throw new IllegalArgumentException("location is null.");
		}
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
	 * Returns resource bundle that contains problem messages.
	 * @param locale the locale for which a message is desired.
	 * @return resource bundle.
	 */
	protected ResourceBundle findBundle(Locale locale) {
		return ResourceBundle.getBundle(BUNDLE_BASE_NAME, locale);
	}

	/**
	 * Localizes the message for this problem.
	 * @param locale the locale for which a message is desired.
	 * @param arguments the arguments composing the message.
	 * @return localized message.
	 */
	protected String localize(Locale locale, Object... arguments) {
		ResourceBundle bundle = findBundle(locale);
		String key = getClass().getSimpleName();
		String pattern = bundle.getString(key);
		return MessageFormat.format(pattern, arguments);
	}
}
