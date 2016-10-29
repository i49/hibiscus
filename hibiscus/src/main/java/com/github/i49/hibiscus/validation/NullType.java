package com.github.i49.hibiscus.validation;

public class NullType extends ValueType {

	public static final NullType INSTANCE = new NullType();
	
	private NullType() {
	}
	
	@Override
	public TypeId getTypeId() {
		return TypeId.NULL;
	}
}
