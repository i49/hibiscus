package com.github.i49.hibiscus;

public class MissingPropertyException extends ValidationException {

	private static final long serialVersionUID = 1L;
	private final String fieldName;
	
	public MissingPropertyException(String fieldName) {
		this.fieldName = fieldName;
	}
	
	public String getPropertyName() {
		return fieldName;
	}
	
	@Override
	public String getMessage() {
		return "Required object property \"" + getPropertyName() + "\" is missing.";
	}
}
