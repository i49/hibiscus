package com.github.i49.hibiscus.facets;

import java.util.List;
import java.util.function.Predicate;

import javax.json.JsonValue;

import com.github.i49.hibiscus.problems.AssertionFailureProblem;
import com.github.i49.hibiscus.problems.DescriptionSupplier;
import com.github.i49.hibiscus.problems.Problem;

/**
 * <strong>assertion</strong> facet to add arbitrary assertions on the type. 
 * <p>
 * This facet is applicable to all but {@code nil()} types.
 * If the assertion failed, {@link AssertionFailureProblem} will be reported 
 * by this facet.
 * </p>
 *
 * @param <V> the type of {@link JsonValue} to which this facet will be applied.
 */
public class AssertionFacet<V extends JsonValue> implements Facet<V> {

	private final Predicate<V> predicate;
	private final DescriptionSupplier<V> description;
	
	/**
	 * Constructs this facet.
	 * @param predicate the predicate to test the assertion on the value in JSON document 
	 *                  and return {@code true} when the assertion succeeded, 
	 *                  or {@code false} when the assertion failed.
	 * @param description the supplier which will supply the description of the problem when the assertion failed.
	 */
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
