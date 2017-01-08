package com.github.i49.hibiscus.validation;

/**
 * A future object that provides the value immediately.
 *
 * @param <V> The result type returned by this Future's get method.
 */
class Immediate<V> extends AbstractFuture<V> {
	
	private final V value;
	
	/**
	 * Constructs this object.
	 * @param value the value to be returned by {@link #get()} method.
	 */
	Immediate(V value) {
		this.value = value;
	}

	@Override
	public V get() {
		return value;
	}
}
