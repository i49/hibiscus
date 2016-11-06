package com.github.i49.hibiscus.schema.types;

import java.math.BigDecimal;
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

	@Override
	public IntegerType min(long value) {
		return (IntegerType)super.min(value);
	}

	@Override
	public IntegerType min(BigDecimal value) {
		return (IntegerType)super.min(value);
	}

	@Override
	public IntegerType exclusiveMin(boolean exclusive) {
		return (IntegerType)super.exclusiveMin(exclusive);
	}

	@Override
	public IntegerType max(long value) {
		return (IntegerType)super.max(value);
	}

	@Override
	public IntegerType max(BigDecimal value) {
		return (IntegerType)super.max(value);
	}
	
	@Override
	public IntegerType exclusiveMax(boolean exclusive) {
		return (IntegerType)super.exclusiveMax(exclusive);
	}
	
	/**
	 * Specifies values allowed for this type.
	 * @param values values allowed.
	 * @return this type.
	 */
	public IntegerType values(long... values) {
		IntegerType self = modifiable();
		Set<JsonValue> valueSet = new HashSet<>();
		for (long value: values) {
			valueSet.add(JsonValues.createNumber(value));
		}
		self.setValueSet(valueSet);
		return self;
	}
	
	@Override
	protected IntegerType modifiable() {
		return this;
	}
	
	/**
	 * Integer type without any constraints.
	 */
	private static class DefaultIntegerType extends IntegerType {
		@Override
		protected IntegerType modifiable() {
			return new IntegerType();
		}
	}
}
