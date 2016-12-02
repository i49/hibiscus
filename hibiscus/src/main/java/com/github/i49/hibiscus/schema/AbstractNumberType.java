package com.github.i49.hibiscus.schema;

import static com.github.i49.hibiscus.schema.Enumerations.valueSet;

import java.math.BigDecimal;

import javax.json.JsonNumber;

import com.github.i49.hibiscus.facets.EnumerationFacet;
import com.github.i49.hibiscus.facets.MaxNumberFacet;
import com.github.i49.hibiscus.facets.MinNumberFacet;
import com.github.i49.hibiscus.json.JsonValues;

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
		addFacet(EnumerationFacet.ofEmpty());
		@SuppressWarnings("unchecked")
		T self = (T)this;
		return self;
	}

	public T enumeration(long... values) {
		addFacet(EnumerationFacet.of(valueSet(values)));
		@SuppressWarnings("unchecked")
		T self = (T)this;
		return self;
	}
	
	public T enumeration(BigDecimal... values) {
		addFacet(EnumerationFacet.of(valueSet(JsonValues::createNumber, values)));
		@SuppressWarnings("unchecked")
		T self = (T)this;
		return self;
	}
}
