package com.github.i49.hibiscus.schema;

import java.util.List;

import javax.json.JsonValue;

import com.github.i49.hibiscus.problems.Problem;

/**
 * Facet is a single defining aspect of a value space. 
 *
 * @param <T> type of {@code JsonValue} in JSON instance.
 */
public interface Facet<T extends JsonValue> {

	/**
	 * Applies this facet to value in JSON instance.
	 * @param value the value in JSON instance.
	 * @param problems the list to which found problems to be added.
	 */
	void apply(T value, List<Problem> problems);
}
