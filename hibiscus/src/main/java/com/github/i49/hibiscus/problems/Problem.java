package com.github.i49.hibiscus.problems;

import java.util.Locale;

import javax.json.stream.JsonLocation;

/**
 * Common interface which should be implemented by all problems that will be detected by JSON validator.
 * 
 * <p>Please read <a href="package-summary.html#summary-of-problem">Summary of Problem</a> first.</p>
 * 
 * @see <a href="package-summary.html#summary-of-problem">Summary of Problem</a>
 */
public interface Problem {

	/**
	 * Returns the location where this problem was found including line and column numbers
	 * on the input JSON document.
	 * @return {@link JsonLocation} object which holds the location of this problem.
	 * @see JsonLocation
	 */
	JsonLocation getLocation();

	/**
	 * Assigns the location where this problem was found to this problem.
	 * @param location the location which indicates where this problem was found. 
	 *                 {@code null} indicates the location is unknown.
	 * @return this problem.
	 */
	Problem setLocation(JsonLocation location);
	
	/**
	 * Returns the description of this problem for the default locale.
	 * <p>Calling this method is equivalent to call {@code getDescription(Locale.getDefault())}.</p>
	 * @return the description of this problem.
	 * @see #getDescription(Locale)
	 */
	default String getDescription() {
		return getDescription(Locale.getDefault());
	}
	
	/**
	 * Returns the description of this problem for specific locale.
	 * @param locale the locale for which the description is desired.
	 *               if the argument is {@code null}, default locale is used instead.
	 * @return the description of this problem.
	 */
	String getDescription(Locale locale);
	
	/**
	 * Returns the message of this problem for the default locale.
	 * <p>This is a handy method to display this problem to the application users.
	 * The message to be returned includes both the location and the description of this problem.</p>
	 * <p>Calling this method is equivalent to call {@code getMessage(Locale.getDefault())}.</p>
	 * 
	 * @return the message of this problem.
	 * @see #getMessage(Locale)
	 */
	default String getMessage() {
		return getMessage(Locale.getDefault());
	}
	
	/**
	 * Returns the message of this problem for specific locale.
	 * <p>This is a handy method to display this problem to the application users.
	 * The message to be returned includes both the location and the description of this problem.</p>
	 * 
	 * @param locale the locale for which the message is desired.
	 *               if the argument is {@code null}, default locale is used instead.
	 * @return the message of this problem.
	 */
	String getMessage(Locale locale); 
}
