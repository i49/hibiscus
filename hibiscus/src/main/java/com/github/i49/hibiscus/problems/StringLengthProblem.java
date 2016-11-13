package com.github.i49.hibiscus.problems;

import javax.json.JsonString;

import com.github.i49.hibiscus.common.IntRange;

/**
 * Base class of StringTooShortProblem and StringTooLongProblem.
 */
public abstract class StringLengthProblem extends ValueProblem<JsonString> {

	private final int length;
	private final IntRange range;
	
	/**
	 * Constructs this problem.
	 * @param value string value in JSON instance. 
	 * @param length actual number of characters in the string.
	 * @param range the number of characters allowed for the string. 
	 */
	protected StringLengthProblem(JsonString value, int length, IntRange range) {
		super(value);
		this.length = length;
		this.range = range;
	}
	
	/**
	 * Returns actual number of characters in string.
	 * @return actual number of characters.
	 */
	public int getActualLength() {
		return length;
	}

	/**
	 * Returns the number of characters allowed for string. 
	 * @return range representing minimum and maximum numbers of characters.
	 */
	public IntRange getExpectedRange() {
		return range;
	}
}
