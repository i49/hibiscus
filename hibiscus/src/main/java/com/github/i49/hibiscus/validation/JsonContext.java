package com.github.i49.hibiscus.validation;

import java.math.BigDecimal;
import java.util.concurrent.Future;

import javax.json.JsonNumber;
import javax.json.JsonString;
import javax.json.JsonValue;

import com.github.i49.hibiscus.common.JsonPointer;

/**
 * A context class which will be created per a {@link JsonValue} container
 * such as JSON documents, arrays or objects. 
 */
interface JsonContext {
	
	/**
	 * Returns the parent context of this context. 
	 * @return the parent context.
	 */
	JsonContext getParent();
	
	/**
	 * Assigns the parent context of this context.
	 * @param parent the parent context.
	 */
	void setParent(JsonContext parent);

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
	 * Returns the future of the current active value.
	 * @return the future of the current active value.
	 */
	Future<JsonValue> getCurrentValueFuture();

	/**
	 * Returns the future of the value which owns this context.
	 * @return this future of the value which owns this context.
	 */
	Future<JsonValue> getSelfFuture();

	/**
	 * Returns the JSON pointer of the current active value.
	 * @return the JSON pointer of the current active value.
	 */
	JsonPointer getCurrentPointer();

	/**
	 * Returns the JSON pointer of the value which owns this context.
	 * @return the JSON pointer of the value which owns this context.
	 */
	JsonPointer getSelfPointer();
	
	/**
	 * Builds the JSON pointer of the current active value.
	 * @param builder the builder of the JSON pointer.
	 */
	void buildCurrentPointer(JsonPointer.Builder builder);
}
