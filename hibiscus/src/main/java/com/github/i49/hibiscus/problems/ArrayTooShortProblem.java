package com.github.i49.hibiscus.problems;

import java.util.Locale;

import javax.json.JsonArray;

/**
 * Problem that an array has too few elements than the specified explicitly in the schema.
 *
 * <p>This problem can be caused by {@code array()} type only.</p>
 */
public class ArrayTooShortProblem extends TypedProblem<JsonArray> {

	private final int actualLength;
	private final int limitLength;
	
	/**
	 * Constructs this problem.
	 * @param actualLength the actual number of elements in the array in JSON document.
	 * @param limitLength the minimum number of elements in the array type declared in the schema. 
	 */
	public ArrayTooShortProblem(int actualLength, int limitLength) {
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
	 * Returns the minimum number of elements in the array type declared in the schema. 
	 * @return the minimum number of elements in the array type.
	 */
	public int getLimitLength() {
		return limitLength;
	}
	
	@Override
	public String buildDescription(Locale locale) {
		return Messages.ARRAY_TOO_SHORT_PROBLEM(locale, getActualLength(), getLimitLength());
	}
}
