package com.github.i49.hibiscus;

public class Property {

	private final String name;
	private final Type type;
	private final boolean required;
	
	public Property(String name, Type type, boolean required) {
		this.name = name;
		this.type = type;
		this.required = required;
	}
	
	public String getName() {
		return name;
	}
	
	public Type getType() {
		return type;
	}
	
	public boolean isRequired() {
		return required;
	}
}
