package com.github.i49.hibiscus.schema;

import java.util.HashSet;
import java.util.Set;

import javax.json.JsonValue;

import com.github.i49.hibiscus.facets.EnumerationFacet;

/**
 * Implementation of {@code BooleanType}.
 */
class BooleanTypeImpl extends AbstractJsonType<JsonValue, BooleanType> implements BooleanType {

	/**
	 * Constructs this type.
	 */
	BooleanTypeImpl() {
	}

	@Override
	public BooleanType enumeration(boolean... values) {
		Set<Object> enumerators = new HashSet<>();
		for (boolean value: values) {
			enumerators.add(value);
		}
		return facet(EnumerationFacet.of(enumerators, BooleanTypeImpl::mapValue));
	}
	
	private static Boolean mapValue(JsonValue value) {
		if (value == JsonValue.TRUE) {
			return Boolean.TRUE;
		} else if (value == JsonValue.FALSE) {
			return Boolean.FALSE;
		} else {
			return null;
		}
	}
}
