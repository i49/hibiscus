package com.github.i49.hibiscus;

public class NumberType extends ValueType {

	@Override
	public TypeId getType() {
		return TypeId.NUMBER;
	}

	@Override
	public boolean isTypeOf(TypeId type) {
		return (type == TypeId.NUMBER || type == TypeId.INTEGER);
	}
}
