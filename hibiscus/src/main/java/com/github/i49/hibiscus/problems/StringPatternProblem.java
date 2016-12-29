package com.github.i49.hibiscus.problems;

import java.util.Locale;

import javax.json.JsonString;

/**
 * Problems that a string value does not match the expected pattern specified as a regular expression.
 *
 * <p>This problem can be caused by {@code string()} type only.</p>
 */
public class StringPatternProblem extends JsonValueProblem<JsonString> {

	/**
	 * Constructs this problem.
	 * @param value the string value which did not match the expected pattern and caused this problem.
	 */
	public StringPatternProblem(JsonString value) {
		super(value);
	}
	
	@Override
	public String buildDescription(Locale locale) {
		return Messages.STRING_PATTERN_PROBLEM(locale, getActualValue());
	}
}
