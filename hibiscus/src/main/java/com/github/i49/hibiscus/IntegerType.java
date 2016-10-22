package com.github.i49.hibiscus;

public class IntegerType extends ValueType {

	@Override
	public Type getType() {
		return Type.INTEGER;
	}

	@Override
	public boolean isTypeOf(Type type) {
		return (type == Type.INTEGER);
	}
}
