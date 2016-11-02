package com.github.i49.schema.types;

import com.github.i49.hibiscus.validation.TypeMap;
import com.github.i49.schema.TypeId;

public class ArrayType extends ContainerType {

	private final TypeMap typeMap;

	public static ArrayType of(ValueType[] itemTypes) {
		return new ArrayType(TypeMap.of(itemTypes));
	}
	
	protected ArrayType() {
		this.typeMap = TypeMap.empty();
	}

	protected ArrayType(TypeMap typeMap) {
		this.typeMap = typeMap;
	}
	
	@Override
	public TypeId getTypeId() {
		return TypeId.ARRAY;
	}
	
	public TypeMap getItemTypes() {
		return typeMap;
	}
}
