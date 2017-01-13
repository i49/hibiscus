package com.github.i49.hibiscus.facets;

import javax.json.JsonValue;

import com.github.i49.hibiscus.problems.TypedProblem;

/**
 * A helper interface which will create problems that report the values have invalid lengths,
 * on behalf of {@link LengthFacet}, {@link MinLengthFacet} and {@link MaxLengthFacet} classes.
 * 
 * @param <V> the type of JSON values which may have invalid lengths.
 */
@FunctionalInterface
public interface LengthProblemFactory<V extends JsonValue> {

	/**
	 * Creates a new problem when a value has an invalid length.
	 * 
	 * @param actualLength actual length of the value in JSON document.
	 * @param expectedLength the length expected by facets.
	 * @return created problem.
	 */
	TypedProblem<V> newProblem(int actualLength, int expectedLength);
}
