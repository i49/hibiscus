package com.github.i49.hibiscus.schema.problems;

import java.math.BigDecimal;
import java.util.Locale;

import javax.json.JsonNumber;

import com.github.i49.hibiscus.schema.Range;

/**
 * Problem that instance number is more than maximum value of given range.
 */
public class MoreThanMaximumProblem extends NumberRangeProblem {

	public MoreThanMaximumProblem(JsonNumber value, Range<BigDecimal> range) {
		super(value, range);
	}

	@Override
	public String getMessage(Locale locale) {
		return localize(locale, getActualValue(), getAllowedRange().getMaximum());
	}
}
