package com.github.i49.hibiscus.formats;

import java.util.function.Predicate;

import javax.json.JsonString;

import com.github.i49.hibiscus.schema.SchemaComponents;
import com.github.i49.hibiscus.schema.JsonType;

/**
 * A type of {@link Format} which can be applied only to {@link JsonString} value.
 * 
 * <p>This class also implements {@code Predicate<String>} so that it can be specified as 
 * predicates of pattern properties and passed in to {@link SchemaComponents#pattern(Predicate, JsonType, JsonType...)}.
 * </p> 
 */
public abstract class StringFormat extends AbstractFormat<JsonString> implements Predicate<String> {
	
	/**
	 * Tests whether the given value matches this format
	 * and returns {@code true} if the value matches this format, {@code false} otherwise.
	 * 
	 * @param value the string to be tested whether it matches against this format or not. 
	 * @return {@code true} if the input argument matches the format, {@code false} otherwise.
	 */
	@Override
	public abstract boolean test(String value);
	
	/**
	 * Tests whether the given value matches this format
	 * and returns {@code true} if the value matches this format, {@code false} otherwise.
	 * This method just calls {@link #test(String)} and should not be overridden.
	 * 
	 * @param jsonValue the JSON value to be tested whether it matches against this format or not. 
	 * @return {@code true} if the input argument matches the format, {@code false} otherwise.
	 * 
	 * @see #test(String)
	 */
	@Override
	public final boolean matches(JsonString jsonValue) {
		return test(jsonValue.getString());
	}
}
