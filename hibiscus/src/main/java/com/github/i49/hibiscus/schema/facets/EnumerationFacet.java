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
 * @param <T> type of {@code JsonValue}
 */
public class EnumerationFacet<T extends JsonValue> implements Facet<T> {

	private final Set<T> valueSet;
	
	/**
	 * Creates a facet.
	 * @param valueSet the values in the enumeration.
	 * @return created facet.
	 * @param <T> type of {@code JsonValue}.
	 */
	public static <T extends JsonValue> EnumerationFacet<T> of(Set<T> valueSet) {
		return new EnumerationFacet<T>(valueSet);
	}

	/**
	 * Creates a empty facet.
	 * @return empty facet.
	 * @param <T> type of {@code JsonValue}.
	 */
	public static <T extends JsonValue> EnumerationFacet<T> ofEmpty() {
		return new EnumerationFacet<T>(Collections.emptySet());
	}

	/**
	 * Constructs this facet.
	 * @param valueSet the value space.
	 */
	private EnumerationFacet(Set<T> valueSet) {
		this.valueSet = valueSet;
	}

	@Override
	public void apply(T value, List<Problem> problems) {
		if (!valueSet.contains(value)) {
			problems.add(new UnknownValueProblem(value, new HashSet<JsonValue>(valueSet)));
		}
	}
}
