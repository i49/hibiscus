package com.github.i49.hibiscus.schema.problems;

/**
 * Problem that array is too short or too long.
 */
public class ArraySizeProblem extends Problem {

	private final int threshold;
	private final int instanceSize;
	
	/**
	 * Constructs this problem.
	 * @param threshold minimum or maximum number of elements allowed in array. 
	 * @param instanceSize actual number of elements in array.
	 */
	public ArraySizeProblem(int threshold, int instanceSize) {
		this.threshold = threshold;
		this.instanceSize = instanceSize;
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
	public int getInstanceSize() {
		return instanceSize;
	}
	
	@Override
	public String getMessage() {
		StringBuilder b = new StringBuilder();
		if (getInstanceSize() < getThreshold()) {
			b.append("Array is too short. It must have at least ");
		} else {
			b.append("Array is too long. It must have at most ");
		};
		b.append(getThreshold()).append(" elements ");
		b.append("but instance has ").append(getInstanceSize()).append(".");
		return b.toString();
	}
}
