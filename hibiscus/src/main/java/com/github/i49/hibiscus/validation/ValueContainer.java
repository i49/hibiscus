package com.github.i49.hibiscus.validation;

import java.math.BigDecimal;

import javax.json.JsonValue;

/**
 * A object that contains {@link JsonValue}s. 
 */
interface ValueContainer {

	Transient add(int value);

	Transient add(long value);

	Transient add(BigDecimal value);
	
	Transient add(String value);
	
	Transient add(JsonValue value);
}
