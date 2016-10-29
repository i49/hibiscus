package com.github.i49.hibiscus.validation;

public class BooleanType extends ValueType {
	
	private static final BooleanType DEFAULT = new BooleanType();
	
	public static BooleanType getDefault() {
		return DEFAULT;
	}

	@Override
	public TypeId getTypeId() {
		return TypeId.BOOLEAN;
	}

	@Override
	public boolean isTypeOf(TypeId type) {
		return (type == TypeId.BOOLEAN);
	}
}
