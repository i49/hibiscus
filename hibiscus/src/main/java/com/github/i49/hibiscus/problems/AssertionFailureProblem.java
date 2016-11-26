package com.github.i49.hibiscus.problems;

import java.util.Locale;

import javax.json.JsonValue;

/**
 * Problem that assertion on specific type was failed. 
 */
public class AssertionFailureProblem extends ValueProblem<JsonValue> {

	private final String message;
	
	public AssertionFailureProblem(JsonValue value, String message) {
		super(value);
		this.message = message;
	}

	@Override
	protected String buildDescription(Locale locale) {
		return message;
	}
}
