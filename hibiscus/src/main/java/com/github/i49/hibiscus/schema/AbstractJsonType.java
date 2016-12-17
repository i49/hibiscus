package com.github.i49.hibiscus.schema;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import javax.json.JsonValue;

import com.github.i49.hibiscus.facets.AssertionFacet;
import com.github.i49.hibiscus.facets.Facet;
import com.github.i49.hibiscus.problems.DescriptionSupplier;
import com.github.i49.hibiscus.problems.Problem;

/**
 * Skeletal class to implement {@code JsonType}.
 * 
 * @param <V> the type of JSON value.
 * @param <T> the type of actual class derived from this class.
 */
abstract class AbstractJsonType<V extends JsonValue, T extends JsonType> implements JsonType {

	private List<Facet<V>> facets;
	
	@Override
	public void validateInstance(JsonValue value, List<Problem> problems) {
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
	 * Returns a string representation of this type.
	 * @return a string representation of the object. 
	 */
	@Override
	public String toString() {
		return getTypeId().toString();
	}

	/**
	 * Adds a facet to this type.
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
	 * Specifies assertion on this type.
	 * @param predicate the lambda expression that will return true if the assertion succeeded or false if failed.
	 * @param description the object to supply a description to be reported when the assertion failed.
	 * @return this type.
	 * @exception SchemaException if any of specified parameters is {@code null}.
	 */
	public T assertion(Predicate<V> predicate, DescriptionSupplier<V> description) {
		if (predicate == null) {
			throw new SchemaException(Messages.METHOD_PARAMETER_IS_NULL("assertion", "predicate"));
		}
		if (description == null) {
			throw new SchemaException(Messages.METHOD_PARAMETER_IS_NULL("assertion", "description"));
		}
		facet(new AssertionFacet<V>(predicate, description));
		return self();
	}

	@SuppressWarnings("unchecked")
	private T self() {
		return (T)this;
	}
}
