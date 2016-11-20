package com.github.i49.hibiscus.problems;

import java.util.Locale;

/**
 * Problem that array is too long.
 */
public class ArrayTooLongProblem extends ArraySizeProblem {

	/**
	 * Constructs this problem.
	 * @param actualSize the actual number of elements in array instance.
	 * @param limitSize the maximum number of elements allowed in the array type. 
	 */
	public ArrayTooLongProblem(int actualSize, int limitSize) {
		super(actualSize, limitSize);
	}

	@Override
	public String buildMessage(Locale locale) {
		return Messages.ARRAY_TOO_LONG(locale, getActualSize(), getLimitSize());
	}
}
