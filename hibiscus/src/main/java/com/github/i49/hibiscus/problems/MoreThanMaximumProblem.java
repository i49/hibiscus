package com.github.i49.hibiscus.problems;

import java.math.BigDecimal;
import java.util.Locale;

import javax.json.JsonNumber;

import com.github.i49.hibiscus.common.Bound;

/**
 * Problem that instance number is more than maximum value of given range.
 */
public class MoreThanMaximumProblem extends NumberRangeProblem {

	/**
	 * Constructs this problem.
	 * @param value the actual value in JSON instance.
	 * @param bound the upper bound of the range allowed for the number type.
	 */
	public MoreThanMaximumProblem(JsonNumber value, Bound<BigDecimal> bound) {
		super(value, bound);
	}

	@Override
	public String buildDescription(Locale locale) {
		return Messages.MORE_THAN_MAXIMUM(locale, getActualValue(), getBound().getValue());
	}
}
