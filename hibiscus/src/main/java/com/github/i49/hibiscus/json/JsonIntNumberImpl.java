package com.github.i49.hibiscus.json;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.json.JsonNumber;

public class JsonIntNumberImpl extends JsonNumberImpl {
	
	public static final JsonIntNumberImpl ZERO = new JsonIntNumberImpl(0);
	public static final JsonIntNumberImpl ONE = new JsonIntNumberImpl(1);
	
	private final int value;

	public JsonIntNumberImpl(int value) {
		this.value = value;
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
