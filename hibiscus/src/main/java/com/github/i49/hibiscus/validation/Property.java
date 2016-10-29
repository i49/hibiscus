package com.github.i49.hibiscus.validation;

public class Property {

	private final String name;
	private final TypeMap typeMap;
	private final boolean required;
	
	public Property(String name, ValueType[] types, boolean required) {
		this.name = name;
		this.typeMap = TypeMap.of(types);
		this.required = required;
	}
	
	public String getName() {
		return name;
	}
	
	public TypeMap getTypeMap() {
		return typeMap;
	}
	
	public boolean isRequired() {
		return required;
	}
}
