package com.github.i49.hibiscus.validation;

public class AnyType extends ValueType {
	
	public static final AnyType INSTANCE = new AnyType();

	private AnyType() {
	}

	@Override
	public TypeId getTypeId() {
		return null;
	}

	@Override
	public boolean isTypeOf(TypeId type) {
		return true;
	}
}
