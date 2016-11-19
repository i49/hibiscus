package com.github.i49.hibiscus.schema;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.json.JsonNumber;

import com.github.i49.hibiscus.common.TypeId;
import com.github.i49.hibiscus.json.JsonValues;
import com.github.i49.hibiscus.schema.facets.MaxNumberFacet;
import com.github.i49.hibiscus.schema.facets.MinNumberFacet;
import com.github.i49.hibiscus.schema.facets.ValueSetFacet;

/**
 * JSON type for numeric value, including integer.
 */
public class NumberType extends AbstractSimpleType<JsonNumber> implements SimpleType {

	/**
	 * Constructs this type.
	 */
	public NumberType() {
	}

	@Override
	public TypeId getTypeId() {
		return TypeId.NUMBER;
	}
	
	/**
	 * Specifies the inclusive lower bound of the value space for this type.
	 * @param value the inclusive lower bound value.
	 * @return this type.
	 */
	public NumberType minInclusive(long value) {
		return minInclusive(BigDecimal.valueOf(value));
	}

	/**
	 * Specifies the exclusive lower bound of the value space for this type.
	 * @param value the exclusive lower bound value.
	 * @return this type.
	 */
	public NumberType minExclusive(long value) {
		return minExclusive(BigDecimal.valueOf(value));
	}

	/**
	 * Specifies the inclusive lower bound of the value space for this type.
	 * @param value the inclusive lower bound value.
	 * @return this type.
	 */
	public NumberType minInclusive(BigDecimal value) {
		addFacet(new MinNumberFacet(value, false));
		return this;
	}

	/**
	 * Specifies the exclusive lower bound of the value space for this type.
	 * @param value the exclusive lower bound value.
	 * @return this type.
	 */
	public NumberType minExclusive(BigDecimal value) {
		addFacet(new MinNumberFacet(value, true));
		return this;
	}

	/**
	 * Specifies the inclusive upper bound of the value space for this type.
	 * @param value the inclusive upper bound value.
	 * @return this type.
	 */
	public NumberType maxInclusive(long value) {
		return maxInclusive(BigDecimal.valueOf(value));
	}

	/**
	 * Specifies the exclusive upper bound of the value space for this type.
	 * @param value the exclusive upper bound value.
	 * @return this type.
	 */
	public NumberType maxExclusive(long value) {
		return maxExclusive(BigDecimal.valueOf(value));
	}
	
	/**
	 * Specifies the inclusive upper bound of the value space for this type.
	 * @param value the inclusive upper bound value.
	 * @return this type.
	 */
	public NumberType maxInclusive(BigDecimal value) {
		addFacet(new MaxNumberFacet(value, false));
		return this;
	}
	
	/**
	 * Specifies the exclusive upper bound of the value space for this type.
	 * @param value the exclusive upper bound value.
	 * @return this type.
	 */
	public NumberType maxExclusive(BigDecimal value) {
		addFacet(new MaxNumberFacet(value, true));
		return this;
	}

	/**
	 * Specifies values allowed for this type.
	 * @param values the values allowed.
	 * @return this type.
	 */
	public NumberType values(long... values) {
		Set<JsonNumber> valueSet = new HashSet<>();
		for (long value: values) {
			valueSet.add(JsonValues.createNumber(value));
		}
		addFacet(ValueSetFacet.of(valueSet));
		return this;
	}
	
	/**
	 * Specifies values allowed for this type.
	 * @param values the values allowed.
	 * @return this type.
	 * @exception SchemaException if one of values specified is null.
	 */
	public NumberType values(BigDecimal... values) {
		Set<JsonNumber> valueSet = new HashSet<>();
		int index = 0;
		for (BigDecimal value: values) {
			if (value == null) {
				throw new SchemaException(Messages.ONE_OF_VALUES_IS_NULL(index));
			}
			valueSet.add(JsonValues.createNumber(value));
			index++;
		}
		addFacet(ValueSetFacet.of(valueSet));
		return this;
	}
}
