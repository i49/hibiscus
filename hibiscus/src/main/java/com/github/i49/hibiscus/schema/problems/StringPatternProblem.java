package com.github.i49.hibiscus.schema.problems;

import java.util.Locale;

/**
 * Problems that string value does not match expected pattern.
 */
public class StringPatternProblem extends Problem {

	private final String instanceValue;
	
	public StringPatternProblem(String instanceValue) {
		this.instanceValue = instanceValue;
	}
	
	public String getInstanceValue() {
		return instanceValue;
	}

	@Override
	public String getMessage(Locale locale) {
		return localize(locale, getInstanceValue());
	}
}
