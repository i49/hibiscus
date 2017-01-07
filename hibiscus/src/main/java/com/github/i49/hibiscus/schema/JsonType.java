package com.github.i49.hibiscus.schema;

import java.util.List;

import javax.json.JsonValue;

import com.github.i49.hibiscus.common.TypeId;
import com.github.i49.hibiscus.problems.JsonValueProblem;
import com.github.i49.hibiscus.validation.JsonValidator;

/**
 * A base type of all built-in types available in schema definitions.
 * These built-in types include array, boolean, integer, number, null, object and string.
 */
public interface JsonType {
	
	/**
	 * Returns the type identifier of this built-in type.
	 * @return the type identifier.
	 * @see TypeId
	 */
	TypeId getTypeId();
	
	/**
	 * Validates a value of this type in JSON document and reports problems if detected.
	 * This method is for internal use only and only
	 * {@link JsonValidator} implementations are allowed to call this method directly. 
	 * 
	 * @param value the value in JSON document. Cannot be {@code null}.
	 * @param problems the list to which detected problems to be added. Cannot be {@code null}.
	 */
	default void validateInstance(JsonValue value, List<JsonValueProblem> problems) {
	}
 }
