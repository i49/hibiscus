package com.github.i49.schema.types;

import com.github.i49.schema.TypeId;

/**
 * Primitive type in JSON schema.
 */
public abstract class ValueType {
	
	public abstract TypeId getTypeId();
	
	@Override
	public String toString() {
		return getTypeId().toString().toLowerCase();
	}
 }
