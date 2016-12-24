package com.github.i49.hibiscus.facets;

import java.util.List;

import javax.json.JsonValue;

import com.github.i49.hibiscus.problems.Problem;

/**
 * Common interface to be implemented by all facet classes.
 *
 * <p>A <strong>facet</strong> is a single defining aspect of a value space of types.
 * Each of built-in types in JSON schema just provides default value space of its own.
 * By using facet, you can restrict the value spaces of these types as necessary.</p>
 * <p>For example, you can express restrictions listed below.</p>
 * <ul>
 * <li>valid range of numeric values, such as lower or upper threshold of the range.</li>
 * <li>the length of values, such as length of string.</li>
 * <li>list of values to be considered as valid.</li>
 * <li>predefined format of values.</li>
 * <li>regular expression that valid values must match.</li>
 * </ul>
 * 
 * <p>A facet can be applied to a value in JSON document 
 * and when it detects the value is out of valid value space, 
 * it will reports one or more corresponding problems.
 * All problems to be reported by the facets are defined in {@link com.github.i49.hibiscus.problems} package.</p>
 * 
 * @param <V> the type of JSON values to which this facet will be applied.
 * 
 * @see com.github.i49.hibiscus.facets
 */
public interface Facet<V extends JsonValue> {

	/**
	 * Applies this facet to the value in JSON document and 
	 * when the value is out of valid value space, it reports one or more corresponding problems. 
	 * 
	 * @param value the value to be validated.
	 * @param problems the list of problems to which new problems found by this facet will be added.
	 */
	void apply(V value, List<Problem> problems);
}
