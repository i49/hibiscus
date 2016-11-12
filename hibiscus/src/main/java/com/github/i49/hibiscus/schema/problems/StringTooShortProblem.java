package com.github.i49.hibiscus.schema.problems;

import java.util.Locale;

import com.github.i49.hibiscus.schema.IntRange;

/**
 * Problem that string is shorter than required.
 */
public class StringTooShortProblem extends StringLengthProblem {

	/**
	 * Constructs this problem.
	 * @param actualLength actual number of characters in string.
	 * @param range the number of characters allowed in string. 
	 */
	public StringTooShortProblem(int actualLength, IntRange range) {
		super(actualLength, range);
	}

	@Override
	public String getMessage(Locale locale) {
		return localize(locale, getActualLength(), getExpectedRange().getMinimum());
	}
}
