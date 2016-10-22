package com.github.i49.hibiscus;

public class ArrayType extends ContainerType {

	private final Type itemType;
	
	public ArrayType(Type itemType) {
		this.itemType = itemType;
	}
	
	public Type getItemType() {
		return itemType;
	}
}
