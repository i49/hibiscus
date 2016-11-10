package com.github.i49.hibiscus.schema.problems;

import java.math.BigDecimal;

import com.github.i49.hibiscus.schema.Range;

/**
 * Problem that instance number is out of range.
 */
public abstract class NumberRangeProblem extends Problem {
	
	private final BigDecimal value; 
	private final Range<BigDecimal> range;

	/**
	 * Constructs this problem.
	 * @param value instance value.
	 * @param range restricting range of value specified in schema.
	 */
	public NumberRangeProblem(BigDecimal value, Range<BigDecimal> range) {
		this.value = value;
		this.range = range;
	}
	
	/**
	 * Returns instance value.
	 * @return instance value.
	 */
	public BigDecimal getInstanceValue() {
		return value;
	}
	
	/**
	 * Returns range of value specified in schema.
	 * @return range of value.
	 */
	public Range<BigDecimal> getAllowedRange() {
		return range;
	}
}
