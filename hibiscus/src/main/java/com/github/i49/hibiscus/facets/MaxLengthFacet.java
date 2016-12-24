package com.github.i49.hibiscus.facets;

import java.util.List;
import java.util.function.ToIntFunction;

import javax.json.JsonValue;

import com.github.i49.hibiscus.problems.Problem;

/**
 * <strong>maxLength</strong> facet to restrict the value space to the values 
 * that have lengths less than or equal to a specific length.
 * <p>
 * This facet is applicable to {@code string()} or {@code array()} type.
 * If the value of the type has the length longer than the expected length,
 * a problem will be reported by this facet. 
 * The type of the problem to be reported is determined by {@link LengthProblemFactory}
 * which is passed in to the constructor. 
 * </p>
 *
 * @param <V> the type of {@link JsonValue} to which this facet will be applied.
 */
public class MaxLengthFacet<V extends JsonValue> implements Facet<V> {

	private final int maxLength;
	private final ToIntFunction<V> lengthMapper;
	private final LengthProblemFactory<V> problemFactory;

	/**
	 * Constructs this facet.
	 * @param maxLength the maximum length allowed for the type.
	 * @param lengthMapper the mapper used to retrieve the length of the value.
	 * @param problemFactory the factory used to create a new problem when given value has an invalid length.
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