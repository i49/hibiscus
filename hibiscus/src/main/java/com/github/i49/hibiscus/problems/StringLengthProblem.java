package com.github.i49.hibiscus.problems;

import java.util.Locale;

import javax.json.JsonString;

/**
 * Problem that string does not have exactly the same as expected characters.
 */
public class StringLengthProblem extends ValueProblem<JsonString> {

	private final int actualLength;
	private final int expectedLength;
	
	/**
	 * Constructs this problem.
	 * @param value the string value in JSON document. 
	 * @param actualLength the actual number of characters in the string.
	 * @param expectedLength the expected number of characters in the string.
	 */
	public StringLengthProblem(JsonString value, int actualLength, int expectedLength) {
		super(value);
		this.actualLength = actualLength;
		this.expectedLength = expectedLength;
	}
	
	/**
	 * Returns actual number of characters in string.
	 * @return actual number of characters.
	 */
	public int getActualLength() {
		return actualLength;
	}

	/**
	 * Returns the number of characters expected for the string. 
	 * @return the number of characters expected.
	 */
	public int getExpectedLength() {
		return expectedLength;
	}

	@Override
	protected String buildDescription(Locale locale) {
		return Messages.STRING_LENGTH_PROBLEM(locale, getActualLength(), getExpectedLength());
	}
}
