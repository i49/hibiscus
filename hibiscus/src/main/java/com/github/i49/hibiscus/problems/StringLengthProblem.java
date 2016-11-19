package com.github.i49.hibiscus.problems;

import javax.json.JsonString;

/**
 * Base class of {@code StringTooShortProblem} and {@code StringTooLongProblem}.
 */
public abstract class StringLengthProblem extends ValueProblem<JsonString> {

	private final int actualLength;
	private final int limitLength;
	
	/**
	 * Constructs this problem.
	 * @param value string value in JSON instance. 
	 * @param actualLength actual number of characters in the string.
	 * @param limitLength the minimum or maximum number of characters allowed for the type. 
	 */
	protected StringLengthProblem(JsonString value, int actualLength, int limitLength) {
		super(value);
		this.actualLength = actualLength;
		this.limitLength = limitLength;
	}
	
	/**
	 * Returns actual number of characters in string.
	 * @return actual number of characters.
	 */
	public int getActualLength() {
		return actualLength;
	}

	/**
	 * Returns the number of characters allowed for string. 
	 * @return the minimum or maximum number of characters.
	 */
	public int getLimitLength() {
		return limitLength;
	}
}
