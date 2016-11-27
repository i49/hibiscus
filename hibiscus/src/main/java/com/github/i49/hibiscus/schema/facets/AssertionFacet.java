package com.github.i49.hibiscus.schema.facets;

import java.util.List;
import java.util.function.Predicate;

import javax.json.JsonValue;

import com.github.i49.hibiscus.problems.AssertionFailureProblem;
import com.github.i49.hibiscus.problems.DescriptionSupplier;
import com.github.i49.hibiscus.problems.Problem;

/**
 * Facet that specifies additional restriction on the values of the type. 
 *
 * @param <T> type of {@code JsonValue}.
 */
public class AssertionFacet<T extends JsonValue> implements Facet<T> {

	private final Predicate<T> predicate;
	private final DescriptionSupplier description;
	
	public AssertionFacet(Predicate<T> predicate, DescriptionSupplier description) {
		this.predicate = predicate;
		this.description = description;
	}

	@Override
	public void apply(T value, List<Problem> problems) {
		if (!predicate.test(value)) {
			problems.add(new AssertionFailureProblem(value, description));
		}
	}
}
