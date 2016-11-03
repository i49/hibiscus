package com.github.i49.hibiscus.schema.types;

import java.util.List;

import javax.json.JsonValue;

import com.github.i49.hibiscus.schema.TypeId;
import com.github.i49.hibiscus.schema.problems.Problem;

/**
 * The superclass of all types in JSON schema.
 */
public abstract class JsonType {

	/**
	 * Returns type identifier of this type.
	 * @return type identifier.
	 */
	public abstract TypeId getTypeId();
	
	/**
	 * Validates instance of this type.
	 * @param value instance value.
	 * @param problems list to which detected problems to be added.
	 */
	public void validateInstance(JsonValue value, List<Problem> problems) {
		// By default we do nothing.
	}
	
	/**
	 * Returns a string representation of this type.
	 * @return a string representation of the object. 
	 */
	@Override
	public String toString() {
		return getTypeId().toString().toLowerCase();
	}
 }
