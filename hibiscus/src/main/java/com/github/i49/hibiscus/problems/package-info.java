/**
 * Provides all problem classes that will be detected by JSON validator.
 * 
 * <h2 id="summary-of-problem">1. Summary of Problem</h2>
 * <p>When the JSON validator verifies a JSON document, 
 * it reports detected violations against given schema as <i>problems</i>.
 * Detected problems can be obtained through
 * {@link com.github.i49.hibiscus.validation.ValidationResult ValidationResult} interface,
 * which is returned from the validation methods.
 * Following code shows how to handle all problems detected by the JSON validator.
 * </p>
 * <blockquote><pre><code>
 * ValidationResult result = validator.validate(...);
 * for (Problem p: result.getProblems()) {
 *     // should handle each detected problem here.
 * } 
 * </code></pre></blockquote>
 * <p>Each kind of problem is encapsulated in its own class defined in this package. 
 * These problem classes implement the common interface {@link com.github.i49.hibiscus.problems.Problem Problem}.
 * This interface provides several methods in order to 
 * give users the clues on the found problem.
 * </p> 
 * <ol>
 * <li><p>The location of problem</p>
 * <p>{@link com.github.i49.hibiscus.problems.Problem#getLocation() Problem.getLocation()} method provides
 * <i>where</i> the problem was detected in original JSON document. The information includes
 * line and column numbers in the JSON document.</p>
 * </li>
 * <li><p>The description of problem</p>
 * <p>{@link com.github.i49.hibiscus.problems.Problem#getDescription() Problem.getDescription()} method gives
 * the textural description of the problem detected.</p>
 * </li>
 * <li><p>The string representing of problem</p>
 * <p>{@link com.github.i49.hibiscus.problems.Problem#getMessage() Problem.getMessage()} method gives
 * the string representation of the problem which includes the location and the description of the problem.
 * This is handy method to display the problem for the application users.</p>
 * </li>
 * </ol>
 * <h2>2. List of Problems</h2>
 */
package com.github.i49.hibiscus.problems;
