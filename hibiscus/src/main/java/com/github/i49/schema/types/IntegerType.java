package com.github.i49.schema.types;

import com.github.i49.schema.TypeId;

public class IntegerType extends ValueType {

	private static final IntegerType DEFAULT = new IntegerType();
	
	public static IntegerType getDefault() {
		return DEFAULT;
	}
	
	@Override
	public TypeId getTypeId() {
		return TypeId.INTEGER;
	}
}
