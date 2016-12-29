package com.github.i49.hibiscus.problems;

import java.util.Locale;

import javax.json.JsonString;

/**
 * Problems that string value does not match expected pattern.
 */
public class StringPatternProblem extends JsonValueProblem<JsonString> {

	/**
	 * Constructs this problem.
	 * @param value the value in JSON document.
	 */
	public StringPatternProblem(JsonString value) {
		super(value);
	}
	
	@Override
	public String buildDescription(Locale locale) {
		return Messages.STRING_PATTERN_PROBLEM(locale, getActualValue());
	}
}
