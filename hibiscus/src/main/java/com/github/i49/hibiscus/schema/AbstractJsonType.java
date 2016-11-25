package com.github.i49.hibiscus.schema;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.json.JsonValue;

import com.github.i49.hibiscus.problems.Problem;
import com.github.i49.hibiscus.schema.facets.Facet;

/**
 * Skeletal class to implement {@code JsonType}.
 * 
 * @param <T> the type of JSON value.
 */
abstract class AbstractJsonType<T extends JsonValue> implements JsonType {

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

	/**
	 * Verifies enumerated values.
	 * @param values the values specified in enumeration.
	 * @exception SchemaException if one of values is {@code null}.
	 */
	static void verifyValues(Object[] values) {
		int index = 0;
		for (Object value: values) {
			if (value == null) {
				throw new SchemaException(Messages.ONE_OF_VALUES_IS_NULL(index));
			}
			index++;
		}
	}
}
