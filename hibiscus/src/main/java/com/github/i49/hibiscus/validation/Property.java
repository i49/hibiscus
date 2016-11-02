package com.github.i49.hibiscus.validation;

import com.github.i49.schema.types.ValueType;

public class Property {

	private final String name;
	private final TypeMap typeMap;
	private final boolean required;
	
	public Property(String name, ValueType type, ValueType[] moreTypes, boolean required) {
		this.name = name;
		this.typeMap = TypeMap.of(type, moreTypes);
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
