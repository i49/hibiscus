package com.github.i49.hibiscus;

public class ArrayType extends ContainerType {

	private final ValueType itemType;
	
	public ArrayType(ValueType itemType) {
		this.itemType = itemType;
	}
	
	@Override
	public TypeId getType() {
		return TypeId.ARRAY;
	}
	
	@Override
	public boolean isTypeOf(TypeId type) {
		return (type == TypeId.ARRAY);
	}

	public ValueType getItemType() {
		return itemType;
	}
}
