package com.github.i49.hibiscus;

public class StringType extends ValueType {

	@Override
	public TypeId getType() {
		return TypeId.STRING;
	}

	@Override
	public boolean isTypeOf(TypeId type) {
		return (type == TypeId.STRING);
	}
}
