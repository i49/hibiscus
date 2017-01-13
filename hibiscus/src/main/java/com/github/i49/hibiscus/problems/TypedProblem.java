package com.github.i49.hibiscus.problems;

import javax.json.JsonValue;

/**
 * A problem class that is caused by a value of a specific type.
 *
 * @param <V> the type of the {@link JsonValue} which caused this problem {@code V}.
 */
public abstract class TypedProblem<V extends JsonValue> extends AbstractProblem {

	/**
	 * Constructs this problem.
	 */
	public TypedProblem() {
		super();
	}

	@SuppressWarnings("unchecked")
	@Override
	public V getCauseValue() {
		final JsonValue v = super.getCauseValue();
		return (V)v;
	}
}
