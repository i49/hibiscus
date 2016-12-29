package com.github.i49.hibiscus.problems;

import java.util.Locale;

import javax.json.JsonArray;

/**
 * Problem that an array has too many elements than the specified explicitly in the schema.
 * 
 * <p>This problem can be caused by {@code array()} type only.</p>
 */
public class ArrayTooLongProblem extends JsonValueProblem<JsonArray> {

	private final int actualLength;
	private final int limitLength;
	
	/**
	 * Constructs this problem.
	 * @param value the array which has invalid number of elements.
	 * @param actualLength the actual number of elements in the array in JSON document.
	 * @param limitLength the maximum number of elements in the array type declared in the schema. 
	 */
	public ArrayTooLongProblem(JsonArray value, int actualLength, int limitLength) {
		super(value);
		this.actualLength = actualLength;
		this.limitLength = limitLength;
	}

	/**
	 * Returns the actual number of elements in the array.
	 * @return the actual number of elements.
	 */
	public int getActualLength() {
		return actualLength;
	}
	
	/**
	 * Returns the maximum number of elements in the array type declared in the schema. 
	 * @return the maximum number of elements in the array type.
	 */
	public int getLimitLength() {
		return limitLength;
	}

	@Override
	public String buildDescription(Locale locale) {
		return Messages.ARRAY_TOO_LONG_PROBLEM(locale, getActualLength(), getLimitLength());
	}
}
