package com.github.i49.hibiscus.common;

/**
 * Range representing a range of numbers of the same type.
 * This class is immutable.
 * @param <T> type of number.
 */
public class Range<T> implements BaseRange {

	private final T minimum;
	private final T maximum;
	private final boolean exclusiveMinimum;
	private final boolean exclusiveMaximum;
	
	/**
	 * Creates a range which has specified minimum and maximum number.
	 * @param minimum minimum number which is inclusive.
	 * @param maximum maximum number which is inclusive.
	 * @return range.
	 */
	public static <T> Range<T> of(T minimum, T maximum) {
		return new Range<T>(minimum, maximum, false, false);
	}

	public static <T> Range<T> of (T minimum, T maximum, boolean exclusiveMinimum, boolean exclusiveMaximum) {
		return new Range<T>(minimum, maximum, exclusiveMinimum, exclusiveMaximum);
	}
	
	private Range(T minimum, T maximum, boolean exclusiveMinimum, boolean exclusiveMaximum) {
		this.minimum = minimum;
		this.maximum = maximum;
		this.exclusiveMinimum = exclusiveMinimum;
		this.exclusiveMaximum = exclusiveMaximum;
	}
	
	@Override
	public boolean hasMinimum() {
		return minimum != null;
	}
	
	@Override
	public boolean hasExlusiveMinimum() {
		return hasMinimum() && exclusiveMinimum;
	}
	
	public T getMinimum() {
		return minimum;
	}
	
	@Override
	public boolean hasMaximum() {
		return maximum != null;
	}

	@Override
	public boolean hasExclusiveMaximum() {
		return hasMaximum() && exclusiveMaximum;
	}

	public T getMaximum() {
		return maximum;
	}
}
