package com.github.i49.hibiscus.schema.problems;

import com.github.i49.hibiscus.schema.IntRange;

/**
 * Base class of StringTooShortProblem and StringTooLongProblem.
 */
public abstract class StringLengthProblem extends Problem {

	private final int actualLength;
	private final IntRange range;
	
	/**
	 * Constructs this problem.
	 * @param actualLength actual number of characters in string.
	 * @param range the number of characters allowed in string. 
	 */
	protected StringLengthProblem(int actualLength, IntRange range) {
		this.actualLength = actualLength;
		this.range = range;
	}
	
	/**
	 * Returns actual number of characters in string.
	 * @return actual number of characters.
	 */
	public int getActualLength() {
		return actualLength;
	}

	/**
	 * Returns the number of characters allowed in array. 
	 * @return range representing minimum and maximum numbers of characters.
	 */
	public IntRange getExpectedRange() {
		return range;
	}
}
