package com.github.i49.hibiscus.validation;

public class ArrayType extends ContainerType {

	private static final ArrayType GENERIC_ARRAY_TYPE = new ArrayType(TypeMap.empty());
	
	private final TypeMap typeMap;

	public static ArrayType of(ValueType[] itemTypes) {
		return new ArrayType(TypeMap.of(itemTypes));
	}
	
	public static ArrayType getGeneric() {
		return GENERIC_ARRAY_TYPE;
	}
	
	private ArrayType(TypeMap typeMap) {
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
