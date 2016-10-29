package com.github.i49.hibiscus.validation;

public class SchemaComponents {

	public static Property optional(String name, ValueType type, ValueType... moreTypes) {
		return new Property(name, type, moreTypes, false);
	}
	
	public static Property required(String name, ValueType type, ValueType... moreTypes) {
		return new Property(name, type, moreTypes, true);
	}
	
	public static ArrayType array(ValueType... itemTypes) {
		return ArrayType.of(itemTypes);
	}

	public static BooleanType bool() {
		return BooleanType.getDefault();
	}

	public static IntegerType integer() {
		return IntegerType.getDefault();
	}
	
	public static NumberType number() {
		return NumberType.getDefault();
	}
	
	public static NullType nullValue() {
		return NullType.INSTANCE;
	}

	public static ObjectType object(Property... properties) {
		return ObjectType.of(properties);
	}
	
	public static StringType string() {
		return StringType.getDefault();
	}
	
	private SchemaComponents() {
	}
}
