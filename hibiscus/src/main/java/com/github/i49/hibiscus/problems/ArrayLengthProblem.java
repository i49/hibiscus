package com.github.i49.hibiscus.problems;

import java.util.Locale;

import javax.json.JsonArray;

/**
 * Problem that the number of elements in the array does not equal to the expected.
 */
public class ArrayLengthProblem extends ValueProblem<JsonArray> {

	private final int actualLength;
	private final int expectedLength;
	
	/**
	 * Constructs this problem.
	 * @param value the actual value in JSON document.
	 * @param actualLength the actual number of elements in array instance.
	 * @param expectedLength the number of elements expected in the array type. 
	 */
	public ArrayLengthProblem(JsonArray value, int actualLength, int expectedLength) {
		super(value);
		this.actualLength = actualLength;
		this.expectedLength = expectedLength;
	}

	/**
	 * Returns the actual number of elements in array instance.
	 * @return the actual number of elements.
	 */
	public int getActualLength() {
		return actualLength;
	}
	
	/**
	 * Returns the number of elements expected in array. 
	 * @return the number of elements expected.
	 */
	public int getExpectedLength() {
		return expectedLength;
	}

	@Override
	protected String buildDescription(Locale locale) {
		return Messages.ARRAY_LENGTH(locale, getActualLength(), getExpectedLength());
	}
}
