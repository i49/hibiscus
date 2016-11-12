package com.github.i49.hibiscus.schema.problems;

import java.util.Locale;

import javax.json.JsonString;

/**
 * Problems that string value does not match expected pattern.
 */
public class StringPatternProblem extends ValueProblem<JsonString> {

	/**
	 * Constructs this problem.
	 * @param value the value in JSON instance.
	 */
	public StringPatternProblem(JsonString value) {
		super(value);
	}
	
	@Override
	public String getMessage(Locale locale) {
		return localize(locale, getActualValue().getString());
	}
}
