package com.github.i49.hibiscus.schema;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.json.JsonValue;

import com.github.i49.hibiscus.problems.Problem;
import com.github.i49.hibiscus.schema.facets.Facet;

abstract class AbstractSimpleType<T extends JsonValue> extends SimpleType {

	private Map<Class<?>, Facet<T>> facets;

	@Override
	public void validateInstance(JsonValue value, List<Problem> problems) {
		if (this.facets == null) {
			return;
		}
		@SuppressWarnings("unchecked")
		T actualValue = (T)value;
		for (Facet<T> facet: this.facets.values()) {
			facet.apply(actualValue, problems);
		}
	}

	protected void addFacet(Facet<T> facet) {
		if (this.facets == null) {
			this.facets = new HashMap<>();
		}
		this.facets.put(facet.getClass(), facet);
	}
}
