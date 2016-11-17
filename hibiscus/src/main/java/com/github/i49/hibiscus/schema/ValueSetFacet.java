package com.github.i49.hibiscus.schema;

import java.util.List;
import java.util.Set;

import javax.json.JsonValue;

import com.github.i49.hibiscus.problems.Problem;
import com.github.i49.hibiscus.problems.UnknownValueProblem;

/**
 * Facet constraining value space to a specified set of values. 
 *
 * @param <T> type of JSON value.
 */
class ValueSetFacet<T extends JsonValue> implements Facet<T> {

	private final Set<JsonValue> valueSet;
	
	public ValueSetFacet(Set<JsonValue> valueSet) {
		this.valueSet = valueSet;
	}

	@Override
	public void apply(T value, List<Problem> problems) {
		if (!valueSet.contains(value)) {
			problems.add(new UnknownValueProblem(value, valueSet));
		}
	}
}
