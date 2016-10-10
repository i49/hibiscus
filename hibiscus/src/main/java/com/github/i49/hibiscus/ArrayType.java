package com.github.i49.hibiscus;

import com.fasterxml.jackson.databind.node.JsonNodeType;

public class ArrayType extends ContainerType {

	private final Type itemType;
	
	public ArrayType(Type itemType) {
		super(JsonNodeType.ARRAY);
		this.itemType = itemType;
	}
	
	public Type getItemType() {
		return itemType;
	}
}
