package com.github.i49.hibiscus.problems;

import java.util.Locale;

import javax.json.JsonString;

/**
 * Problem that string is shorter than required.
 */
public class StringTooShortProblem extends ValueProblem<JsonString> {

	private final int actualLength;
	private final int limitLength;
	
	/**
	 * Constructs this problem.
	 * @param value string value in JSON document. 
	 * @param actualLength actual number of characters in the string.
	 * @param limitLength the minimum number of characters allowed for the type. 
	 */
	public StringTooShortProblem(JsonString value, int actualLength, int limitLength) {
		super(value);
		this.actualLength = actualLength;
		this.limitLength = limitLength;
	}
	
	/**
	 * Returns actual number of characters in the string.
	 * @return actual number of characters.
	 */
	public int getActualLength() {
		return actualLength;
	}

	/**
	 * Returns the minimum number of characters allowed. 
	 * @return the minimum number of characters.
	 */
	public int getLimitLength() {
		return limitLength;
	}

	@Override
	public String buildDescription(Locale locale) {
		return Messages.STRING_TOO_SHORT_PROBLEM(locale, getActualLength(), getLimitLength());
	}
}
