package com.github.i49.hibiscus.problems;

import java.util.Locale;

/**
 * Problem that array is too short.
 */
public class ArrayTooShortProblem extends ArraySizeProblem {

	/**
	 * Constructs this problem.
	 * @param actualSize actual number of elements in array instance.
	 * @param limitSize the minimum or maximum number of elements allowed in the array type. 
	 */
	public ArrayTooShortProblem(int actualSize, int limitSize) {
		super(actualSize, limitSize);
	}

	@Override
	public String getMessage(Locale locale) {
		return localize(locale, getActualSize(), getLimitSize());
	}
}
