package com.github.i49.hibiscus.schema.facets;

import javax.json.JsonValue;

import com.github.i49.hibiscus.problems.Problem;

/**
 * Factory that creates problems that relates to length of values.
 *
 * @param <T> type of JSON value.
 */
@FunctionalInterface
public interface LengthProblemFactory<T extends JsonValue> {

	/**
	 * Creates a new problem.
	 * @param value the value that is the cause of the problem.
	 * @param actualLength the length of the value in JSON document.
	 * @param expectedLength the length expected.
	 * @return new problem.
	 */
	Problem newProblem(T value, int actualLength, int expectedLength);
}
