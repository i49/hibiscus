package com.github.i49.hibiscus.problems;

import javax.json.stream.JsonLocation;

public class MissingPropertyProblem extends Problem {

	private final String name;
	
	public MissingPropertyProblem(String name, JsonLocation location) {
		super(location);
		this.name = name;
	}
	
	public String getPropertyName() {
		return name;
	}

	@Override
	public String getMessage() {
		return "Property \"" + getPropertyName() + "\" is required.";
	}
}
