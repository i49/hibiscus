package com.github.i49.hibiscus.validation;

import java.util.List;

import javax.json.JsonValue;

import com.github.i49.hibiscus.problems.Problem;

/**
 * Result of JSON validation.
 */
public interface ValidationResult {
	
	/**
	 * Returns the JSON value found at root of JSON document.
	 * 
	 * The type of value returned by this method is defined in JSR 353: Java API for JSON Processing. 
	 * All the values below the root of document can be retrieved easily via the API.
	 * 
	 * @return JSON value. 
	 * @see <a href="https://jsonp.java.net/">JSR 353: Java API for JSON Processing</a>
	 */
	JsonValue getValue();
	
	/**
	 * Returns whether the validation detected any problems of JSON document or not.
	 * 
	 * @return {@code true} if the validation detected any problems, or {@code false} it detected no problems.
	 */
	boolean hasProblems();

	/**
	 * Returns all problems detected by the validation.
	 * 
	 * If there are no problems found, empty list will be returned.
	 * 
	 * @return list of problems, never be {@code null}.
	 */
	List<Problem> getProblems();
}
