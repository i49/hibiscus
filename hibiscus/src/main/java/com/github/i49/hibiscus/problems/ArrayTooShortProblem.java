package com.github.i49.hibiscus.problems;

import java.util.Locale;

import javax.json.JsonArray;

/**
 * Problem that array is too short.
 */
public class ArrayTooShortProblem extends ValueProblem<JsonArray> {

	private final int actualSize;
	private final int limitSize;
	
	/**
	 * Constructs this problem.
	 * @param value the actual value in JSON document.
	 * @param actualSize the actual number of elements in array value.
	 * @param limitSize the minimum number of elements allowed in the array type. 
	 */
	public ArrayTooShortProblem(JsonArray value, int actualSize, int limitSize) {
		super(value);
		this.actualSize = actualSize;
		this.limitSize = limitSize;
	}

	/**
	 * Returns the actual number of elements in array instance.
	 * @return actual number of elements.
	 */
	public int getActualSize() {
		return actualSize;
	}
	
	/**
	 * Returns the number of elements allowed in array. 
	 * @return the minimum number of elements.
	 */
	public int getLimitSize() {
		return limitSize;
	}
	
	@Override
	public String buildDescription(Locale locale) {
		return Messages.ARRAY_TOO_SHORT(locale, getActualSize(), getLimitSize());
	}
}
