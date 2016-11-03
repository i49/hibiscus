package com.github.i49.hibiscus.schema.types;

import java.util.HashSet;
import java.util.Set;

import javax.json.JsonValue;

import com.github.i49.hibiscus.json.JsonValues;
import com.github.i49.hibiscus.schema.TypeId;

/**
 * JSON boolean.
 */
public class BooleanType extends SimpleType {
	
	private static final BooleanType DEFAULT = new DefaultBooleanType();
	
	/**
	 * Returns this type with default settings.
	 * @return immutable type with default settings.
	 */
	public static BooleanType getDefault() {
		return DEFAULT;
	}

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
		setValueSet(valueSet);
		return this;
	}
	
	/**
	 * Boolean type without any constraints.
	 */
	private static class DefaultBooleanType extends BooleanType {
		
		@Override
		public BooleanType values(boolean... values) {
			return new BooleanType().values(values);
		}
	}
}
