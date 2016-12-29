package com.github.i49.hibiscus.problems;

import java.math.BigDecimal;
import java.util.Locale;

import javax.json.JsonNumber;

import com.github.i49.hibiscus.common.Bound;

/**
 * Problem that a numeric value is less than the lower bound of the valid range
 * specified in the schema. The value of the lower bound is included in the valid range
 * and therefore does not cause this problem.
 *
 * <p>This problem can be caused by {@code integer()} or {@code number()} type.</p>
 */
public class InclusiveLowerBoundProblem extends NumericRangeProblem {

	/**
	 * Constructs this problem.
	 * @param value the actual value in JSON document.
	 * @param bound the lower bound of the range allowed for the number type.
	 */
	public InclusiveLowerBoundProblem(JsonNumber value, Bound<BigDecimal> bound) {
		super(value, bound);
	}

	@Override
	public String buildDescription(Locale locale) {
		return Messages.INCLUSIVE_LOWER_BOUND_PROBLEM(locale, getActualValue(), getBound().getValue());
	}
}
