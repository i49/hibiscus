package com.github.i49.hibiscus.json;

import java.math.BigDecimal;

import javax.json.JsonNumber;
import javax.json.JsonString;
import javax.json.JsonValue;

/**
 * Implementation class of {@link JsonValueFactory} interface.
 */
public class JsonValueFactoryImpl implements JsonValueFactory {

	/**
	 * Constructs this factory.
	 */
	public JsonValueFactoryImpl() {
	}

	@Override
	public JsonString createString(String value) {
		return JsonStringImpl.valueOf(value);
	}

	@Override
	public JsonNumber createNumber(int value) {
		return JsonIntNumberImpl.valueOf(value);
	}

	@Override
	public JsonNumber createNumber(long value) {
		return JsonLongNumberImpl.valueOf(value);
	}

	@Override
	public JsonNumber createNumber(BigDecimal value) {
		return JsonDecimalNumberImpl.valueOf(value);
	}

	@Override
	public JsonValue createBoolean(boolean value) {
		return value ? JsonValue.TRUE : JsonValue.FALSE;
	}
}
