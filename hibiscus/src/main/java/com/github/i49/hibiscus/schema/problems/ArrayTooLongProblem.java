package com.github.i49.hibiscus.schema.problems;

import java.util.Locale;

import com.github.i49.hibiscus.schema.IntRange;

/**
 * Problem that array is too long.
 */
public class ArrayTooLongProblem extends ArraySizeProblem {

	/**
	 * Constructs this problem.
	 * @param actualSize actual number of elements in array instance.
	 * @param range the number of elements allowed in array. 
	 */
	public ArrayTooLongProblem(int actualSize, IntRange range) {
		super(actualSize, range);
	}

	@Override
	public String getMessage(Locale locale) {
		return localize(locale, getActualSize(), getExpectedRange().getMaximum());
	}
}
