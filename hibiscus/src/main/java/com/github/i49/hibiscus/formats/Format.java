package com.github.i49.hibiscus.formats;

import java.util.Locale;

import javax.json.JsonValue;

/**
 * Common interface to be implemented by all format classes.
 *
 * <p>
 * A <strong>format</strong> is one of restrictions on the value spaces of the types.
 * Types in schema can select a format from predefined ones such as email address or IPv4 address.
 * That makes it unnecessary to write a complex regular expression representing email address.
 * Most formats defined here are described by authoritative parties and considered as standard.
 * </p>
 * <p>
 * These formats can be obtained by static methods of {@link com.github.i49.hibiscus.formats.Formats Formats} class.
 * Formats can be applied to the built-in types with help of {@link com.github.i49.hibiscus.facets.FormatFacet FormatFacet},
 * which is one of <i>facets</i> provided by {@link com.github.i49.hibiscus.facets} package.
 * All formats currently available can be applied only to {@code string()} type.
 * </p>
 * <p>
 * All formats available are shown in <a href="package-summary.html#list-of-formats">the list of formats</a>.
 * </p>
 *
 * @param <V> the type of {@link JsonValue} to be validated against this format.
 * 
 * @see Formats
 * @see com.github.i49.hibiscus.formats
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
