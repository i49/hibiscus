package com.github.i49.hibiscus.schema;

import java.util.List;

import javax.json.JsonValue;

import com.github.i49.hibiscus.common.TypeId;
import com.github.i49.hibiscus.problems.Problem;

/**
 * The interface representing all types in JSON schema.
 */
public interface JsonType {

	/**
	 * Returns type identifier of this type.
	 * @return type identifier.
	 */
	TypeId getTypeId();
	
	/**
	 * Validates value of this type in JSON instance.
	 * @param value the value in JSON instance. Cannot be {@code null}.
	 * @param problems the list to which detected problems to be added. Cannot be {@code null}.
	 */
	void validateInstance(JsonValue value, List<Problem> problems);
 }
