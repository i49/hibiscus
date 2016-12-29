package com.github.i49.hibiscus.problems;

import java.math.BigDecimal;
import java.util.Locale;

import javax.json.JsonNumber;

import com.github.i49.hibiscus.common.Bound;

/**
 * Problem that a numeric value is greater than or equal to the upper bound of the valid range
 * specified in the schema. The value of the upper bound is not included in the valid range
 * and therefore causes this problem to be reported.
 *
 * <p>This problem can be caused by {@code integer()} or {@code number()} type.</p>
 */
public class ExclusiveUpperBoundProblem extends NumericRangeProblem {

	/**
	 * Constructs this problem.
	 * @param value the actual value in JSON document.
	 * @param bound the upper bound of the range allowed for the number type.
	 */
	public ExclusiveUpperBoundProblem(JsonNumber value, Bound<BigDecimal> bound) {
		super(value, bound);
	}

	@Override
	public String buildDescription(Locale locale) {
		return Messages.EXCLUSIVE_UPPER_BOUND_PROBLEM(locale, getActualValue(), getBound().getValue());
	}
}
