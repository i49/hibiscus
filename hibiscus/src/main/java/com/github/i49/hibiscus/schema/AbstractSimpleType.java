package com.github.i49.hibiscus.schema;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.json.JsonValue;

import com.github.i49.hibiscus.problems.Problem;
import com.github.i49.hibiscus.schema.facets.Facet;

/**
 * Skeletal class to implement {@code SimleType}.
 */
abstract class AbstractSimpleType<T extends JsonValue> extends AbstractJsonType {

	private Map<Class<?>, Facet<T>> facets;

	@Override
	public void validateInstance(JsonValue value, List<Problem> problems) {
		if (this.facets == null) {
			// Nothing to do.
			return;
		}
		@SuppressWarnings("unchecked")
		T actualValue = (T)value;
		for (Facet<T> facet: this.facets.values()) {
			facet.apply(actualValue, problems);
		}
	}

	/**
	 * Adds facet to this type.
	 * @param facet the facet to be added. Cannot be {@code null}.
	 */
	void addFacet(Facet<T> facet) {
		if (facet == null) {
			throw new IllegalStateException("facet is null.");
		}
		if (this.facets == null) {
			this.facets = new HashMap<>();
		}
		this.facets.put(facet.getClass(), facet);
	}
}
