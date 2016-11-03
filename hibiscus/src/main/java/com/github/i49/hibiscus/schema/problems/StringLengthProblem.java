package com.github.i49.hibiscus.schema.problems;

/**
 * Problem that string is too short or too long.
 */
public class StringLengthProblem extends Problem {

	private final int threshold;
	private final int actualLength;
	
	/**
	 * Constructs this problem.
	 * @param threshold minimum or maximum number of characters allowed in string. 
	 * @param actualLength actual number of characters in string.
	 */
	public StringLengthProblem(int threshold, int actualLength) {
		this.threshold = threshold;
		this.actualLength = actualLength;
	}
	
	/**
	 * Returns minimum or maximum number of characters allowed in string.
	 * @return minimum or maximum threshold.
	 */
	public int getThreshold() {
		return threshold;
	}

	/**
	 * Returns actual number of characters in string.
	 * @return actual number of characters.
	 */
	public int getActualLength() {
		return actualLength;
	}

	@Override
	public String getMessage() {
		StringBuilder b = new StringBuilder();
		if (getActualLength() < getThreshold()) {
			b.append("String is too short. It must have at least ");
		} else {
			b.append("String is too long. It must have at most ");
		};
		b.append(getThreshold()).append(" characters ");
		b.append("but instance has ").append(getActualLength()).append(".");
		return b.toString();
	}
}
