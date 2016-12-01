package com.github.i49.hibiscus.facets;

import java.math.BigDecimal;
import java.util.List;

import javax.json.JsonNumber;

import com.github.i49.hibiscus.common.Bound;
import com.github.i49.hibiscus.problems.MoreThanMaximumProblem;
import com.github.i49.hibiscus.problems.NotLessThanMaximumProblem;
import com.github.i49.hibiscus.problems.Problem;

/**
 * Facet constraining a value space to values with a specific upper bound.  
 */
public class MaxNumberFacet implements Facet<JsonNumber> {

	private final Bound<BigDecimal> bound;
	
	/**
	 * Constructs this facet.
	 * @param limit the upper bound of value space.
	 * @param exclusive {@code true} if the bound is exclusive, otherwise {@code false}.
	 */
	public MaxNumberFacet(BigDecimal limit, boolean exclusive) {
		this.bound = Bound.of(limit, exclusive);
	}

	@Override
	public void apply(JsonNumber value, List<Problem> problems) {
		BigDecimal decimal = value.bigDecimalValue();
		int result = decimal.compareTo(bound.getValue());
		if (bound.isExclusive()) {
			if (result >= 0) {
				problems.add(new NotLessThanMaximumProblem(value, bound));
			}
		} else {
			if (result > 0) {
				problems.add(new MoreThanMaximumProblem(value, bound));
			}
		}
	}
}
