package com.github.i49.hibiscus.schema.facets;

import java.util.List;
import java.util.function.ToIntFunction;

import javax.json.JsonValue;

import com.github.i49.hibiscus.problems.Problem;

/**
 * Facet constraining a value space to values of the specific length. 
 */
public class LengthFacet<T extends JsonValue> implements Facet<T> {

	private final int expectedLength;
	private final ToIntFunction<T> lengthMapper;
	private final LengthProblemFactory<T> problemFactory;
	
	/**
	 * Constructs this facet.
	 * @param expectedLength the length allowed for the type.
	 * @param lengthMapper the mapper object to retrieve the length of the value.
	 * @param problemFactory the factory object to create a new problem.
	 */
	public LengthFacet(int expectedLength, ToIntFunction<T> lengthMapper, LengthProblemFactory<T> problemFactory) {
		this.expectedLength = expectedLength;
		this.lengthMapper = lengthMapper;
		this.problemFactory = problemFactory;
	}

	@Override
	public void apply(T value, List<Problem> problems) {
		int length = lengthMapper.applyAsInt(value);
		if (length != expectedLength) {
			problems.add(problemFactory.newProblem(value, length, expectedLength));
		}
	}
}
