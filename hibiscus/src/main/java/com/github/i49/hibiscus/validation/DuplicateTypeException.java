package com.github.i49.hibiscus.validation;

public class DuplicateTypeException extends SchemaException {

	private static final long serialVersionUID = 1L;

	private final TypeId typeId;
	
	public DuplicateTypeException(TypeId typeId) {
		this.typeId = typeId;
	}

	public TypeId getTypeId() {
		return typeId;
	}
	
	@Override
	public String getMessage() {
		return "Duplicate type " + getTypeId() + " was found.";
	}
}
