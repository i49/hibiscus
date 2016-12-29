package com.github.i49.hibiscus.problems;

import java.util.Locale;

import javax.json.JsonString;

/**
 * Problem that an string does not have exactly the same as expected characters.
 *
 * <p>This problem can be caused by {@code string()} type only.</p>
 */
public class StringLengthProblem extends JsonValueProblem<JsonString> {

	private final int actualLength;
	private final int expectedLength;
	
	/**
	 * Constructs this problem.
	 * @param value the string value which has invalid length and caused this problem. 
	 * @param actualLength the actual number of characters in the string.
	 * @param expectedLength the expected number of characters for the type.
	 */
	public StringLengthProblem(JsonString value, int actualLength, int expectedLength) {
		super(value);
		this.actualLength = actualLength;
		this.expectedLength = expectedLength;
	}
	
	/**
	 * Returns the actual number of characters in the string.
	 * @return the actual number of characters in the string.
	 */
	public int getActualLength() {
		return actualLength;
	}

	/**
	 * Returns the number of characters expected for the type. 
	 * @return the number of characters expected for the type.
	 */
	public int getExpectedLength() {
		return expectedLength;
	}

	@Override
	protected String buildDescription(Locale locale) {
		return Messages.STRING_LENGTH_PROBLEM(locale, getActualLength(), getExpectedLength());
	}
}
