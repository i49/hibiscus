package com.github.i49.schema.problems;

/**
 * Problem that array is too short or too long.
 */
public class ArraySizeProblem extends Problem {

	private final int threshold;
	private final int actualSize;
	
	/**
	 * Constructs this problem.
	 * @param threshold minimum or maximum number of elements allowed in array. 
	 * @param actualSize actual number of elements in array.
	 */
	public ArraySizeProblem(int threshold, int actualSize) {
		this.threshold = threshold;
		this.actualSize = actualSize;
	}

	/**
	 * Returns minimum or maximum number of elements allowed in array.
	 * @return minimum or maximum threshold.
	 */
	public int getThreshold() {
		return threshold;
	}

	/**
	 * Returns actual number of elements in array.
	 * @return actual number of elements.
	 */
	public int getActualSize() {
		return actualSize;
	}
	
	@Override
	public String getMessage() {
		StringBuilder b = new StringBuilder();
		if (getActualSize() < getThreshold()) {
			b.append("Array is too short. It must have at least ");
		} else {
			b.append("Array is too long. It must have at most ");
		};
		b.append(getThreshold()).append(" elements ");
		b.append("but instance has ").append(getActualSize()).append(".");
		return b.toString();
	}
}
