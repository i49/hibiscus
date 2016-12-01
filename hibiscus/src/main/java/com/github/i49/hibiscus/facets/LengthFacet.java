package com.github.i49.hibiscus.facets;

import java.util.List;
import java.util.function.ToIntFunction;

import javax.json.JsonValue;

import com.github.i49.hibiscus.problems.Problem;

/**
 * Facet constraining a value space to values of the specific length. 
 * @param <V> the type of values in JSON document. 
 */
public class LengthFacet<V extends JsonValue> implements Facet<V> {

	private final int expectedLength;
	private final ToIntFunction<V> lengthMapper;
	private final LengthProblemFactory<V> problemFactory;
	
	/**
	 * Constructs this facet.
	 * @param expectedLength the length allowed for the type.
	 * @param lengthMapper the mapper object to retrieve the length of the value.
	 * @param problemFactory the factory object to create a new problem.
	 */
	public LengthFacet(int expectedLength, ToIntFunction<V> lengthMapper, LengthProblemFactory<V> problemFactory) {
		this.expectedLength = expectedLength;
		this.lengthMapper = lengthMapper;
		this.problemFactory = problemFactory;
	}

	@Override
	public void apply(V value, List<Problem> problems) {
		int length = lengthMapper.applyAsInt(value);
		if (length != expectedLength) {
			problems.add(problemFactory.newProblem(value, length, expectedLength));
		}
	}
}
