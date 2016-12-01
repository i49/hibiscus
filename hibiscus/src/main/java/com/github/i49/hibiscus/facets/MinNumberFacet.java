package com.github.i49.hibiscus.facets;

import java.math.BigDecimal;
import java.util.List;

import javax.json.JsonNumber;

import com.github.i49.hibiscus.common.Bound;
import com.github.i49.hibiscus.problems.LessThanMinimumProblem;
import com.github.i49.hibiscus.problems.NotMoreThanMinimumProblem;
import com.github.i49.hibiscus.problems.Problem;

/**
 * Facet constraining a value space to values with a specific lower bound.  
 */
public class MinNumberFacet implements Facet<JsonNumber> {

	private final Bound<BigDecimal> bound;
	
	/**
	 * Constructs this facet.
	 * @param limit the lower bound of value space.
	 * @param exclusive {@code true} if the bound is exclusive, otherwise {@code false}.
	 */
	public MinNumberFacet(BigDecimal limit, boolean exclusive) {
		this.bound = Bound.of(limit, exclusive);
	}

	@Override
	public void apply(JsonNumber value, List<Problem> problems) {
		BigDecimal decimal = value.bigDecimalValue();
		int result = decimal.compareTo(bound.getValue());
		if (bound.isExclusive()) {
			if (result <= 0) {
				problems.add(new NotMoreThanMinimumProblem(value, bound));
			}
		} else {
			if (result < 0) {
				problems.add(new LessThanMinimumProblem(value, bound));
			}
		}
	}
}
