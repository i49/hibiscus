package com.github.i49.hibiscus.schema;

import java.math.BigDecimal;

import com.github.i49.hibiscus.common.TypeId;

/**
 * JSON type for numeric value without a fraction or exponent part.
 */
public class IntegerType extends NumberType {

	/**
	 * Constructs this type.
	 */
	public IntegerType() {
	}
	
	@Override
	public TypeId getTypeId() {
		return TypeId.INTEGER;
	}

	@Override
	public IntegerType minInclusive(long value) {
		return (IntegerType)super.minInclusive(value);
	}

	@Override
	public IntegerType minExclusive(long value) {
		return (IntegerType)super.minExclusive(value);
	}

	@Override
	public IntegerType minInclusive(BigDecimal value) {
		return (IntegerType)super.minInclusive(value);
	}

	@Override
	public IntegerType minExclusive(BigDecimal value) {
		return (IntegerType)super.minExclusive(value);
	}

	@Override
	public IntegerType maxInclusive(long value) {
		return (IntegerType)super.maxInclusive(value);
	}

	@Override
	public IntegerType maxExclusive(long value) {
		return (IntegerType)super.maxExclusive(value);
	}

	@Override
	public IntegerType maxInclusive(BigDecimal value) {
		return (IntegerType)super.maxInclusive(value);
	}
	
	@Override
	public IntegerType maxExclusive(BigDecimal value) {
		return (IntegerType)super.maxExclusive(value);
	}
	
	@Override
	public IntegerType enumeration(long... values) {
		return (IntegerType)super.enumeration(values);
	}

	@Override
	public IntegerType enumeration(BigDecimal... values) {
		return (IntegerType)super.enumeration(values);
	}
}
