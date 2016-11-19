package com.github.i49.hibiscus.schema;

import java.math.BigDecimal;

import javax.json.JsonNumber;

import com.github.i49.hibiscus.common.TypeId;
import com.github.i49.hibiscus.schema.facets.MaxNumberFacet;
import com.github.i49.hibiscus.schema.facets.MinNumberFacet;

/**
 * JSON type for numeric value including integer.
 */
public class NumberType extends AbstractSimpleType<JsonNumber> {

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
}
