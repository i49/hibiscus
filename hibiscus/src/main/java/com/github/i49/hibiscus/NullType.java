package com.github.i49.hibiscus;

public class NullType extends ValueType {

	@Override
	public Type getType() {
		return Type.NULL;
	}

	@Override
	public boolean isTypeOf(Type type) {
		return (type == Type.NULL);
	}
}
