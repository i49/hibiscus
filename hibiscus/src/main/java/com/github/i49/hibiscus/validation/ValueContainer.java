package com.github.i49.hibiscus.validation;

import java.math.BigDecimal;

import javax.json.JsonValue;

/**
 * A object that contains {@link JsonValue}s. 
 */
interface ValueContainer {

	Transient<JsonValue> add(int value);

	Transient<JsonValue> add(long value);

	Transient<JsonValue> add(BigDecimal value);
	
	Transient<JsonValue> add(String value);
	
	Transient<JsonValue> add(JsonValue value);
}
