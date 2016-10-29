package com.github.i49.hibiscus.validation;

public class NumberType extends ValueType {

	private static final NumberType DEFAULT = new NumberType();
	
	public static NumberType getDefault() {
		return DEFAULT;
	}
	
	@Override
	public TypeId getTypeId() {
		return TypeId.NUMBER;
	}

	@Override
	public boolean isTypeOf(TypeId type) {
		return (type == TypeId.NUMBER || type == TypeId.INTEGER);
	}
}
