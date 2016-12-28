package com.github.i49.hibiscus.problems;

import java.util.Locale;

import javax.json.JsonValue;

/**
 * A helper interface to supply a description of a problem for specified locale.
 * <p>This interface is used with {@link AssertionFailureProblem} class.</p>
 * 
 * @param <V> the type of {@link JsonValue} which caused the problem.
 */
@FunctionalInterface
public interface DescriptionSupplier<V extends JsonValue> {

	/**
	 * Supplies a description of a problem for specified locale.
	 * @param value the value in JSON document.
	 * @param locale the locale for which a description is desired.
	 * @return a description of a problem.
	 */
	String getDescription(V value, Locale locale);
}
