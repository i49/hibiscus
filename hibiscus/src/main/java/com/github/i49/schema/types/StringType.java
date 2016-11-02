package com.github.i49.schema.types;

import com.github.i49.schema.TypeId;

public class StringType extends ValueType {
	
	private static final StringType DEFAULT = new StringType();

	public static StringType getDefault() {
		return DEFAULT;
	}
	
	@Override
	public TypeId getTypeId() {
		return TypeId.STRING;
	}
}
