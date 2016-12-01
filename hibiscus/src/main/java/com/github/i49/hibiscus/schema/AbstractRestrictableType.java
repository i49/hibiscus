package com.github.i49.hibiscus.schema;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import javax.json.JsonValue;

import com.github.i49.hibiscus.facets.AssertionFacet;
import com.github.i49.hibiscus.facets.Facet;
import com.github.i49.hibiscus.problems.DescriptionSupplier;
import com.github.i49.hibiscus.problems.Problem;

/**
 * {@code JsonType} to which facets can be applied.
 * 
 * @param <V> the type of JSON value.
 * @param <T> derived type.
 */
abstract class AbstractRestrictableType<V extends JsonValue, T extends JsonType> extends AbstractJsonType<V> {

	private Map<Class<?>, Facet<V>> facets;
	
	@Override
	public void validateInstance(JsonValue value, List<Problem> problems) {
		if (this.facets == null) {
			// Nothing to do.
			return;
		}
		@SuppressWarnings("unchecked")
		V actualValue = (V)value;
		for (Facet<V> facet: this.facets.values()) {
			facet.apply(actualValue, problems);
		}
	}
	
	/**
	 * Specifies assertion on this type.
	 * @param predicate the lambda expression that will return true if the assertion succeeded or false if failed.
	 * @param description the object to supply a description to be reported when the assertion failed.
	 * @return this type.
	 */
	public T assertion(Predicate<V> predicate, DescriptionSupplier<V> description) {
		if (predicate == null) {
			throw new SchemaException(Messages.METHOD_PARAMETER_IS_NULL("assertion", "predicate"));
		}
		if (description == null) {
			throw new SchemaException(Messages.METHOD_PARAMETER_IS_NULL("assertion", "description"));
		}
		addFacet(new AssertionFacet<V>(predicate, description));
		@SuppressWarnings("unchecked")
		T self = (T)this;
		return self;
	}

	/**
	 * Adds a facet to this type.
	 * @param facet the facet to be added. Cannot be {@code null}.
	 */
	void addFacet(Facet<V> facet) {
		if (facet == null) {
			throw new IllegalStateException("facet is null.");
		}
		if (this.facets == null) {
			this.facets = new HashMap<>();
		}
		this.facets.put(facet.getClass(), facet);
	}
}
