package com.github.i49.hibiscus.schema;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.json.JsonNumber;

import com.github.i49.hibiscus.common.TypeId;
import com.github.i49.hibiscus.json.JsonValues;
import com.github.i49.hibiscus.schema.facets.ValueSetFacet;

/**
 * JSON number without a fraction or exponent part.
 */
public class IntegerType extends NumberType {

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

	/**
	 * Specifies values allowed for this type.
	 * @param values values allowed.
	 * @return this type.
	 */
	public IntegerType values(long... values) {
		Set<JsonNumber> valueSet = new HashSet<>();
		for (long value: values) {
			valueSet.add(JsonValues.createNumber(value));
		}
		addFacet(ValueSetFacet.of(valueSet));
		return this;
	}
}
