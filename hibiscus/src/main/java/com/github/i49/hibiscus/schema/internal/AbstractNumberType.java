package com.github.i49.hibiscus.schema.internal;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.json.JsonNumber;

import com.github.i49.hibiscus.facets.EnumerationFacet;
import com.github.i49.hibiscus.facets.MaxNumberFacet;
import com.github.i49.hibiscus.facets.MinNumberFacet;
import com.github.i49.hibiscus.schema.IntegerType;
import com.github.i49.hibiscus.schema.NumberType;
import com.github.i49.hibiscus.schema.SchemaException;

/**
 * A skeletal class to help implement {@link NumberType} and {@link IntegerType}.
 *
 * @param <T> the interface to be implemented, which is {@link NumberType} or {@link IntegerType}. 
 */
abstract class AbstractNumberType<T extends NumberType> extends AbstractJsonType<JsonNumber, T> {

	public T minInclusive(long value) {
		return minInclusive(BigDecimal.valueOf(value));
	}

	public T minExclusive(long value) {
		return minExclusive(BigDecimal.valueOf(value));
	}

	public T minInclusive(BigDecimal value) {
		return facet(new MinNumberFacet(value, false));
	}

	public T minExclusive(BigDecimal value) {
		return facet(new MinNumberFacet(value, true));
	}

	public T maxInclusive(long value) {
		return maxInclusive(BigDecimal.valueOf(value));
	}

	public T maxExclusive(long value) {
		return maxExclusive(BigDecimal.valueOf(value));
	}
	
	public T maxInclusive(BigDecimal value) {
		return facet(new MaxNumberFacet(value, false));
	}
	
	public T maxExclusive(BigDecimal value) {
		return facet(new MaxNumberFacet(value, true));
	}

	public T enumeration() {
		return addEnumerationFacet(Collections.emptySet());
	}

	public T enumeration(long... values) {
		Set<Object> enumerators = new HashSet<>();
		for (long value: values) {
			enumerators.add(BigDecimal.valueOf(value));
		}
		return addEnumerationFacet(enumerators);
	}
	
	public T enumeration(BigDecimal... values) {
		Set<Object> enumerators = new HashSet<>();
		int i = 0;
		for (BigDecimal value: values) {
			if (value == null) {
				throw new SchemaException(Messages.ONE_OF_VALUES_IS_NULL(i));
			}
			enumerators.add(value);
			i++;
		}
		return addEnumerationFacet(enumerators);
	}
	
	private T addEnumerationFacet(Set<Object> enumerators) {
		return facet(EnumerationFacet.of(enumerators, JsonNumber::bigDecimalValue));
	}
}
