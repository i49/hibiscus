package com.github.i49.hibiscus.validation;

public class IntegerType extends ValueType {

	private static final IntegerType DEFAULT = new IntegerType();
	
	public static IntegerType getDefault() {
		return DEFAULT;
	}
	
	@Override
	public TypeId getTypeId() {
		return TypeId.INTEGER;
	}

	@Override
	public boolean isTypeOf(TypeId type) {
		return (type == TypeId.INTEGER);
	}
}
