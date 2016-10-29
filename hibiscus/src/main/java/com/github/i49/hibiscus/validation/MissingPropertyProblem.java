package com.github.i49.hibiscus.validation;

import javax.json.stream.JsonLocation;

public class MissingPropertyProblem extends Problem {

	private final String propertyName;
	
	public MissingPropertyProblem(String propertyName, JsonLocation location) {
		super(location);
		this.propertyName = propertyName;
	}
	
	public String getPropertyName() {
		return propertyName;
	}

	@Override
	public String getMessage() {
		return "Property \"" + getPropertyName() + "\" is required.";
	}
}
