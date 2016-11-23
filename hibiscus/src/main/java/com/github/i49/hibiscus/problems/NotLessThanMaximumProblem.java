package com.github.i49.hibiscus.problems;

import java.math.BigDecimal;
import java.util.Locale;

import javax.json.JsonNumber;

import com.github.i49.hibiscus.common.Bound;

/**
 * Problem that instance number is greater or equal to maximum value of given range.
 */
public class NotLessThanMaximumProblem extends NumberRangeProblem {

	/**
	 * Constructs this problem.
	 * @param value the actual value in JSON instance.
	 * @param bound the upper bound of the range allowed for the number type.
	 */
	public NotLessThanMaximumProblem(JsonNumber value, Bound<BigDecimal> bound) {
		super(value, bound);
	}

	@Override
	public String buildDescription(Locale locale) {
		return Messages.NOT_LESS_THAN_MAXIMUM(locale, getActualValue(), getBound().getValue());
	}
}
