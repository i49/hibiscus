package com.github.i49.hibiscus.validation;

import java.math.BigDecimal;
import java.util.concurrent.Future;

import javax.json.JsonNumber;
import javax.json.JsonString;
import javax.json.JsonValue;

/**
 * A builder class to build various kinds of containers of {@link JsonValue}s. 
 */
interface JsonBuilder {

	JsonNumber add(int value);

	JsonNumber add(long value);

	JsonNumber add(BigDecimal value);
	
	JsonString add(String value);
	
	JsonValue add(JsonValue value);
	
	/**
	 * Returns the future of the given value which MUST be the last value provided by this builder.
	 * @param value the value for which the future object will be returned.
	 * @return the future of the value.
	 */
	Future<JsonValue> getFutureOf(JsonValue value);
}
