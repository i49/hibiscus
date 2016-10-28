package com.github.i49.hibiscus.validation;

public class IntegerType extends ValueType {

	@Override
	public TypeId getType() {
		return TypeId.INTEGER;
	}

	@Override
	public boolean isTypeOf(TypeId type) {
		return (type == TypeId.INTEGER);
	}
}
