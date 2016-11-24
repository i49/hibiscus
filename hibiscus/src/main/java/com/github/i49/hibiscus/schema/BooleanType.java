package com.github.i49.hibiscus.schema;

import java.util.HashSet;
import java.util.Set;

import javax.json.JsonValue;

import com.github.i49.hibiscus.common.TypeId;
import com.github.i49.hibiscus.json.JsonValues;
import com.github.i49.hibiscus.schema.facets.ValueSetFacet;

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
	public BooleanType values(boolean... values) {
		Set<JsonValue> valueSet = new HashSet<>();
		for (boolean value: values) {
			valueSet.add(JsonValues.createBoolean(value));
		}
		addFacet(ValueSetFacet.of(valueSet));
		return this;
	}
}
