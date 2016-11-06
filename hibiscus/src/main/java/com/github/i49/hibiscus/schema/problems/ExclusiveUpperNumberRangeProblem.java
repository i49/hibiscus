package com.github.i49.hibiscus.schema.problems;

import java.math.BigDecimal;

import com.github.i49.hibiscus.schema.Range;

public class ExclusiveUpperNumberRangeProblem extends NumberRangeProblem {

	public ExclusiveUpperNumberRangeProblem(BigDecimal value, Range<BigDecimal> range) {
		super(value, range);
	}

	@Override
	public String getMessage() {
		return null;
	}
}
