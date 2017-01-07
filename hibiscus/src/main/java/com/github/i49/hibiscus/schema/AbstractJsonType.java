package com.github.i49.hibiscus.schema;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import javax.json.JsonValue;

import com.github.i49.hibiscus.facets.AssertionFacet;
import com.github.i49.hibiscus.facets.Facet;
import com.github.i49.hibiscus.problems.ProblemDescriber;
import com.github.i49.hibiscus.problems.JsonValueProblem;

/**
 * A skeletal class to help implement {@link JsonType}.
 * 
 * @param <V> the type of {@link JsonValue} to be validated by this class.
 * @param <T> the type of the concrete class which is derived from this class.
 */
abstract class AbstractJsonType<V extends JsonValue, T extends JsonType> implements JsonType {

	private List<Facet<V>> facets;
	
	@Override
	public void validateInstance(JsonValue value, List<JsonValueProblem> problems) {
		if (this.facets == null) {
			// Nothing to do.
			return;
		}
		@SuppressWarnings("unchecked")
		V actualValue = (V)value;
		for (Facet<V> facet: this.facets) {
			facet.apply(actualValue, problems);
		}
	}

	/**
	 * Returns a string representation of this {@link JsonType}.
	 * @return a string representation of this {@link JsonType}. 
	 */
	@Override
	public String toString() {
		return getTypeId().toString();
	}

	/**
	 * Adds a {@link Facet} which restricts the value space of this type.
	 * @param facet the facet to be added. Cannot be {@code null}.
	 * @return this type.
	 * @exception SchemaException if facet specified is {@code null}.
	 */
	public T facet(Facet<V> facet) {
		if (facet == null) {
			throw new SchemaException(Messages.METHOD_PARAMETER_IS_NULL("facet", "facet"));
		}
		if (this.facets == null) {
			this.facets = new ArrayList<>();
		}
		this.facets.add(facet);
		return self();
	}
	
	/**
	 * Makes a assertion on the values of this type.
	 * @param predicate the lambda expression that will return {@code true} if the assertion succeeded or {@code false} if failed.
	 * @param describer the object supplying the description of the problem to be reported when the assertion failed.
	 * @return this type.
	 * @exception SchemaException if any of specified parameters is {@code null}.
	 */
	public T assertion(Predicate<V> predicate, ProblemDescriber<V> describer) {
		if (predicate == null) {
			throw new SchemaException(Messages.METHOD_PARAMETER_IS_NULL("assertion", "predicate"));
		}
		if (describer == null) {
			throw new SchemaException(Messages.METHOD_PARAMETER_IS_NULL("assertion", "describer"));
		}
		facet(new AssertionFacet<V>(predicate, describer));
		return self();
	}

	@SuppressWarnings("unchecked")
	private T self() {
		return (T)this;
	}
}
