package com.github.i49.hibiscus.schema.types;

import java.util.HashSet;
import java.util.Set;

import javax.json.JsonValue;

import com.github.i49.hibiscus.json.JsonValues;
import com.github.i49.hibiscus.schema.TypeId;

/**
 * JSON number without a fraction or exponent part.
 */
public class IntegerType extends NumberType {

	private static final IntegerType DEFAULT = new DefaultIntegerType();
	
	/**
	 * Returns this type with default settings.
	 * @return immutable type with default settings.
	 */
	public static IntegerType getDefault() {
		return DEFAULT;
	}
	
	@Override
	public TypeId getTypeId() {
		return TypeId.INTEGER;
	}

	/**
	 * Specifies values allowed for this type.
	 * @param values values allowed.
	 * @return this type.
	 */
	public IntegerType values(long... values) {
		Set<JsonValue> valueSet = new HashSet<>();
		for (long value: values) {
			valueSet.add(JsonValues.createNumber(value));
		}
		setValueSet(valueSet);
		return this;
	}
	
	/**
	 * Integer type without any constraints.
	 */
	private static class DefaultIntegerType extends IntegerType {
		
		@Override
		public IntegerType values(long... values) {
			return new IntegerType().values(values);
		}
	}
}
