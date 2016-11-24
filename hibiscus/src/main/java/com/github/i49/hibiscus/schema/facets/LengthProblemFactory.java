package com.github.i49.hibiscus.schema.facets;

import javax.json.JsonValue;

import com.github.i49.hibiscus.problems.Problem;

/**
 * Factory that creates problems relating to length of values.
 *
 * @param <T> type of JSON value.
 */
@FunctionalInterface
public interface LengthProblemFactory<T extends JsonValue> {

	Problem newProblem(T value, int length, int expectedLength);
}
