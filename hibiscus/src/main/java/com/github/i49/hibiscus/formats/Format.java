package com.github.i49.hibiscus.formats;

import javax.json.JsonValue;

/**
 * Base type of all formats.
 *
 * @param <V> The type of the JSON values.
 */
public interface Format<V extends JsonValue> {

	/**
	 * Tests whether the given value matches this format or not. 
	 * @param value JSON value to test. 
	 * @return {@code true} if the input argument matches the format, otherwise {@code false}.
	 */
	boolean test(V value);
}
