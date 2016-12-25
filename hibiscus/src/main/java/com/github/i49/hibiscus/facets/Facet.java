package com.github.i49.hibiscus.facets;

import java.util.List;

import javax.json.JsonValue;

import com.github.i49.hibiscus.problems.Problem;

/**
 * Common interface to be implemented by all facet classes.
 *
 * <p>
 * For readers unfamiliar with <i>facets</i>, please see <a href="package-summary.html#introducing-facets">Introducing facets</a> first.
 * </p>
 * 
 * @param <V> the type of {@link JsonValue} to which this facet will be applied.
 * 
 * @see <a href="package-summary.html#introducing-facets">Introducing facets</a>
 */
public interface Facet<V extends JsonValue> {

	/**
	 * Applies this facet to a value in JSON document and 
	 * when the value is out of valid value space, it reports one or more corresponding problems. 
	 * 
	 * @param value {@link JsonValue} in JSON document to be validated.
	 * @param problems the list of problems to which new problems found by this facet will be added.
	 */
	void apply(V value, List<Problem> problems);
}
