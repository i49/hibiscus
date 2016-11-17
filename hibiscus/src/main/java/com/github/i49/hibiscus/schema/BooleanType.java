package com.github.i49.hibiscus.schema;

import java.util.HashSet;
import java.util.Set;

import javax.json.JsonValue;

import com.github.i49.hibiscus.common.TypeId;
import com.github.i49.hibiscus.json.JsonValues;

/**
 * JSON boolean.
 */
public class BooleanType extends AbstractSimpleType<JsonValue> {
	
	@Override
	public TypeId getTypeId() {
		return TypeId.BOOLEAN;
	}

	/**
	 * Specifies values allowed for this type.
	 * @param values values allowed.
	 * @return this type.
	 */
	public BooleanType values(boolean... values) {
		Set<JsonValue> valueSet = new HashSet<>();
		for (boolean value: values) {
			valueSet.add(JsonValues.createBoolean(value));
		}
		addFacet(new ValueSetFacet<JsonValue>(valueSet));
		return this;
	}
}
