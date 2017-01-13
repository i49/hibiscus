package com.github.i49.hibiscus.problems;

import java.math.BigDecimal;

import javax.json.JsonNumber;

import com.github.i49.hibiscus.common.Bound;

/**
 * An abstract class representing a problem that a numeric value in JSON document is out of the valid range.
 */
public abstract class NumericRangeProblem extends TypedProblem<JsonNumber> {
	
	private final Bound<BigDecimal> bound;

	/**
	 * Constructs this problem.
	 * @param bound the lower or upper bound of the valid range for the type declared in the schema.
	 */
	public NumericRangeProblem(Bound<BigDecimal> bound) {
		this.bound = bound;
	}
	
	/**
	 * Returns the specified bound of the valid range for the type.
	 * @return the specified bound of the valid range.
	 */
	public Bound<BigDecimal> getBound() {
		return bound;
	}
}
