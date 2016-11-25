package com.github.i49.hibiscus.schema;

import javax.json.JsonValue;

import com.github.i49.hibiscus.common.TypeId;
import com.github.i49.hibiscus.schema.facets.EnumerationFacet;

/**
 * JSON type for boolean value.
 */
public class BooleanType extends AbstractJsonType<JsonValue> implements SimpleType {
	
	/**
	 * Constructs this type.
	 */
	public BooleanType() {
	}

	@Override
	public TypeId getTypeId() {
		return TypeId.BOOLEAN;
	}

	/**
	 * Specifies values allowed for this type.
	 * @param values the values allowed.
	 * @return this type.
	 */
	public BooleanType enumeration(boolean... values) {
		addFacet(EnumerationFacet.of(values));
		return this;
	}
}
