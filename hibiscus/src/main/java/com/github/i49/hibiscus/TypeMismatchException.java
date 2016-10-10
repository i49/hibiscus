package com.github.i49.hibiscus;

public class TypeMismatchException extends ValidationException {

	private static final long serialVersionUID = 1L;

	private final String propertyName;
	private final TypeKind expectedType;
	private final TypeKind actualType;
	
	public TypeMismatchException(String propertyName, TypeKind expectedType, TypeKind actualType) {
		this.propertyName = propertyName;
		this.expectedType = expectedType;
		this.actualType = actualType;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public TypeKind getExpectedType() {
		return expectedType;
	}

	public TypeKind getActualType() {
		return actualType;
	}
	
	@Override
	public String getMessage() {
		String name = getPropertyName();
		if (name == null) {
			name = "(undefined)";
		}
		return "Expected type of property \"" + name + 
				"\" is " + getExpectedType() + 
				" but actual type is " + getActualType();
	}
}
