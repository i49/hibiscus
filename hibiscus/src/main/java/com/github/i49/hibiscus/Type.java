package com.github.i49.hibiscus;

public abstract class Type {

	private final TypeKind typeKind;
	
	public Type(TypeKind typeKind) {
		this.typeKind = typeKind;
	}
	
	public TypeKind getTypeKind() {
		return typeKind;
	}
}
