package com.github.i49.hibiscus.schema;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.json.JsonValue;

import com.github.i49.hibiscus.common.TypeId;
import com.github.i49.hibiscus.json.JsonValues;

/**
 * JSON number without a fraction or exponent part.
 */
public class IntegerType extends NumberType {

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
		Set<JsonValue> valueSet = new HashSet<>();
		for (long value: values) {
			valueSet.add(JsonValues.createNumber(value));
		}
		this.setValueSet(valueSet);
		return this;
	}
}
