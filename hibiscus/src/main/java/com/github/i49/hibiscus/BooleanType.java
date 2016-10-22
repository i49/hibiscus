package com.github.i49.hibiscus;

public class BooleanType extends ValueType {

	@Override
	public Type getType() {
		return Type.BOOLEAN;
	}

	@Override
	public boolean isTypeOf(Type type) {
		return (type == Type.BOOLEAN);
	}
}
