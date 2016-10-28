package com.github.i49.hibiscus.validation;

public class BooleanType extends ValueType {

	@Override
	public TypeId getType() {
		return TypeId.BOOLEAN;
	}

	@Override
	public boolean isTypeOf(TypeId type) {
		return (type == TypeId.BOOLEAN);
	}
}
