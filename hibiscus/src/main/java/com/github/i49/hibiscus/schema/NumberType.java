package com.github.i49.hibiscus.schema;

import java.math.BigDecimal;
import java.util.function.Predicate;

import javax.json.JsonNumber;

import com.github.i49.hibiscus.common.TypeId;
import com.github.i49.hibiscus.json.JsonValues;
import com.github.i49.hibiscus.schema.facets.MaxNumberFacet;
import com.github.i49.hibiscus.schema.facets.MinNumberFacet;
import com.github.i49.hibiscus.schema.facets.EnumerationFacet;

import static com.github.i49.hibiscus.schema.Enumerations.*;

/**
 * JSON type for numeric value, including integer.
 */
public class NumberType extends AbstractRestrictableType<JsonNumber, NumberType> implements AtomicType {

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
	 * @return this type.
	 */
	public NumberType enumeration() {
		addFacet(EnumerationFacet.ofEmpty());
		return this;
	}

	/**
	 * Specifies values allowed for this type.
	 * @param values the values allowed.
	 * @return this type.
	 */
	public NumberType enumeration(long... values) {
		addFacet(EnumerationFacet.of(valueSet(values)));
		return this;
	}
	
	/**
	 * Specifies values allowed for this type.
	 * @param values the values allowed.
	 * @return this type.
	 * @exception SchemaException if one of values specified is null.
	 */
	public NumberType enumeration(BigDecimal... values) {
		addFacet(EnumerationFacet.of(valueSet(JsonValues::createNumber, values)));
		return this;
	}

	@Override
	public NumberType assertion(Predicate<JsonNumber> predicate, String message) {
		return super.assertion(predicate, message);
	}
}
