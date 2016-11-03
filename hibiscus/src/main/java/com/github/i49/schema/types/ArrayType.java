package com.github.i49.schema.types;

import com.github.i49.schema.TypeId;

/**
 * JSON array which can hold zero or more values as elements.
 */
public class ArrayType extends ContainerType {

	private final TypeSet typeSet;

	public static ArrayType of(ValueType[] itemTypes) {
		return new ArrayType(TypeSet.of(itemTypes));
	}
	
	protected ArrayType(TypeSet typeSet) {
		this.typeSet = typeSet;
	}
	
	@Override
	public TypeId getTypeId() {
		return TypeId.ARRAY;
	}
	
	public TypeSet getItemTypes() {
		return typeSet;
	}
}
