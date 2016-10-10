package com.github.i49.hibiscus;

import com.fasterxml.jackson.databind.node.JsonNodeType;

public abstract class Schema {

	private static final ValueType STRING_TYPE = new ValueType(JsonNodeType.STRING);
	private static final ValueType NUMBER_TYPE = new ValueType(JsonNodeType.NUMBER);
	private static final ValueType BOOLEAN_TYPE = new ValueType(JsonNodeType.BOOLEAN);
	
	public static Property optional(String name, Type type) {
		return new Property(name, type, false);
	}
	
	public static Property required(String name, Type type) {
		return new Property(name, type, true);
	}
	
	public static ObjectType object(Property...properties) {
		return new ObjectType(properties);
	}
	
	public static ArrayType array(Type itemType) {
		return new ArrayType(itemType);
	}
	
	public static ValueType string() {
		return STRING_TYPE;
	}

	public static ValueType number() {
		return NUMBER_TYPE;
	}
	
	public static ValueType bool() {
		return BOOLEAN_TYPE;
	}
}
