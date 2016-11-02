package com.github.i49.schema.types;

import com.github.i49.schema.TypeId;

public class BooleanType extends ValueType {
	
	private static final BooleanType DEFAULT = new BooleanType();
	
	public static BooleanType getDefault() {
		return DEFAULT;
	}

	@Override
	public TypeId getTypeId() {
		return TypeId.BOOLEAN;
	}
}
