package com.github.i49.hibiscus;

public class StringType extends ValueType {

	@Override
	public Type getType() {
		return Type.STRING;
	}

	@Override
	public boolean isTypeOf(Type type) {
		return (type == Type.STRING);
	}
}
