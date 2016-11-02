package com.github.i49.schema.types;

import com.github.i49.schema.TypeId;

public class NumberType extends ValueType {

	private static final NumberType DEFAULT = new NumberType();
	
	public static NumberType getDefault() {
		return DEFAULT;
	}
	
	@Override
	public TypeId getTypeId() {
		return TypeId.NUMBER;
	}
}
