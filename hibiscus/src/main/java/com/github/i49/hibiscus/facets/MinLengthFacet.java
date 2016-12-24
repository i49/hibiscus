package com.github.i49.hibiscus.facets;

import java.util.List;
import java.util.function.ToIntFunction;

import javax.json.JsonValue;

import com.github.i49.hibiscus.problems.Problem;

/**
 * <strong>minLength</strong> facet to restrict the value space to the values
 * that have lengths more than or equal to a specific length.
 * <p>
 * This facet is applicable to {@code string()} or {@code array()} type.
 * If the value of the type has the length shorter than the expected length,
 * a problem will be reported by this facet. 
 * The type of the problem to be reported is determined by {@link LengthProblemFactory}
 * which is passed in to the constructor. 
 * </p>
 *
 * @param <V> the type of {@link JsonValue} to which this facet will be applied.
 */
public class MinLengthFacet<V extends JsonValue> implements Facet<V> {

	private final int minLength;
	private final ToIntFunction<V> lengthMapper;
	private final LengthProblemFactory<V> problemFactory;
	
	/**
	 * Constructs this facet.
	 * @param minLength the minimum length allowed for the type.
	 * @param lengthMapper the mapper used to retrieve the length of the value.
	 * @param problemFactory the factory used to create a new problem when given value has an invalid length.
	 */
	public MinLengthFacet(int minLength, ToIntFunction<V> lengthMapper, LengthProblemFactory<V> problemFactory) {
		this.minLength = minLength;
		this.lengthMapper = lengthMapper;
		this.problemFactory = problemFactory;
	}
	
	@Override
	public void apply(V value, List<Problem> problems) {
		int length = lengthMapper.applyAsInt(value);
		if (length < minLength) {
			problems.add(problemFactory.newProblem(value, length, minLength));
		}
	}
}