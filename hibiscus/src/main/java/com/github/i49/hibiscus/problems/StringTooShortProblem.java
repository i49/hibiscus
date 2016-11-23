package com.github.i49.hibiscus.problems;

import java.util.Locale;

import javax.json.JsonString;

/**
 * Problem that string is shorter than required.
 */
public class StringTooShortProblem extends StringLengthProblem {

	/**
	 * Constructs this problem.
	 * @param value string value in JSON instance. 
	 * @param actualLength actual number of characters in the string.
	 * @param limitLength the minimum or maximum number of characters allowed for the type. 
	 */
	public StringTooShortProblem(JsonString value, int actualLength, int limitLength) {
		super(value, actualLength, limitLength);
	}

	@Override
	public String buildDescription(Locale locale) {
		return Messages.STRING_TOO_SHORT(locale, getActualLength(), getLimitLength());
	}
}
