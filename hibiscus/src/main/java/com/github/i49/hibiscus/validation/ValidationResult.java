package com.github.i49.hibiscus.validation;

import java.util.List;

import javax.json.JsonValue;

import com.github.i49.hibiscus.problems.Problem;

/**
 * Result of validation.
 */
public class ValidationResult {
	
	private final JsonValue value;
	private final List<Problem> problems;

	/**
	 * Constructs this result.
	 * @param value the JSON value which is found at root of JSON instance.
	 * @param problems the list of problems found by validation.
	 */
	public ValidationResult(JsonValue value, List<Problem> problems) {
		this.value = value;
		this.problems = problems;
	}
	
	/**
	 * Returns the JSON value which is found at root of JSON instance.
	 * @return JSON value.
	 * @see javax.json.JsonValue
	 */
	public JsonValue getValue() {
		return value;
	}
	
	/**
	 * Returns whether validation found any problem or not.
	 * @return {@code true} if no problem found by validation, or {@code false} if any problems are found.
	 */
	public boolean hasProblems() {
		return !problems.isEmpty();
	}

	/**
	 * Returns list of problems found by validation.
	 * If the validation found no problems, empty list will be returned.
	 * @return list of problems.
	 */
	public List<Problem> getProblems() {
		return problems;
	}
}
