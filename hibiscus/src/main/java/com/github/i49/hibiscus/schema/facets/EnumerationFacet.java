package com.github.i49.hibiscus.schema.facets;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.json.JsonValue;

import com.github.i49.hibiscus.problems.Problem;
import com.github.i49.hibiscus.problems.UnknownValueProblem;

/**
 * Facet constraining a value space to a specific set of values. 
 *
 * @param <V> type of {@code JsonValue}.
 */
public class EnumerationFacet<V extends JsonValue> implements Facet<V> {

	private final Set<V> valueSet;
	
	/**
	 * Creates a facet.
	 * @param valueSet the values in the enumeration.
	 * @return created facet.
	 * @param <V> type of {@code JsonValue}.
	 */
	public static <V extends JsonValue> EnumerationFacet<V> of(Set<V> valueSet) {
		return new EnumerationFacet<V>(valueSet);
	}

	/**
	 * Creates a empty facet.
	 * @return empty facet.
	 * @param <T> type of {@code JsonValue}.
	 */
	public static <V extends JsonValue> EnumerationFacet<V> ofEmpty() {
		return new EnumerationFacet<V>(Collections.emptySet());
	}

	/**
	 * Constructs this facet.
	 * @param valueSet the value space.
	 */
	private EnumerationFacet(Set<V> valueSet) {
		this.valueSet = valueSet;
	}

	@Override
	public void apply(V value, List<Problem> problems) {
		if (!valueSet.contains(value)) {
			problems.add(new UnknownValueProblem(value, new HashSet<JsonValue>(valueSet)));
		}
	}
}
