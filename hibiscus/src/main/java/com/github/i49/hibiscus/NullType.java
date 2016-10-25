package com.github.i49.hibiscus;

public class NullType extends ValueType {

	public static final NullType INSTANCE = new NullType();
	
	private NullType() {
	}
	
	@Override
	public TypeId getType() {
		return TypeId.NULL;
	}

	@Override
	public boolean isTypeOf(TypeId type) {
		return (type == TypeId.NULL);
	}
}
