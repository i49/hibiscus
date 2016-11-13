package com.github.i49.hibiscus.problems;

import java.math.BigDecimal;

import javax.json.JsonNumber;

import com.github.i49.hibiscus.common.Range;

/**
 * Problem that instance number is out of range.
 */
public abstract class NumberRangeProblem extends ValueProblem<JsonNumber> {
	
	private final Range<BigDecimal> range;

	/**
	 * Constructs this problem.
	 * @param value actual value in JSON instance.
	 * @param range expected range of number.
	 */
	public NumberRangeProblem(JsonNumber value, Range<BigDecimal> range) {
		super(value);
		this.range = range;
	}
	
	/**
	 * Returns range of value specified in schema.
	 * @return range of value.
	 */
	public Range<BigDecimal> getAllowedRange() {
		return range;
	}
}
