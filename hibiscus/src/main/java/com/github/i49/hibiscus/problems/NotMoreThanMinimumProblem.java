package com.github.i49.hibiscus.problems;

import java.math.BigDecimal;
import java.util.Locale;

import javax.json.JsonNumber;

import com.github.i49.hibiscus.common.Bound;

/**
 * Problem that instance number is less or equal to minimum value of given range.
 */
public class NotMoreThanMinimumProblem extends NumberRangeProblem {

	/**
	 * Constructs this problem.
	 * @param value the actual value in JSON instance.
	 * @param bound the lower bound of the range allowed for the number type.
	 */
	public NotMoreThanMinimumProblem(JsonNumber value, Bound<BigDecimal> bound) {
		super(value, bound);
	}

	@Override
	public String buildDescription(Locale locale) {
		return Messages.NOT_MORE_THAN_MINIMUM(locale, getActualValue(), getBound().getValue());
	}
}
