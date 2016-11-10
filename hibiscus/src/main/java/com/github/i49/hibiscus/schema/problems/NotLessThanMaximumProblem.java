package com.github.i49.hibiscus.schema.problems;

import java.math.BigDecimal;
import java.util.Locale;

import com.github.i49.hibiscus.schema.Range;

/**
 * Problem that instance number is greater or equal to maximum value of given range.
 */
public class NotLessThanMaximumProblem extends NumberRangeProblem {

	public NotLessThanMaximumProblem(BigDecimal value, Range<BigDecimal> range) {
		super(value, range);
	}

	@Override
	public String getMessage(Locale locale) {
		return localize(locale, getInstanceValue(), getAllowedRange().getMaximum());
	}
}
