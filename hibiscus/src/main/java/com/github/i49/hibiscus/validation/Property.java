package com.github.i49.hibiscus.validation;

public class Property {

	private final String key;
	private final ValueType type;
	private final boolean required;
	
	public Property(String key, ValueType type, boolean required) {
		this.key = key;
		this.type = type;
		this.required = required;
	}
	
	public String getKey() {
		return key;
	}
	
	public ValueType getType() {
		return type;
	}
	
	public boolean isRequired() {
		return required;
	}
}
