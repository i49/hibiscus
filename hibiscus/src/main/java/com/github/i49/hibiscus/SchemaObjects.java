package com.github.i49.hibiscus;

public abstract class SchemaObjects {

	private static final StringType STRING_TYPE = new StringType();
	private static final IntegerType INTEGER_TYPE = new IntegerType();
	private static final NumberType NUMBER_TYPE = new NumberType();
	private static final BooleanType BOOLEAN_TYPE = new BooleanType();
	private static final NullType NULL_TYPE = new NullType();
	
	public static Property optional(String name, ValueType type) {
		return new Property(name, type, false);
	}
	
	public static Property required(String name, ValueType type) {
		return new Property(name, type, true);
	}
	
	public static ObjectType object() {
		return new ObjectType();
	}
	
	public static ArrayType array(ValueType itemType) {
		return new ArrayType(itemType);
	}
	
	public static StringType string() {
		return STRING_TYPE;
	}

	public static IntegerType integer() {
		return INTEGER_TYPE;
	}
	
	public static NumberType number() {
		return NUMBER_TYPE;
	}
	
	public static BooleanType bool() {
		return BOOLEAN_TYPE;
	}
	
	public static NullType nil() {
		return NULL_TYPE;
	}
}
