package com.github.i49.hibiscus.problems;

import java.util.function.Supplier;

import javax.json.JsonValue;

/**
 * An abstract base class of the classes representing the problems caused by the value in JSON document,
 * which has a valid type but is out of the valid value space.
 */
public abstract class JsonValueProblem extends AbstractProblem {

	private Supplier<JsonValue> valueSupplier;
	
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
		return valueSupplier.get();
	}
	
	/**
	 * Assigns a supplier of the actual value that caused this problem.
	 * @param valueSupplier the supplier of the actual value.
	 */
	public void setActualValueSupplier(Supplier<JsonValue> valueSupplier) {
		this.valueSupplier = valueSupplier;
	}
}
