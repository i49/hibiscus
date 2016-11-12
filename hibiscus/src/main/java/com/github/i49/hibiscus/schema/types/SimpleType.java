package com.github.i49.hibiscus.schema.types;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.json.JsonValue;

import com.github.i49.hibiscus.schema.problems.Problem;
import com.github.i49.hibiscus.schema.problems.UnknownValueProblem;

/**
 * Base class of boolean, integer, number, null and string type.
 */
public abstract class SimpleType extends JsonType {

	private Set<JsonValue> valueSet;
	
	@Override
	public void validateInstance(JsonValue value, List<Problem> problems) {
		if (valueSet != null && !valueSet.contains(value)) {
			problems.add(new UnknownValueProblem(value, valueSet));
		}
	}

	protected void setValueSet(Set<JsonValue> valueSet) {
		this.valueSet = Collections.unmodifiableSet(valueSet);
	}
}
