package com.github.i49.hibiscus.problems;

import javax.json.JsonValue;

/**
 * An abstract base class of the classes representing the problems caused by the value in JSON document,
 * which has a valid type but is out of the valid value space.
 *
 * @param <V> the type of {@link JsonValue} which is out of the valid value space and caused this problem.
 */
public abstract class TypedJsonValueProblem<V extends JsonValue> extends JsonValueProblem {

	/**
	 * Constructs this problem.
	 */
	public TypedJsonValueProblem() {
		super();
	}

	@SuppressWarnings("unchecked")
	@Override
	public V getActualValue() {
		final JsonValue v = super.getActualValue();
		return (V)v;
	}
}
