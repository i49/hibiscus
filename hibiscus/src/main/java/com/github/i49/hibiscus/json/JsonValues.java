package com.github.i49.hibiscus.json;

import java.math.BigDecimal;

import javax.json.JsonNumber;
import javax.json.JsonString;

public abstract class JsonValues {

	public static JsonString createString(String value) {
		return JsonStringImpl.valueOf(value);
	}
	
	public static JsonNumber createNumber(int value) {
		return JsonIntNumberImpl.valueOf(value);
	}
	
	public static JsonNumber createNumber(long value) {
		return JsonLongNumberImpl.valueOf(value);
	}
	
	public static JsonNumber createNumber(BigDecimal value) {
		return JsonDecimalNumberImpl.valueOf(value);
	}
	
	private JsonValues() {
	}
}
