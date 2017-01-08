package com.github.i49.hibiscus.json;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.json.JsonNumber;

/**
 * A writable integer value which implements {@link JsonNumber}.
 */
public class WritableJsonIntNumber extends AbstractJsonNumber {
	
	private int value;
	
	/**
	 * Constructs this JSON value with default value.
	 */
	public WritableJsonIntNumber() {
		this.value = 0;
	}

	/**
	 * Constructs this JSON value.
	 * @param value the value to be assigned.
	 */
	public WritableJsonIntNumber(int value) {
		this.value = value;
	}
	
	/**
	 * Assigns a value to this JSON value.
	 * @param value the value to be assigned.
	 * @return this JSON value.
	 */
	public WritableJsonIntNumber assign(int value) {
		this.value = value;
		return this;
	}

	@Override
	public BigDecimal bigDecimalValue() {
		return BigDecimal.valueOf(value);
	}

	@Override
	public BigInteger bigIntegerValue() {
		return BigInteger.valueOf(value);
	}

	@Override
	public BigInteger bigIntegerValueExact() {
		return BigInteger.valueOf(value);
	}

	@Override
	public double doubleValue() {
		return value;
	}

	@Override
	public int intValue() {
		return value;
	}

	@Override
	public int intValueExact() {
		return value;
	}

	@Override
	public boolean isIntegral() {
		return true;
	}

	@Override
	public long longValue() {
		return value;
	}

	@Override
	public long longValueExact() {
		return value;
	}
	
	@Override
	public int hashCode() {
		return Integer.hashCode(value);
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
		if (other.isIntegral()) {
			return value == other.longValue();
		} else { 
			return false;
		}
	}
	
	@Override
	public String toString() {
		return Integer.toString(value);
	}
}
