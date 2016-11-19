package com.github.i49.hibiscus.problems;

import java.util.Locale;

import javax.json.stream.JsonLocation;

/**
 * Problem to be detected during validation of JSON content.
 */
public interface Problem {

	/**
	 * Returns the location where this problem was found.
	 * @return location the location object defined in JSON Processing API.
	 */
	JsonLocation getLocation();

	/**
	 * Assigns the location where this problem was found.
	 * @param location the location object defined in JSON Processing API.
	 * @return this problem.
	 * @exception IllegalArgumentException if location is null.
	 */
	Problem setLocation(JsonLocation location);
	
	/**
	 * Returns the error message of this problem.
	 * @return error message.
	 */
	default String getMessage() {
		return getMessage(Locale.getDefault());
	}
	
	/**
	 * Returns the error message of this problem for specified locale.
	 * @param locale the locale for which the message is desired.
	 * @return error message.
	 * @exception IllegalArgumentException if locale is null.
	 */
	String getMessage(Locale locale);
}
