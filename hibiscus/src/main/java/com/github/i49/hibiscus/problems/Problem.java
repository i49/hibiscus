package com.github.i49.hibiscus.problems;

import javax.json.stream.JsonLocation;

public abstract class Problem {

	private final JsonLocation location;
	
	public Problem(JsonLocation location) {
		this.location = location;
	}
	
	public JsonLocation getLocation() {
		return location;
	}
	
	@Override
	public String toString() {
		JsonLocation location = getLocation();
		StringBuilder b = new StringBuilder();
		b.append("Line ").append(location.getLineNumber());
		b.append(", column ").append(location.getColumnNumber());
		b.append(": ").append(getMessage());
		return b.toString();
	}

	public abstract String getMessage();
}
