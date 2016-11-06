package com.github.i49.hibiscus.schema.problems;

import java.math.BigDecimal;

import com.github.i49.hibiscus.schema.Range;

public abstract class NumberRangeProblem extends Problem {
	
	private final BigDecimal value; 
	private final Range<BigDecimal> range;
	
	public NumberRangeProblem(BigDecimal value, Range<BigDecimal> range) {
		this.value = value;
		this.range = range;
	}
	
	public BigDecimal getInstanceValue() {
		return value;
	}
	
	public Range<BigDecimal> getRangeInSchema() {
		return range;
	}
}
