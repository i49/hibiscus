package com.github.i49.hibiscus.problems;

import java.util.Locale;

import javax.json.stream.JsonLocation;

/**
 * Problem that will be detected during validation of JSON document.
 */
public interface Problem {

	/**
	 * Returns the location where this problem was found.
	 * @return the location object defined in JSON Processing API.
	 */
	JsonLocation getLocation();

	/**
	 * Assigns the location where this problem was found.
	 * @param location the location which indicates where this problem was found. {@code null} is acceptable.
	 * @return this problem.
	 */
	Problem setLocation(JsonLocation location);
	
	/**
	 * Returns the description of this problem.
	 * @return the description of this problem.
	 */
	default String getDescription() {
		return getDescription(Locale.getDefault());
	}
	
	/**
	 * Returns the description of this problem for specified locale.
	 * @param locale the locale for which the message is desired.
	 *               if the argument is {@code null}, current locale is used instead.
	 * @return the description of this problem.
	 */
	String getDescription(Locale locale);
}
