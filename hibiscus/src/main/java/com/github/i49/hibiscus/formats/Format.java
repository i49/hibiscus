package com.github.i49.hibiscus.formats;

import java.util.Locale;

import javax.json.JsonValue;
import com.github.i49.hibiscus.problems.InvalidFormatProblem;

/**
 * The base type of all format classes.
 *
 * <p>All classes that implement this interface can be applied to JSON types
 * to declare the detailed format of the type.</p>
 *
 * @param <V> The type of the JSON values.
 */
public interface Format<V extends JsonValue> {

	/**
	 * Returns the name of this format. 
	 * Every format must have unique name.
	 * @return the name of this format.
	 */
	String getName();
	
	/**
	 * Returns the name of this format for specific locale.
	 * @param locale the locale of the text representing the name.
	 * @return the name of this format.
	 */
	default String getLocalizedName(Locale locale) {
		return getName();
	}
	
	/**
	 * Tests whether the given value matches this format or not.
	 *  
	 * <p>If this method returns {@code false}, {@link InvalidFormatProblem} will be reported.</p>
	 * 
	 * @param value JSON value to test. 
	 * @return {@code true} if the input argument matches the format, otherwise {@code false}.
	 */
	boolean matches(V value);
}
