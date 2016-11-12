package com.github.i49.hibiscus.schema;

public class IntRange implements BaseRange {

	private final int minimum;
	private final int maximum;
	
	public static IntRange of(int minimum, int maximum) {
		return new IntRange(minimum, maximum);
	}
	
	private IntRange(int minimum, int maximum) {
		this.minimum = minimum;
		this.maximum = maximum;
	}

	@Override
	public boolean hasMinimum() {
		return (minimum != -1);
	}

	@Override
	public boolean hasExlusiveMinimum() {
		return false;
	}
	
	public int getMinimum() {
		return minimum;
	}

	@Override
	public boolean hasMaximum() {
		return (maximum != -1);
	}

	@Override
	public boolean hasExclusiveMaximum() {
		return false;
	}

	public int getMaximum() {
		return maximum;
	}
}
