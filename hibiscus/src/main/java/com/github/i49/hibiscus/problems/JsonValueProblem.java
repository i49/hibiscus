package com.github.i49.hibiscus.problems;

import java.util.concurrent.Future;

import javax.json.JsonValue;

/**
 * An abstract base class of the classes representing the problems caused by the value in JSON document,
 * which has a valid type but is out of the valid value space.
 */
public abstract class JsonValueProblem extends AbstractProblem {

	private Future<JsonValue> value;
	
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
		try {
			return value.get();
		} catch (Exception e) {
			// This never happens.
			return null;
		}
	}
	
	/**
	 * Assigns the future object that will provide the actual value that caused this problem.
	 * @param value the future object that will provide the actual value.
	 */
	public void setActualValue(Future<JsonValue> value) {
		this.value = value;
	}
}
