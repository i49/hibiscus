package com.github.i49.hibiscus.validation;

import java.math.BigDecimal;
import java.util.concurrent.Future;

import javax.json.JsonNumber;
import javax.json.JsonString;
import javax.json.JsonValue;

/**
 * A context class which will be created per a {@link JsonValue} container
 * such as JSON documents, arrays or objects. 
 */
interface JsonContext {

	/**
	 * Adds an integer value to this context.
	 * @param value the value to be added.
	 * @return a temporary {@link JsonNumber} which has the given value.
	 */
	JsonNumber add(int value);

	/**
	 * Adds a long value to this context.
	 * @param value the value to be added.
	 * @return a temporary {@link JsonNumber} which has the given value.
	 */
	JsonNumber add(long value);

	/**
	 * Adds a {@link BigDecimal} value to this context.
	 * @param value the value to be added.
	 * @return a temporary {@link JsonNumber} which has the given value.
	 */
	JsonNumber add(BigDecimal value);
	
	/**
	 * Adds a {@code String} value to this context.
	 * @param value the value to be added.
	 * @return a temporary {@link JsonString} which has the given value.
	 */
	JsonString add(String value);
	
	/**
	 * Adds a {@link JsonValue} value to this context.
	 * @param value the value to be added.
	 * @return the added {@link JsonValue}.
	 */
	JsonValue add(JsonValue value);
	
	/**
	 * Returns the future of the currently processing value.
	 * @return the future of the currently processing value.
	 */
	Future<JsonValue> getFuture();
}
