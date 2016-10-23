package com.github.i49.hibiscus;

public class AnyType extends ValueType {
	
	public static final AnyType INSTANCE = new AnyType();

	private AnyType() {
	}

	@Override
	public Type getType() {
		return null;
	}

	@Override
	public boolean isTypeOf(Type type) {
		return true;
	}
}
