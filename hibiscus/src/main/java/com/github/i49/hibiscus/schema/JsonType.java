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
	 * Returns the type identifier of this type.
	 * @return type identifier.
	 */
	TypeId getTypeId();
	
	/**
	 * Validates a value of this type in JSON document and reports problems.
	 * This method is for internal use only and
	 * {@code JsonValidator} implementations are allowed to call this method directly. 
	 * @param value the value in JSON document. Cannot be {@code null}.
	 * @param problems the list to which detected problems to be added. Cannot be {@code null}.
	 */
	default void validateInstance(JsonValue value, List<Problem> problems) {
	}
 }
