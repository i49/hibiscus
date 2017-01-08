package com.github.i49.hibiscus.json;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.json.JsonNumber;

/**
 * A writable decimal value which implements {@link JsonNumber}.
 */
public class WritableJsonDecimalNumber extends AbstractJsonNumber {
	
	private BigDecimal value;
	
	/**
	 * Constructs this JSON value with default value.
	 */
	public WritableJsonDecimalNumber() {
		this.value = BigDecimal.ZERO;
	}

	/**
	 * Constructs this JSON value.
	 * @param value the value to be assigned.
	 * @exception IllegalArgumentException if the value is {@code null}.
	 */
	public WritableJsonDecimalNumber(BigDecimal value) {
		if (value == null) {
			throw new IllegalArgumentException("value is null.");
		}
		this.value = value;
	}

	/**
	 * Assigns a value to this JSON value.
	 * @param value the value to be assigned.
	 * @return this JSON value.
	 * @exception IllegalArgumentException if the value is {@code null}.
	 */
	public WritableJsonDecimalNumber assign(BigDecimal value) {
		if (value == null) {
			throw new IllegalArgumentException("value is null.");
		}
		this.value = value;
		return this;
	}
	
	@Override
	public BigDecimal bigDecimalValue() {
		return value;
	}

	@Override
	public BigInteger bigIntegerValue() {
		return value.toBigInteger();
	}

	@Override
	public BigInteger bigIntegerValueExact() {
		return value.toBigIntegerExact();
	}

	@Override
	public double doubleValue() {
		return value.doubleValue();
	}

	@Override
	public int intValue() {
		return value.intValue();
	}

	@Override
	public int intValueExact() {
		return value.intValueExact();
	}

	@Override
	public boolean isIntegral() {
		return value.scale() == 0;
	}

	@Override
	public long longValue() {
		return value.longValue();
	}

	@Override
	public long longValueExact() {
		return value.longValueExact();
	}
	
	@Override
	public int hashCode() {
		return value.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof JsonNumber)) {
			return false;
		}
		JsonNumber other = (JsonNumber)obj;
		return value.equals(other.bigDecimalValue());
	}

	@Override
	public String toString() {
		return value.toString();
	}
}
