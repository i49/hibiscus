package com.github.i49.hibiscus.facets;

import java.util.List;
import java.util.Set;
import java.util.function.Function;

import javax.json.JsonValue;

import com.github.i49.hibiscus.problems.JsonValueProblem;
import com.github.i49.hibiscus.problems.NoSuchEnumeratorProblem;

/**
 * <strong>enumeration</strong> facet to restrict the value space of the type 
 * to a set of distinct values. 
 * <p>
 * This facet is applicable to {@code boolean()}, {@code string()}, {@code number()}, 
 * and {@code integer()} types.
 * If the tested value is not found in the set of valid values, {@link NoSuchEnumeratorProblem} 
 * will be reported by this facet. 
 * </p>
 *
 * @param <V> the type of {@link JsonValue} to which this facet will be applied.
 * @param <E> the type of the valid values specified as enumerators.
 */
public class EnumerationFacet<V extends JsonValue, E> implements Facet<V> {

	private final Set<Object> enumerators;
	private final Function<V, E> mapper;
	
	/**
	 * Creates a facet of this type.
	 * @param enumerators the set of valid values.
	 * @param mapper the mapper which will be used to map JSON values to corresponding values of Java type. 
	 * @return created facet.
	 * @param <V> the type of {@link JsonValue} to which this facet will be applied.
	 * @param <E> the type of the valid values specified as enumerators.
	 */
	public static <V extends JsonValue, E> EnumerationFacet<V, E> of(Set<Object> enumerators, Function<V, E> mapper) {
		return new EnumerationFacet<V, E>(enumerators, mapper);
	}

	/**
	 * Constructs this facet.
	 * @param enumerators the set of valid values.
	 * @param mapper the mapper which will be used to map JSON values to corresponding values of Java type. 
	 */
	private EnumerationFacet(Set<Object> enumerators, Function<V, E> mapper) {
		this.enumerators = enumerators;
		this.mapper = mapper;
	}

	@Override
	public void apply(V value, List<JsonValueProblem> problems) {
		if (!enumerators.contains(mapper.apply(value))) {
			problems.add(new NoSuchEnumeratorProblem(enumerators));
		}
	}
}
