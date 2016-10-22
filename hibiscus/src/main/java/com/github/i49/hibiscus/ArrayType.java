package com.github.i49.hibiscus;

public class ArrayType extends ContainerType {

	private final ValueType itemType;
	
	public ArrayType(ValueType itemType) {
		this.itemType = itemType;
	}
	
	@Override
	public Type getType() {
		return Type.ARRAY;
	}
	
	@Override
	public boolean isTypeOf(Type type) {
		return (type == Type.ARRAY);
	}

	public ValueType getItemType() {
		return itemType;
	}
}
