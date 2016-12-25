package com.github.i49.hibiscus.formats;

import java.util.Locale;

import javax.json.JsonValue;

/**
 * Common interface to be implemented by all format classes.
 *
 * <p>
 * For readers unfamiliar with <i>formats</i>, please see <a href="package-summary.html#introducing-formats">Introducing formats</a> first.
 * </p>
 *
 * @param <V> the type of {@link JsonValue} to be validated against this format.
 * 
 * @see <a href="package-summary.html#introducing-formats">Introducing formats</a>
 */
public interface Format<V extends JsonValue> {

	/**
	 * Returns the format name which must be unique in all formats.
	 * @return the name of this format.
	 * @see #getLocalizedName(Locale)
	 */
	String getName();
	
	/**
	 * Returns the format name which is appropriate to be displayed in specified locale.
	 * 
	 * @param locale the locale for which the name will be localized.
	 * @return the localized name of this format.
	 * @see #getName()
	 */
	default String getLocalizedName(Locale locale) {
		return getName();
	}
	
	/**
	 * Tests whether the given value matches this format
	 * and returns {@code true} if the value matches this format or otherwise returns {@code false}.
	 * 
	 * @param jsonValue the JSON value to be tested whether it matches against this format or not. 
	 * @return {@code true} if the input argument matches the format, otherwise {@code false}.
	 */
	boolean matches(V jsonValue);
}
