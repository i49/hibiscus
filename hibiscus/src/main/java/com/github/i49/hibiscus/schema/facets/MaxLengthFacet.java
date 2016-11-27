package com.github.i49.hibiscus.schema.facets;

import java.util.List;
import java.util.function.ToIntFunction;

import javax.json.JsonValue;

import com.github.i49.hibiscus.problems.Problem;

/**
 * Facet constraining a value space to values that are less than or equal to the specific length. 
 */
public class MaxLengthFacet<V extends JsonValue> implements Facet<V> {

	private final int maxLength;
	private final ToIntFunction<V> lengthMapper;
	private final LengthProblemFactory<V> problemFactory;

	/**
	 * Constructs this facet.
	 * @param maxLength the maximum length allowed for the type.
	 * @param lengthMapper the mapper object to retrieve the length of the value.
	 * @param problemFactory the factory object to create a new problem.
	 */
	public MaxLengthFacet(int maxLength, ToIntFunction<V> lengthMapper, LengthProblemFactory<V> problemFactory) {
		this.maxLength = maxLength;
		this.lengthMapper = lengthMapper;
		this.problemFactory = problemFactory;
	}
	
	@Override
	public void apply(V value, List<Problem> problems) {
		int length = lengthMapper.applyAsInt(value);
		if (length > maxLength) {
			problems.add(problemFactory.newProblem(value, length, maxLength));
		}
	}
}