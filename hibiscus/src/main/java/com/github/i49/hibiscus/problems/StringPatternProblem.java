package com.github.i49.hibiscus.problems;

import java.util.Locale;

import javax.json.JsonString;

/**
 * Problems that a string value does not match the expected pattern specified as a regular expression.
 *
 * <p>This problem can be caused by {@code string()} type only.</p>
 */
public class StringPatternProblem extends TypedJsonValueProblem<JsonString> {

	/**
	 * Constructs this problem.
	 */
	public StringPatternProblem() {
	}
	
	@Override
	public String buildDescription(Locale locale) {
		return Messages.STRING_PATTERN_PROBLEM(locale, getActualValue());
	}
}
