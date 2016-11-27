package com.github.i49.hibiscus.problems;

import javax.json.JsonValue;

/**
 * Base class of problems caused by values assigned in JSON document.
 *
 * @param <V> type of {@code JsonValue}.
 */
public abstract class ValueProblem<V extends JsonValue> extends AbstractProblem {

	private final V value;
	
	/**
	 * Constructs this problem.
	 * @param value the actual value assigned in JSON document.
	 */
	public ValueProblem(V value) {
		this.value = value;
	}
	
	/**
	 * Returns the actual value in JSON document.
	 * @return the actual value.
	 */
	public V getActualValue() {
		return value;
	}
}
