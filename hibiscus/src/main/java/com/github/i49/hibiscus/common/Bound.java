package com.github.i49.hibiscus.common;

/**
 * Lower or upper bound of range.
 * This class is immutable.
 * @param <T> type of number.
 */
public class Bound<T> {

	private final T value;
	private final boolean exclusive;

	/**
	 * Creates bound object.
	 * @param value the value of the bound.
	 * @param exclusive {@code true} if the bound is exclusive, {@code false} otherwise.
	 * @param <T> type of number.
	 * @return created bound object.
	 */
	public static <T> Bound<T> of(T value, boolean exclusive) {
		return new Bound<T>(value, exclusive);
	}
	
	/**
	 * Constructs this object.
	 * @param value the value of the bound.
	 * @param exclusive {@code true} if the bound is exclusive, {@code false} otherwise.
	 */
	private Bound(T value, boolean exclusive) {
		this.value = value;
		this.exclusive = exclusive;
	}
	
	/**
	 * Returns the value of the bound.
	 * @return the value of the bound.
	 */
	public T getValue() {
		return value;
	}
	
	/**
	 * Returns whether this bound is inclusive or not.
	 * @return {@code true} if the bound is inclusive, {@code false} otherwise.
	 */
	public boolean isInclusive() {
		return !exclusive;
	}
	
	/**
	 * Returns whether this bound is exclusive or not.
	 * @return {@code true} if the bound is exclusive, {@code false} otherwise.
	 */
	public boolean isExclusive() {
		return exclusive;
	}
}
