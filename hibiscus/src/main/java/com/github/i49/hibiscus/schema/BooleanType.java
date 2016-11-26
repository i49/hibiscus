package com.github.i49.hibiscus.schema;

import javax.json.JsonValue;

import com.github.i49.hibiscus.common.TypeId;
import com.github.i49.hibiscus.schema.facets.EnumerationFacet;

import static com.github.i49.hibiscus.schema.Enumerations.*;

/**
 * JSON type for boolean value.
 */
public class BooleanType extends AbstractRestrictableType<JsonValue> implements AtomicType {
	
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
		addFacet(EnumerationFacet.of(valueSet(values)));
		return this;
	}
}
