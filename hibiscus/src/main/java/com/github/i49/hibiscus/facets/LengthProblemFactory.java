package com.github.i49.hibiscus.facets;

import javax.json.JsonValue;

import com.github.i49.hibiscus.problems.Problem;

/**
 * A supportive interface which will create problems that report the values have invalid lengths,
 * on behalf of {@link LengthFacet}, {@link MinLengthFacet} and {@link MaxLengthFacet} classes.
 * 
 * @param <V> the type of JSON values which may have invalid lengths.
 */
@FunctionalInterface
public interface LengthProblemFactory<V extends JsonValue> {

	/**
	 * Creates a new problem when a value has an invalid length.
	 * 
	 * @param value the value which is the cause of the problem.
	 * @param actualLength actual length of the value in JSON document.
	 * @param expectedLength the length expected by facets.
	 * @return created problem.
	 */
	Problem newProblem(V value, int actualLength, int expectedLength);
}
