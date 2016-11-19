package com.github.i49.hibiscus.schema.facets;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.json.JsonValue;

import com.github.i49.hibiscus.problems.Problem;
import com.github.i49.hibiscus.problems.UnknownValueProblem;

/**
 * Facet constraining a value space to a specified set of values. 
 *
 * @param <T> type of JSON value.
 */
public class ValueSetFacet<T extends JsonValue> implements Facet<T> {

	private final Set<T> valueSet;

	/**
	 * Creates new facet.
	 * @param valueSet the value space.
	 * @return created facet.
	 * @param <T> type of {@code JsonValue}.
	 */
	public static <T extends JsonValue> ValueSetFacet<T> of(Set<T> valueSet) {
		return new ValueSetFacet<T>(valueSet);
	}
	
	/**
	 * Constructs this facet.
	 * @param valueSet the value space.
	 */
	private ValueSetFacet(Set<T> valueSet) {
		this.valueSet = valueSet;
	}

	@Override
	public void apply(T value, List<Problem> problems) {
		if (!valueSet.contains(value)) {
			problems.add(new UnknownValueProblem(value, new HashSet<JsonValue>(valueSet)));
		}
	}
}
