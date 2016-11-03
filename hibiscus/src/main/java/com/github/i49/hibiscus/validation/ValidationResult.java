package com.github.i49.hibiscus.validation;

import java.util.List;

import javax.json.JsonValue;

import com.github.i49.hibiscus.schema.problems.Problem;

/**
 * Result of validation.
 */
public class ValidationResult {
	
	private final JsonValue value;
	private final List<Problem> problems;

	/**
	 * Constructs this result.
	 * @param value JSON value which is represented in JSON.
	 * @param problems list of problems found by validation.
	 */
	public ValidationResult(JsonValue value, List<Problem> problems) {
		this.value = value;
		this.problems = problems;
	}
	
	/**
	 * Returns JSON value which is represented in JSON.
	 * @return JSON value.
	 * @see javax.json.JsonValue
	 */
	public JsonValue getValue() {
		return value;
	}
	
	/**
	 * Returns whether validation found any problem or not.
	 * @return true if no problem found by validation.
	 */
	public boolean hasProblems() {
		return !problems.isEmpty();
	}

	/**
	 * Returns list of problems found by validation.
	 * @return list of problems.
	 */
	public List<Problem> getProblems() {
		return problems;
	}
}
