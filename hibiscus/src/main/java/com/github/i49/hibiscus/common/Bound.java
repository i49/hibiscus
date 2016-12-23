package com.github.i49.hibiscus.common;

/**
 * A boundary of a numeric range of generic type. 
 * 
 * <p>Either lower or upper boundary can be represented by this class.
 * This class is immutable and cannot be modified once constructed.</p>
 * 
 * @param <T> the type of the boundary value.
 */
public class Bound<T> {

	private final T value;
	private final boolean exclusive;

	/**
	 * Creates a new bound object.
	 * @param value the value of the boundary.
	 * @param exclusive {@code true} if the boundary is excluded from the valid range,
	 *                  {@code false} if the boundary is included in the valid range.
	 * @param <T> the type of the boundary value.
	 * @return created bound object.
	 */
	public static <T> Bound<T> of(T value, boolean exclusive) {
		return new Bound<T>(value, exclusive);
	}
	
	/**
	 * Constructs this object.
	 * @param value the value of the boundary.
	 * @param exclusive {@code true} if the boundary is excluded from the valid range,
	 *                  {@code false} if the boundary is included in the valid range.
	 */
	private Bound(T value, boolean exclusive) {
		this.value = value;
		this.exclusive = exclusive;
	}
	
	/**
	 * Returns the boundary value.
	 * @return the boundary value.
	 */
	public T getValue() {
		return value;
	}
	
	/**
	 * Returns whether the boundary value is included in the valid range or not.
	 * @return {@code true} if the bound is included in the valid range, {@code false} otherwise.
	 */
	public boolean isInclusive() {
		return !exclusive;
	}
	
	/**
	 * Returns whether the boundary value is excluded from the valid range or not.
	 * @return {@code true} if the bound is excluded from the valid range, {@code false} otherwise.
	 */
	public boolean isExclusive() {
		return exclusive;
	}
}
