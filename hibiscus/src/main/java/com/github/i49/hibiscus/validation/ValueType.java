package com.github.i49.hibiscus.validation;

/**
 * Primitive type in JSON schema.
 */
public abstract class ValueType {
	
	public abstract TypeId getTypeId();
	
	public abstract boolean isTypeOf(TypeId type); 
	
	@Override
	public String toString() {
		return getTypeId().toString().toLowerCase();
	}
 }
