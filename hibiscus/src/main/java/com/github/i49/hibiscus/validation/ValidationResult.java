package com.github.i49.hibiscus.validation;

import java.util.List;

import javax.json.JsonValue;

public class ValidationResult {
	
	private final JsonValue value;
	private final List<Problem> problems;

	public ValidationResult(JsonValue value, List<Problem> problems) {
		this.value = value;
		this.problems = problems;
	}
	
	public JsonValue getValue() {
		return value;
	}
	
	public boolean hasProblems() {
		return !problems.isEmpty();
	}
	
	public List<Problem> getProblems() {
		return problems;
	}
}
