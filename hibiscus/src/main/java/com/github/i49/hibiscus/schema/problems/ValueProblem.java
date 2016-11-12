package com.github.i49.hibiscus.schema.problems;

import javax.json.JsonValue;

/**
 * Base class of problems caused by value assigned in JSON instance.
 *
 * @param <T> type of value.
 */
public abstract class ValueProblem<T extends JsonValue> extends Problem {

	private final T value;
	
	/**
	 * Constructs this problem.
	 * @param value actual value assigned in JSON instance.
	 */
	public ValueProblem(T value) {
		this.value = value;
	}
	
	/**
	 * Returns actual value in JSON instance.
	 * @return actual value.
	 */
	public T getActualValue() {
		return value;
	}
}
