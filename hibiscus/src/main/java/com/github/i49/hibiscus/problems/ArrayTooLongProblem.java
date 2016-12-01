package com.github.i49.hibiscus.problems;

import java.util.Locale;

import javax.json.JsonArray;

/**
 * Problem that array is too long.
 */
public class ArrayTooLongProblem extends ValueProblem<JsonArray> {

	private final int actualLength;
	private final int limitLength;
	
	/**
	 * Constructs this problem.
	 * @param value the actual value in JSON document.
	 * @param actualLength the actual number of elements in array value.
	 * @param limitLength the maximum number of elements allowed in the array type. 
	 */
	public ArrayTooLongProblem(JsonArray value, int actualLength, int limitLength) {
		super(value);
		this.actualLength = actualLength;
		this.limitLength = limitLength;
	}

	/**
	 * Returns the actual number of elements in array instance.
	 * @return actual number of elements.
	 */
	public int getActualLength() {
		return actualLength;
	}
	
	/**
	 * Returns the number of elements allowed in array. 
	 * @return the maximum number of elements.
	 */
	public int getLimitLength() {
		return limitLength;
	}

	@Override
	public String buildDescription(Locale locale) {
		return Messages.ARRAY_TOO_LONG_PROBLEM(locale, getActualLength(), getLimitLength());
	}
}
