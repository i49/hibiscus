package com.github.i49.hibiscus;

public class ArrayType extends ContainerType {

	private final Type itemType;
	
	public ArrayType(Type itemType) {
		super(TypeKind.ARRAY);
		this.itemType = itemType;
	}
	
	public Type getItemType() {
		return itemType;
	}
}
