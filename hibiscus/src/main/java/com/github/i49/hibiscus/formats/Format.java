package com.github.i49.hibiscus.formats;

import java.util.Locale;

import javax.json.JsonValue;

/**
 * Base type of all formats.
 *
 * @param <V> The type of the JSON values.
 */
public interface Format<V extends JsonValue> {

	/**
	 * Returns the name of this format.
	 * @return the name of this format.
	 */
	String getName();
	
	/**
	 * Returns the name of this format for specific locale.
	 * @param locale the locale of the text representing the name.
	 * @return the name of this format.
	 */
	String getLocalizedString(Locale locale);
	
	/**
	 * Tests whether the given value matches this format or not. 
	 * @param value JSON value to test. 
	 * @return {@code true} if the input argument matches the format, otherwise {@code false}.
	 */
	boolean matches(V value);
}
