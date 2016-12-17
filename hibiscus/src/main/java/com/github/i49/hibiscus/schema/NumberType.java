package com.github.i49.hibiscus.schema;

import java.math.BigDecimal;
import java.util.function.Predicate;

import javax.json.JsonNumber;

import com.github.i49.hibiscus.common.TypeId;
import com.github.i49.hibiscus.facets.Facet;
import com.github.i49.hibiscus.problems.DescriptionSupplier;

/**
 * JSON type for numeric value, including integer.
 */
public interface NumberType extends AtomicType {

	default TypeId getTypeId() {
		return TypeId.NUMBER;
	}
	
	/**
	 * Adds a facet to this type.
	 * @param facet the facet to be added. Cannot be {@code null}.
	 * @return this type.
	 * @exception SchemaException if facet specified is {@code null}.
	 */
	NumberType facet(Facet<JsonNumber> facet);
	
	/**
	 * Specifies the inclusive lower bound of the value space for this type.
	 * @param value the inclusive lower bound value.
	 * @return this type.
	 */
	NumberType minInclusive(long value);

	/**
	 * Specifies the exclusive lower bound of the value space for this type.
	 * @param value the exclusive lower bound value.
	 * @return this type.
	 */
	NumberType minExclusive(long value);

	/**
	 * Specifies the inclusive lower bound of the value space for this type.
	 * @param value the inclusive lower bound value.
	 * @return this type.
	 */
	NumberType minInclusive(BigDecimal value);

	/**
	 * Specifies the exclusive lower bound of the value space for this type.
	 * @param value the exclusive lower bound value.
	 * @return this type.
	 */
	NumberType minExclusive(BigDecimal value);

	/**
	 * Specifies the inclusive upper bound of the value space for this type.
	 * @param value the inclusive upper bound value.
	 * @return this type.
	 */
	NumberType maxInclusive(long value);

	/**
	 * Specifies the exclusive upper bound of the value space for this type.
	 * @param value the exclusive upper bound value.
	 * @return this type.
	 */
	NumberType maxExclusive(long value);
	
	/**
	 * Specifies the inclusive upper bound of the value space for this type.
	 * @param value the inclusive upper bound value.
	 * @return this type.
	 */
	NumberType maxInclusive(BigDecimal value);
	
	/**
	 * Specifies the exclusive upper bound of the value space for this type.
	 * @param value the exclusive upper bound value.
	 * @return this type.
	 */
	NumberType maxExclusive(BigDecimal value);
	
	/**
	 * Specifies values allowed for this type.
	 * @return this type.
	 */
	NumberType enumeration();

	/**
	 * Specifies values allowed for this type.
	 * @param values the values allowed.
	 * @return this type.
	 */
	NumberType enumeration(long... values);
	
	/**
	 * Specifies values allowed for this type.
	 * @param values the values allowed.
	 * @return this type.
	 * @exception SchemaException if one of values specified is null.
	 */
	NumberType enumeration(BigDecimal... values);

	/**
	 * Specifies assertion on this type.
	 * @param predicate the lambda expression that will return true if the assertion succeeded or false if failed.
	 * @param description the object to supply a description to be reported when the assertion failed.
	 * @return this type.
	 */
	NumberType assertion(Predicate<JsonNumber> predicate, DescriptionSupplier<JsonNumber> description);
}
