package com.github.i49.hibiscus.facets;

import java.util.List;
import java.util.Set;
import java.util.function.Function;

import javax.json.JsonValue;

import com.github.i49.hibiscus.problems.NoSuchEnumeratorProblem;
import com.github.i49.hibiscus.problems.Problem;

/**
 * Facet to restrict a value space of type to a set of distinct values. 
 *
 * @param <V> the type of values in JSON document.
 * @param <E> the type of enumerators.
 */
public class EnumerationFacet<V extends JsonValue, E> implements Facet<V> {

	private final Set<Object> enumerators;
	private final Function<V, E> mapper;
	
	/**
	 * Creates a facet.
	 * @param enumerators the values in the enumeration.
	 * @param mapper the mapper which will map JSON values to values of Java type. 
	 * @return created facet.
	 * @param <V> the type of values in JSON document.
	 * @param <E> the type of enumerators.
	 */
	public static <V extends JsonValue, E> EnumerationFacet<V, E> of(Set<Object> enumerators, Function<V, E> mapper) {
		return new EnumerationFacet<V, E>(enumerators, mapper);
	}

	/**
	 * Constructs this facet.
	 * @param enumerators the enumerators.
	 */
	private EnumerationFacet(Set<Object> enumerators, Function<V, E> mapper) {
		this.enumerators = enumerators;
		this.mapper = mapper;
	}

	@Override
	public void apply(V value, List<Problem> problems) {
		if (!enumerators.contains(mapper.apply(value))) {
			problems.add(new NoSuchEnumeratorProblem(value, enumerators));
		}
	}
}
