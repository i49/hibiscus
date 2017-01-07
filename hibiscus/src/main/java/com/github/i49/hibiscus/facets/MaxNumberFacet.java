package com.github.i49.hibiscus.facets;

import java.math.BigDecimal;
import java.util.List;

import javax.json.JsonNumber;

import com.github.i49.hibiscus.common.Bound;
import com.github.i49.hibiscus.problems.InclusiveUpperBoundProblem;
import com.github.i49.hibiscus.problems.JsonValueProblem;
import com.github.i49.hibiscus.problems.ExclusiveUpperBoundProblem;

/**
 * <strong>maxInclusive</strong> and <strong>maxExclusive</strong> facets
 * to restrict the value space to the numeric values included in the range which has a specific upper bound.
 * <p>
 * This facet is applicable to {@code number()} or {@code integer()} type.
 * If the value of the type is too large in comparison with the upper bound,
 * a problem will be reported by this facet.
 * The type of the problem to be reported depends on the inclusion of the bound in the valid range.
 * If the upper bound is specified as included in the valid range, {@link InclusiveUpperBoundProblem} will be reported,
 * and if the bound is specified as excluded from the range, {@link ExclusiveUpperBoundProblem} will be reported.
 * </p>
 */
public class MaxNumberFacet implements Facet<JsonNumber> {

	private final Bound<BigDecimal> bound;
	
	/**
	 * Constructs this facet.
	 * @param bound the upper bound value of the valid range.
	 * @param exclusive {@code true} if the bound is excluded from the valid range, 
	 *                  {@code false} if the bound is included in the valid range.
	 */
	public MaxNumberFacet(BigDecimal bound, boolean exclusive) {
		this.bound = Bound.of(bound, exclusive);
	}

	@Override
	public void apply(JsonNumber value, List<JsonValueProblem> problems) {
		BigDecimal decimal = value.bigDecimalValue();
		int result = decimal.compareTo(bound.getValue());
		if (bound.isExclusive()) {
			if (result >= 0) {
				problems.add(new ExclusiveUpperBoundProblem(bound));
			}
		} else {
			if (result > 0) {
				problems.add(new InclusiveUpperBoundProblem(bound));
			}
		}
	}
}
