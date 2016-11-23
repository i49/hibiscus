package com.github.i49.hibiscus.problems;

import java.util.Locale;

/**
 * Problem that the number of elements in the array does not equal to the expected.
 */
public class ArraySizeProblem extends AbstractProblem {

	private final int actualSize;
	private final int expectedSize;
	
	/**
	 * Constructs this problem.
	 * @param actualSize the actual number of elements in array instance.
	 * @param expectedSize the number of elements expected in the array type. 
	 */
	public ArraySizeProblem(int actualSize, int expectedSize) {
		this.actualSize = actualSize;
		this.expectedSize = expectedSize;
	}

	/**
	 * Returns the actual number of elements in array instance.
	 * @return the actual number of elements.
	 */
	public int getActualSize() {
		return actualSize;
	}
	
	/**
	 * Returns the number of elements expected in array. 
	 * @return the number of elements expected.
	 */
	public int getExpectedSize() {
		return expectedSize;
	}

	@Override
	protected String buildDescription(Locale locale) {
		return Messages.ARRAY_SIZE(locale, getActualSize(), getExpectedSize());
	}
}
