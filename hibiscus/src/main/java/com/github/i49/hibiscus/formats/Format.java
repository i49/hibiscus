package com.github.i49.hibiscus.formats;

import java.util.Locale;

import javax.json.JsonValue;

/**
 * Common interface to be implemented by all format classes.
 *
 * <p>
 * A <i>format</i> is one of restrictions on the value spaces of the types.
 * Types in schema can select a format from predefined ones such as email address or IPv4 address.
 * This makes it unnecessary to write complex regular expressions matching email address or IPv4 address.
 * Most formats defined here are described by authoritative parties and considered as standard.
 * </p>
 * <p>
 * This package provides various kinds of format implementations, 
 * and all these classes implement {@link Format} interface.
 * </p>
 * <p>
 * These formats can be obtained by static methods of {@link Formats} class.
 * Each format can be applied to the built-in types with help of {@link com.github.i49.hibiscus.facets.FormatFacet FormatFacet},
 * which is one of <i>facets</i> provided by {@link com.github.i49.hibiscus.facets} package.
 * All formats currently available can be applied only to {@code string()} type.
 * </p>
 *
 * <p>All currently supported formats are shown in {@link com.github.i49.hibiscus.formats.Formats Formats} page.</p>
 *
 * @param <V> the type of {@link JsonValue} to be validated against this format.
 * 
 * @see Formats
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
