package com.github.i49.schema.types;

import java.util.List;

import javax.json.JsonValue;
import javax.json.stream.JsonLocation;

import com.github.i49.schema.TypeId;
import com.github.i49.schema.problems.Problem;

/**
 * The superclass of all types in JSON schema.
 */
public abstract class ValueType {

	/**
	 * Returns type identifier of this type.
	 * @return type idenfitier.
	 */
	public abstract TypeId getTypeId();
	
	/**
	 * Validates instance of this type.
	 * @param value instance value.
	 * @param location where this value was found.
	 * @param problems problems detected.
	 */
	public void validateInstance(JsonValue value, JsonLocation location, List<Problem> problems) {
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
