package com.github.i49.hibiscus.schema;

import java.util.List;

import javax.json.JsonValue;

import com.github.i49.hibiscus.problems.Problem;

/**
 * Skeletal class to implement {@code JsonType}.
 * 
 * @param <T> the type of JSON value.
 */
abstract class AbstractJsonType<T extends JsonValue> implements JsonType {

	@Override
	public void validateInstance(JsonValue value, List<Problem> problems) {
		// Nothing to do.
	}

	/**
	 * Returns a string representation of this type.
	 * @return a string representation of the object. 
	 */
	@Override
	public String toString() {
		return getTypeId().toString();
	}
}
