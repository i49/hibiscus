package com.github.i49.hibiscus.validation;

public class StringType extends ValueType {
	
	private static final StringType DEFAULT = new StringType();

	public static StringType getDefault() {
		return DEFAULT;
	}
	
	@Override
	public TypeId getTypeId() {
		return TypeId.STRING;
	}

	@Override
	public boolean isTypeOf(TypeId type) {
		return (type == TypeId.STRING);
	}
}
