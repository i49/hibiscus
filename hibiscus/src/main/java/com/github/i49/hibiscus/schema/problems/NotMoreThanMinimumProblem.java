package com.github.i49.hibiscus.schema.problems;

import java.math.BigDecimal;
import java.util.Locale;

import javax.json.JsonNumber;

import com.github.i49.hibiscus.schema.Range;

/**
 * Problem that instance number is less or equal to minimum value of given range.
 */
public class NotMoreThanMinimumProblem extends NumberRangeProblem {

	public NotMoreThanMinimumProblem(JsonNumber value, Range<BigDecimal> range) {
		super(value, range);
	}

	@Override
	public String getMessage(Locale locale) {
		return localize(locale, getActualValue(), getAllowedRange().getMinimum());
	}
}
