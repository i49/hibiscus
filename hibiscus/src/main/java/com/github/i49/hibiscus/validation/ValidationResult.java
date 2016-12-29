package com.github.i49.hibiscus.validation;

import java.util.List;

import javax.json.JsonValue;

import com.github.i49.hibiscus.problems.Problem;

/**
 * A interface to inspect the result of the validation of JSON document.
 * {@link JsonValidator} returns an object implementing this interface
 * as result of the validation.
 * 
 * <p>The following code shows how to retrieve the JSON value found at the root of the input JSON document.</p>
 * <blockquote><pre><code>
 * ValidationResult result = ...;
 * JsonValue value = result.getValue();
 * </code></pre></blockquote>
 * 
 * <p>The following code shows how to retrieve the problems detected in the process of the validation.</p>
 * <blockquote><pre><code>
 * ValidationResult result = ...;
 * for (Problem p: result.getProblems()) {
 *   // handles each problem here.
 *   System.out.println(p.toString());
 * }
 * </code></pre></blockquote>
 * 
 *  @see JsonValidator
 */
public interface ValidationResult {
	
	/**
	 * Returns the JSON value found at the root of the input JSON document including its descendant values.
	 * The classes representing the values returned by this method are all defined in
	 * JSR 353: Java API for JSON Processing.
	 * All the values under the root of the document can be retrieved through the API.
	 * 
	 * @return the JSON value found at the root of the input JSON document which includes its descendant values.
	 * 
	 * @see <a href="http://json-processing-spec.java.net/">JSR 353: Java API for JSON Processing</a>
	 */
	JsonValue getValue();
	
	/**
	 * Returns whether the validation detected any problems violating the schema in the input JSON document or not.
	 * 
	 * @return {@code true} if the validation detected any problems, or {@code false} it detected no problems.
	 * 
	 * @see #getProblems()
	 */
	boolean hasProblems();

	/**
	 * Returns all problems detected in the process of the validation against the schema.
	 * If there are no problems found, an empty list will be returned.
	 * Each kind of problems is encapsulated as a class which implements {@link Problem} common interface.
	 * These classes of the problems are provided by {@link com.github.i49.hibiscus.problems} package.
	 * 
	 * @return the list of problems, which never be {@code null}.
	 * 
	 * @see #hasProblems()
	 * @see Problem
	 * @see com.github.i49.hibiscus.problems
	 */
	List<Problem> getProblems();
}
