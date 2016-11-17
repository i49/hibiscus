package com.github.i49.hibiscus.schema;

import java.util.List;

import javax.json.JsonValue;

import com.github.i49.hibiscus.common.TypeId;
import com.github.i49.hibiscus.problems.Problem;

/**
 * This interface represents all types in JSON schema.
 */
public interface JsonType {

	/**
	 * Returns type identifier of this type.
	 * @return type identifier.
	 */
	TypeId getTypeId();
	
	/**
	 * Validates instance of this type.
	 * @param value instance value.
	 * @param problems list to which detected problems to be added.
	 */
	void validateInstance(JsonValue value, List<Problem> problems);
 }
