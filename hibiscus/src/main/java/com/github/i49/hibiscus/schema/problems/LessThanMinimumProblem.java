package com.github.i49.hibiscus.schema.problems;

import java.math.BigDecimal;
import java.util.Locale;

import com.github.i49.hibiscus.schema.Range;

/**
 * Problem that instance number is less than minimum value of given of range.
 */
public class LessThanMinimumProblem extends NumberRangeProblem {

	public LessThanMinimumProblem(BigDecimal value, Range<BigDecimal> range) {
		super(value, range);
	}

	@Override
	public String getMessage(Locale locale) {
		return localize(locale, getInstanceValue(), getAllowedRange().getMinimum());
	}
}
