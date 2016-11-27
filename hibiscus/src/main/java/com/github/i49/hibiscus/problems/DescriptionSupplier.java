package com.github.i49.hibiscus.problems;

import java.util.Locale;

import javax.json.JsonValue;

/**
 * Supplier of description of problem for specified locale.
 * 
 * @param <V> the type of the value in JSON document.
 */
@FunctionalInterface
public interface DescriptionSupplier<V extends JsonValue> {

	/**
	 * Supplies a description of a problem for specified locale.
	 * @param value the value in JSON document.
	 * @param locale the locale for which a description is supplied.
	 * @return a description of a problem.
	 */
	String getDescription(V value, Locale locale);
}
