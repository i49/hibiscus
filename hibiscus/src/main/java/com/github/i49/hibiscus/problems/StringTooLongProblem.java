package com.github.i49.hibiscus.problems;

import java.util.Locale;

import javax.json.JsonString;

import com.github.i49.hibiscus.common.IntRange;

/**
 * Problem that string is longer than allowed.
 */
public class StringTooLongProblem extends StringLengthProblem {

	/**
	 * Constructs this problem.
	 * @param value string value in JSON instance. 
	 * @param length actual number of characters in the string.
	 * @param range the number of characters allowed for the string. 
	 */
	public StringTooLongProblem(JsonString value, int length, IntRange range) {
		super(value, length, range);
	}

	@Override
	public String getMessage(Locale locale) {
		return localize(locale, getActualLength(), getExpectedRange().getMaximum());
	}
}
