package com.github.i49.hibiscus.problems;

/**
 * Base class of {@code ArrayTooShortProblem} and {@code ArrayTooLongProblem}.
 */
public abstract class ArraySizeProblem extends AbstractProblem {

	private final int actualSize;
	private final int limitSize;
	
	/**
	 * Constructs this problem.
	 * @param actualSize actual number of elements in array instance.
	 * @param limitSize the minimum or maximum number of elements allowed in the array type. 
	 */
	protected ArraySizeProblem(int actualSize, int limitSize) {
		this.actualSize = actualSize;
		this.limitSize = limitSize;
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
	 * @return the minimum or maximum number of elements.
	 */
	public int getLimitSize() {
		return limitSize;
	}
}
