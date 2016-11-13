package com.github.i49.hibiscus.problems;

import java.math.BigDecimal;
import java.util.Locale;

import javax.json.JsonNumber;

import com.github.i49.hibiscus.common.Range;

/**
 * Problem that instance number is less than minimum value of given of range.
 */
public class LessThanMinimumProblem extends NumberRangeProblem {

	public LessThanMinimumProblem(JsonNumber value, Range<BigDecimal> range) {
		super(value, range);
	}

	@Override
	public String getMessage(Locale locale) {
		return localize(locale, getActualValue(), getAllowedRange().getMinimum());
	}
}
