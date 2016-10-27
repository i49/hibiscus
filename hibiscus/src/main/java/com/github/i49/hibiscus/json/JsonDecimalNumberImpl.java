package com.github.i49.hibiscus.json;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.json.JsonNumber;

class JsonDecimalNumberImpl extends JsonNumberImpl {
	
	private final BigDecimal value;
	
	public static JsonNumber valueOf(BigDecimal value) {
		return new JsonDecimalNumberImpl(value);
	}

	private JsonDecimalNumberImpl(BigDecimal value) {
		this.value = value;
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
