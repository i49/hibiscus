package com.github.i49.hibiscus;

public class NullType extends ValueType {

	public static final NullType INSTANCE = new NullType();
	
	private NullType() {
	}
	
	@Override
	public Type getType() {
		return Type.NULL;
	}

	@Override
	public boolean isTypeOf(Type type) {
		return (type == Type.NULL);
	}
}
