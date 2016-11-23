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
	 * Returns the description of this problem for current locale.
	 * <p>Calling this method is equivalent to call {@code getDescription(Locale.getDefault())}.</p>
	 * @return the description of this problem.
	 */
	default String getDescription() {
		return getDescription(Locale.getDefault());
	}
	
	/**
	 * Returns the description of this problem for specific locale.
	 * @param locale the locale for which the description is desired.
	 *               if the argument is {@code null}, current locale is used instead.
	 * @return the description of this problem.
	 */
	String getDescription(Locale locale);
	
	/**
	 * Returns the message of this problem for current locale.
	 * <p>The message returned includes both location and description of this problem.
	 * Calling this method is equivalent to call {@code getMessage(Locale.getDefault())}.</p>
	 * @return the message of this problem.
	 */
	default String getMessage() {
		return getMessage(Locale.getDefault());
	}
	
	/**
	 * Returns the message of this problem for specific locale.
	 * <p>The message returned includes both location and description of this problem.</p>
	 * @param locale the locale for which the message is desired.
	 *               if the argument is {@code null}, current locale is used instead.
	 * @return the message of this problem.
	 */
	String getMessage(Locale locale); 
}
