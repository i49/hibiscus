package com.github.i49.hibiscus.problems;

import javax.json.JsonValue;

/**
 * An abstract base class of the classes representing the problems caused by the value in JSON document,
 * which has a valid type but is out of the valid value space.
 *
 * @param <V> the type of {@link JsonValue} which is out of the valid value space and caused this problem.
 */
public abstract class JsonValueProblem<V extends JsonValue> extends AbstractProblem {

	private final V value;
	
	/**
	 * Constructs this problem.
	 * @param value the actual value in JSON document which caused this problem. Cannot be {@code null}.
	 */
	public JsonValueProblem(V value) {
		this.value = value;
	}
	
	/**
	 * Returns the actual value which caused this problem.
	 * @return the actual value which caused this problem.
	 */
	public V getActualValue() {
		return value;
	}
}
