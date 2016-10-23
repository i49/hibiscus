package com.github.i49.hibiscus;

public abstract class SchemaObjects {

	private static final BooleanType BOOLEAN_TYPE = new BooleanType();
	private static final IntegerType INTEGER_TYPE = new IntegerType();
	private static final NumberType NUMBER_TYPE = new NumberType();
	private static final StringType STRING_TYPE = new StringType();
	
	public static Property optional(String name, ValueType type) {
		return new Property(name, type, false);
	}
	
	public static Property required(String name, ValueType type) {
		return new Property(name, type, true);
	}
	
	public static ArrayType array(ValueType itemType) {
		return new ArrayType(itemType);
	}

	public static BooleanType bool() {
		return BOOLEAN_TYPE;
	}

	public static IntegerType integer() {
		return INTEGER_TYPE;
	}
	
	public static NumberType number() {
		return NUMBER_TYPE;
	}
	
	public static NullType none() {
		return NullType.INSTANCE;
	}

	public static ObjectType object() {
		return new ObjectType();
	}
	
	public static StringType string() {
		return STRING_TYPE;
	}
	
	public static AnyType any() {
		return AnyType.INSTANCE;
	}
}
