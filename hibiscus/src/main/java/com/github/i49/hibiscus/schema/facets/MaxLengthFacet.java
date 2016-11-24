package com.github.i49.hibiscus.schema.facets;

import java.util.List;
import java.util.function.ToIntFunction;

import javax.json.JsonValue;

import com.github.i49.hibiscus.problems.Problem;

/**
 * Facet constraining a value space to values that are less than or equal to the specific length. 
 */
public class MaxLengthFacet<T extends JsonValue> implements Facet<T> {

	private final int maxLength;
	private final ToIntFunction<T> lengthMapper;
	private final LengthProblemFactory<T> problemFactory;

	/**
	 * Constructs this facet.
	 * @param maxLength the maximum length allowed for the type.
	 * @param lengthMapper the mapper object to retrieve the length of the value.
	 * @param problemFactory the factory object to create a new problem.
	 */
	public MaxLengthFacet(int maxLength, ToIntFunction<T> lengthMapper, LengthProblemFactory<T> problemFactory) {
		this.maxLength = maxLength;
		this.lengthMapper = lengthMapper;
		this.problemFactory = problemFactory;
	}
	
	@Override
	public void apply(T value, List<Problem> problems) {
		int length = lengthMapper.applyAsInt(value);
		if (length > maxLength) {
			problems.add(problemFactory.newProblem(value, length, maxLength));
		}
	}
}