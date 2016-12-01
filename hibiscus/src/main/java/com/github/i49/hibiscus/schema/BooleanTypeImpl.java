package com.github.i49.hibiscus.schema;

import static com.github.i49.hibiscus.schema.Enumerations.valueSet;

import javax.json.JsonValue;

import com.github.i49.hibiscus.common.TypeId;
import com.github.i49.hibiscus.facets.EnumerationFacet;

/**
 * Implementation of {@code BooleanType}.
 */
class BooleanTypeImpl extends AbstractRestrictableType<JsonValue, BooleanType> implements BooleanType {

	public BooleanTypeImpl() {
	}

	@Override
	public TypeId getTypeId() {
		return TypeId.BOOLEAN;
	}

	@Override
	public BooleanType enumeration(boolean... values) {
		addFacet(EnumerationFacet.of(valueSet(values)));
		return this;
	}
}
