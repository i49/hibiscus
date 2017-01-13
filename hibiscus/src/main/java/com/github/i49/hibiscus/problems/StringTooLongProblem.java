package com.github.i49.hibiscus.problems;

import java.util.Locale;

import javax.json.JsonString;

/**
 * Problem that a string is longer than specified explicitly in the schema.
 *
 * <p>This problem can be caused by {@code string()} type only.</p>
 */
public class StringTooLongProblem extends TypedProblem<JsonString> {

	private final int actualLength;
	private final int limitLength;
	
	/**
	 * Constructs this problem.
	 * @param actualLength the actual number of characters in the string.
	 * @param limitLength the maximum number of characters allowed for the type. 
	 */
	public StringTooLongProblem(int actualLength, int limitLength) {
		this.actualLength = actualLength;
		this.limitLength = limitLength;
	}

	/**
	 * Returns the actual number of characters in the string.
	 * @return the actual number of characters in the string.
	 */
	public int getActualLength() {
		return actualLength;
	}
	
	/**
	 * Returns the maximum number of characters allowed for the type. 
	 * @return the maximum number of characters allowed for the type.
	 */
	public int getLimitLength() {
		return limitLength;
	}
	
	@Override
	public String buildDescription(Locale locale) {
		return Messages.STRING_TOO_LONG_PROBLEM(locale, getActualLength(), getLimitLength());
	}
}
