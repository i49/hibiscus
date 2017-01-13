package com.github.i49.hibiscus.facets;

import java.util.List;
import java.util.function.ToIntFunction;

import javax.json.JsonValue;

import com.github.i49.hibiscus.problems.Problem;

/**
 * <strong>length</strong> facet to restrict the value space to the values that have the specific length.
 * <p>
 * This facet is applicable to {@code string()} or {@code array()} type.
 * If the value of the type does not has the expected length exactly,
 * a problem will be reported by this facet. 
 * The type of the problem to be reported is determined by {@link LengthProblemFactory}
 * which is passed in to the constructor. 
 * </p>
 * 
 * @param <V> the type of {@link JsonValue} to which this facet will be applied.
 */
public class LengthFacet<V extends JsonValue> implements Facet<V> {

	private final int expectedLength;
	private final ToIntFunction<V> lengthMapper;
	private final LengthProblemFactory<V> problemFactory;
	
	/**
	 * Constructs this facet.
	 * 
	 * @param expectedLength the length expected for the type.
	 * @param lengthMapper the mapper used to retrieve the length of the value.
	 * @param problemFactory the factory used to create a new problem when given value has an invalid length.
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
			problems.add(problemFactory.newProblem(length, expectedLength));
		}
	}
}
