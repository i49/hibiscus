package com.github.i49.hibiscus.validation;

public abstract class ValueType {
	
	public abstract TypeId getType();
	
	public abstract boolean isTypeOf(TypeId type); 
 }
