package com.github.i49.hibiscus.problems;

import java.math.BigDecimal;

import javax.json.JsonNumber;

import com.github.i49.hibiscus.common.Bound;

/**
 * An abstract class representing a problem that a numeric value in JSON document is out of the valid range.
 */
public abstract class NumericRangeProblem extends JsonValueProblem<JsonNumber> {
	
	private final Bound<BigDecimal> bound;

	/**
	 * Constructs this problem.
	 * @param value actual value in JSON instance.
	 * @param bound the lower or upper bound of the range allowed for the number type.
	 */
	public NumericRangeProblem(JsonNumber value, Bound<BigDecimal> bound) {
		super(value);
		this.bound = bound;
	}
	
	/**
	 * Returns the bound of the range allowed for the type.
	 * @return the bound of the range.
	 */
	public Bound<BigDecimal> getBound() {
		return bound;
	}
}
