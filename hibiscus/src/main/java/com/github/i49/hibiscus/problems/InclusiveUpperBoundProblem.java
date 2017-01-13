package com.github.i49.hibiscus.problems;

import java.math.BigDecimal;
import java.util.Locale;

import com.github.i49.hibiscus.common.Bound;

/**
 * Problem that a numeric value is greater than the upper bound of the valid range
 * specified in the schema. The value of the upper bound is included in the valid range
 * and therefore does not cause this problem.
 *
 * <p>This problem can be caused by {@code integer()} or {@code number()} type.</p>
 */
public class InclusiveUpperBoundProblem extends NumericRangeProblem {

	/**
	 * Constructs this problem.
	 * @param bound the upper bound of the range allowed for the number type.
	 */
	public InclusiveUpperBoundProblem(Bound<BigDecimal> bound) {
		super(bound);
	}

	@Override
	public String buildDescription(Locale locale) {
		return Messages.INCLUSIVE_UPPER_BOUND_PROBLEM(locale, getCauseValue(), getBound().getValue());
	}
}
