package com.github.i49.hibiscus.schema.facets;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import javax.json.JsonNumber;
import javax.json.JsonValue;

import com.github.i49.hibiscus.json.JsonValues;
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
	 * Creates a empty facet.
	 * @return empty facet.
	 * @param <T> type of {@code JsonValue}.
	 */
	public static <T extends JsonValue> EnumerationFacet<T> empty() {
		return new EnumerationFacet<T>(Collections.emptySet());
	}

	/**
	 * Creates a new facet.
	 * @param mapper the mapper that will converts each value to {@code JsonValue}.
	 * @param valueSet the value space.
	 * @return created facet.
	 * @param <T> type of {@code JsonValue}.
	 * @param <V> type of each value of valueSet.
	 */
	public static <T extends JsonValue, V> EnumerationFacet<T> of(Function<V, T> mapper, V[] values) {
		Set<T> valueSet = new HashSet<>();
		for (V value: values) {
			T mapped = mapper.apply(value);
			if (mapped != null) {
				valueSet.add(mapped);
			}
		}
		return new EnumerationFacet<T>(valueSet);
	}
	
	/**
	 * Creates a new facet.
	 * @param valueSet the value space.
	 * @return created facet.
	 */
	public static EnumerationFacet<JsonValue> of(boolean[] values) {
		Set<JsonValue> valueSet = new HashSet<>();
		for (boolean value: values) {
			valueSet.add(JsonValues.createBoolean(value));
		}
		return new EnumerationFacet<JsonValue>(valueSet);
	}

	/**
	 * Creates a new facet.
	 * @param valueSet the value space.
	 * @return created facet.
	 */
	public static EnumerationFacet<JsonNumber> of(long[] values) {
		Set<JsonNumber> valueSet = new HashSet<>();
		for (long value: values) {
			valueSet.add(JsonValues.createNumber(value));
		}
		return new EnumerationFacet<JsonNumber>(valueSet);
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
