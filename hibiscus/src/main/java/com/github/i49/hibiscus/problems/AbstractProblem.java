package com.github.i49.hibiscus.problems;

import java.util.Locale;

import javax.json.stream.JsonLocation;

/**
 * Skeletal class to implement {@link Problem}.
 */
abstract class AbstractProblem implements Problem {

	private JsonLocation location;

	@Override
	public JsonLocation getLocation() {
		return location;
	}

	@Override
	public Problem setLocation(JsonLocation location) {
		this.location = location;
		return this;
	}

	@Override
	public String getDescription(Locale locale) {
		if (locale == null) {
			locale = Locale.getDefault();
		}
		return buildDescription(locale);
	}
	
	@Override
	public String getMessage(Locale locale) {
		if (locale == null) {
			locale = Locale.getDefault();
		}
		return Messages.PROBLEM_MESSAGE(locale, getLocation(), getDescription(locale));
	}
	
	/**
	 * Returns a string representation of this problem, including location and description.
	 * @return a string representation of this problem. 
	 */
	@Override
	public String toString() {
		return getMessage();
	}
	
	/**
	 * Builds description of this problem.
	 * @param locale the locale for the message. Cannot be null.
	 * @return built description.
	 */
	protected abstract String buildDescription(Locale locale); 
}
