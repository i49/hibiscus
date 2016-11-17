package com.github.i49.hibiscus.schema;

import java.util.List;

import javax.json.JsonValue;

import com.github.i49.hibiscus.problems.Problem;

/**
 * Skeletal implementation of interface {@code JsonType}.
 */
abstract class AbstractJsonType implements JsonType {

	@Override
	public void validateInstance(JsonValue value, List<Problem> problems) {
		// By default we do nothing.
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
