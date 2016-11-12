package com.github.i49.hibiscus.schema.problems;

import com.github.i49.hibiscus.schema.IntRange;

/**
 * Base class of ArrayTooShortProblem and ArrayTooLongProblem.
 */
public abstract class ArraySizeProblem extends Problem {

	private final int actualSize;
	private final IntRange range;
	
	/**
	 * Constructs this problem.
	 * @param actualSize actual number of elements in array instance.
	 * @param range the number of elements allowed in array. 
	 */
	protected ArraySizeProblem(int actualSize, IntRange range) {
		this.actualSize = actualSize;
		this.range = range;
	}

	/**
	 * Returns actual number of elements in array instance.
	 * @return actual number of elements.
	 */
	public int getActualSize() {
		return actualSize;
	}
	
	/**
	 * Returns the number of elements allowed in array. 
	 * @return range minimum and maximum numbers of elements.
	 */
	public IntRange getExpectedRange() {
		return range;
	}
}
