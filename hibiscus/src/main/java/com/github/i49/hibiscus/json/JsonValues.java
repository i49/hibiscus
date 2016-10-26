package com.github.i49.hibiscus.json;

import java.math.BigDecimal;

import javax.json.JsonNumber;
import javax.json.JsonString;

public abstract class JsonValues {

	public JsonString createString(String value) {
		return new JsonStringImpl(value);
	}
	
	public JsonNumber createNumber(int value) {
		if (value == 0) {
			return JsonIntNumberImpl.ZERO;
		} else if (value == 1) {
			return JsonIntNumberImpl.ONE;
		} else {
			return new JsonIntNumberImpl(value);
		}
	}
	
	public JsonNumber createNumber(long value) {
		if (value == 0) {
			return JsonLongNumberImpl.ZERO;
		} else if (value == 1) {
			return JsonLongNumberImpl.ONE;
		} else {
			return new JsonLongNumberImpl(value);
		}
	}
	
	public JsonNumber createNumber(BigDecimal value) {
		return new JsonDecimalNumberImpl(value);
	}
	
	private JsonValues() {
	}
}
