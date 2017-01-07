package com.github.i49.hibiscus.problems;

import javax.json.JsonValue;

/**
 * An abstract base class of the classes representing the problems caused by the value in JSON document,
 * which has a valid type but is out of the valid value space.
 *
 * @param <V> the type of {@link JsonValue} which is out of the valid value space and caused this problem.
 */
public abstract class JsonValueProblem extends AbstractProblem {

	private JsonValue value;
	
	/**
	 * Constructs this problem.
	 */
	public JsonValueProblem() {
	}
	
	/**
	 * Returns the actual value which caused this problem.
	 * @return the actual value which caused this problem.
	 */
	public JsonValue getActualValue() {
		return value;
	}
	
	public void setActualValue(JsonValue value) {
		this.value = value;
	}
}
