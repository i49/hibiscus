package com.github.i49.hibiscus;

import com.fasterxml.jackson.databind.node.JsonNodeType;

public class TypeMismatchException extends ValidationException {

	private static final long serialVersionUID = 1L;

	private final String fieldName;
	private final JsonNodeType expectedType;
	private final JsonNodeType actualType;
	
	public TypeMismatchException(String fieldName, JsonNodeType expectedType, JsonNodeType actualType) {
		this.fieldName = fieldName;
		this.expectedType = expectedType;
		this.actualType = actualType;
	}

	public String getFieldName() {
		return fieldName;
	}

	public JsonNodeType getExpectedType() {
		return expectedType;
	}

	public JsonNodeType getActualType() {
		return actualType;
	}
	
	@Override
	public String getMessage() {
		return "Expected type of field \"" + getFieldName() + 
				"\" is " + getExpectedType() + 
				" but specified type is " + getActualType();
	}
}
