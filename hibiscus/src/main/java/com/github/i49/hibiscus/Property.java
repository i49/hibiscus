package com.github.i49.hibiscus;

public class Property {

	private final String key;
	private final Type type;
	private final boolean required;
	
	public Property(String key, Type type, boolean required) {
		this.key = key;
		this.type = type;
		this.required = required;
	}
	
	public String getKey() {
		return key;
	}
	
	public Type getType() {
		return type;
	}
	
	public boolean isRequired() {
		return required;
	}
}
