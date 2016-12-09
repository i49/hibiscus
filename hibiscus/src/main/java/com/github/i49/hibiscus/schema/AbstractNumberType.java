package com.github.i49.hibiscus.schema;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.json.JsonNumber;

import com.github.i49.hibiscus.facets.EnumerationFacet;
import com.github.i49.hibiscus.facets.MaxNumberFacet;
import com.github.i49.hibiscus.facets.MinNumberFacet;

/**
 * Skeletal class to implement {@code NumberType}.
 *
 * @param <T> the interface to implement. {@code NumberType} or {@code IntegerType}. 
 */
abstract class AbstractNumberType<T extends NumberType> extends AbstractRestrictableType<JsonNumber, T> {

	public T minInclusive(long value) {
		return minInclusive(BigDecimal.valueOf(value));
	}

	public T minExclusive(long value) {
		return minExclusive(BigDecimal.valueOf(value));
	}

	public T minInclusive(BigDecimal value) {
		addFacet(new MinNumberFacet(value, false));
		@SuppressWarnings("unchecked")
		T self = (T)this;
		return self;
	}

	public T minExclusive(BigDecimal value) {
		addFacet(new MinNumberFacet(value, true));
		@SuppressWarnings("unchecked")
		T self = (T)this;
		return self;
	}

	public T maxInclusive(long value) {
		return maxInclusive(BigDecimal.valueOf(value));
	}

	public T maxExclusive(long value) {
		return maxExclusive(BigDecimal.valueOf(value));
	}
	
	public T maxInclusive(BigDecimal value) {
		addFacet(new MaxNumberFacet(value, false));
		@SuppressWarnings("unchecked")
		T self = (T)this;
		return self;
	}
	
	public T maxExclusive(BigDecimal value) {
		addFacet(new MaxNumberFacet(value, true));
		@SuppressWarnings("unchecked")
		T self = (T)this;
		return self;
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
		addFacet(EnumerationFacet.of(enumerators, JsonNumber::bigDecimalValue));
		@SuppressWarnings("unchecked")
		T self = (T)this;
		return self;
	}
}
