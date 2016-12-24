package com.github.i49.hibiscus.facets;

import java.math.BigDecimal;
import java.util.List;

import javax.json.JsonNumber;

import com.github.i49.hibiscus.common.Bound;
import com.github.i49.hibiscus.problems.InclusiveUpperBoundProblem;
import com.github.i49.hibiscus.problems.ExclusiveUpperBoundProblem;
import com.github.i49.hibiscus.problems.Problem;

/**
 * <strong>maxInclusive</strong> and <strong>maxExclusive</strong> facets
 * to restrict the value space to the numeric values in the range which has a specific upper bound.
 */
public class MaxNumberFacet implements Facet<JsonNumber> {

	private final Bound<BigDecimal> bound;
	
	/**
	 * Constructs this facet.
	 * @param limit the upper bound value of the value space.
	 * @param exclusive {@code true} if the bound is excluded from the valid range, otherwise {@code false}.
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
				problems.add(new ExclusiveUpperBoundProblem(value, bound));
			}
		} else {
			if (result > 0) {
				problems.add(new InclusiveUpperBoundProblem(value, bound));
			}
		}
	}
}
