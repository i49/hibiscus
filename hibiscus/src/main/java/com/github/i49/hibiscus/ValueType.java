package com.github.i49.hibiscus;

public abstract class ValueType {
	
	public static enum Type {
		ARRAY,
		BOOLEAN,
		INTEGER,
		NUMBER,
		NULL,
		OBJECT,
		STRING
	}
	
	public abstract Type getType();
	
	public abstract boolean isTypeOf(Type type); 
 }
