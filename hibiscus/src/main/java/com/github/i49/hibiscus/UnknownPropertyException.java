package com.github.i49.hibiscus;

public class UnknownPropertyException extends ValidationException {

	private static final long serialVersionUID = 1L;
	private final String propertyName;
	
	public UnknownPropertyException(String propertyName) {
		this.propertyName = propertyName;
	}
	
	public String getPropertyName() {
		return propertyName;
	}

	@Override
	public String getMessage() {
		return "Object property \"" + getPropertyName() + "\" is not supported.";
	}
}
