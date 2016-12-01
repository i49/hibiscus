package com.github.i49.hibiscus.facets;

import java.util.List;
import java.util.function.Predicate;

import javax.json.JsonValue;

import com.github.i49.hibiscus.problems.AssertionFailureProblem;
import com.github.i49.hibiscus.problems.DescriptionSupplier;
import com.github.i49.hibiscus.problems.Problem;

/**
 * Facet that specifies additional restriction on the values of the type. 
 *
 * @param <V> the type of {@code JsonValue}.
 */
public class AssertionFacet<V extends JsonValue> implements Facet<V> {

	private final Predicate<V> predicate;
	private final DescriptionSupplier<V> description;
	
	public AssertionFacet(Predicate<V> predicate, DescriptionSupplier<V> description) {
		this.predicate = predicate;
		this.description = description;
	}

	@Override
	public void apply(V value, List<Problem> problems) {
		if (!predicate.test(value)) {
			problems.add(new AssertionFailureProblem<V>(value, description));
		}
	}
}
