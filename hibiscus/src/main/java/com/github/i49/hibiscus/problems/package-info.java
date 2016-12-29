/**
 * Provides classes representing problems that will be detected by JSON validator.
 * 
 * <h2 id="summary-of-problem">1. Summary of Problem</h2>
 * <p>When the JSON validator verifies a JSON document, 
 * it reports detected violations against given schema as <i>problems</i>.
 * Detected problems can be obtained through
 * {@link com.github.i49.hibiscus.validation.ValidationResult ValidationResult} interface,
 * which is returned from the invocation of the validation methods.
 * Following code shows how to handle the problems detected by the JSON validator.
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
 * This is handy method to display the problem for the application users instantly.</p>
 * </li>
 * </ol>
 * 
 * <h2 id="list-of-problems">2. List of Problems</h2>
 * 
 * <p>The table shown below lists all problems that the JSON validator can detect.</p>
 * 
 * <table border="1" cellpadding="4" style="border-collapse: collapse;">
 * <caption>The list of problems</caption>
 * <tr>
 * <th>No.</th>
 * <th>Problem Class</th>
 * <th>Causing Types</th>
 * <th>Description</th>
 * </tr>
 * <tr>
 * <td>1</td>
 * <td>{@link com.github.i49.hibiscus.problems.ArrayDuplicateItemProblem ArrayDuplicateItemProblem}</td>
 * <td>{@code array()}</td>
 * <td>Two elements in an array have the same value.</td>
 * </tr>
 * <tr>
 * <td>2</td>
 * <td>{@link com.github.i49.hibiscus.problems.ArrayLengthProblem ArrayLengthProblem}</td>
 * <td>{@code array()}</td>
 * <td>The number of elements in an array is not same exactly as expected.</td>
 * </tr>
 * <tr>
 * <td>3</td>
 * <td>{@link com.github.i49.hibiscus.problems.ArrayTooLongProblem ArrayTooLongProblem}</td>
 * <td>{@code array()}</td>
 * <td>Array has elements more than allowed.</td>
 * </tr>
 * <tr>
 * <td>4</td>
 * <td>{@link com.github.i49.hibiscus.problems.ArrayTooShortProblem ArrayTooShortProblem}</td>
 * <td>{@code array()}</td>
 * <td>Array has elements fewer than required.</td>
 * </tr>
 * <tr>
 * <td>5</td>
 * <td>{@link com.github.i49.hibiscus.problems.AssertionFailureProblem AssertionFailureProblem}</td>
 * <td>all but {@code nil()}</td>
 * <td>An assertion made on a type was failed.</td>
 * </tr>
 * <tr>
 * <td>6</td>
 * <td>{@link com.github.i49.hibiscus.problems.ExclusiveLowerBoundProblem ExclusiveLowerBoundProblem}</td>
 * <td>{@code integer()}, {@code number()}</td>
 * <td>A numeric value is less than or equal to the lower bound of the valid range.</td>
 * </tr>
 * <tr>
 * <td>7</td>
 * <td>{@link com.github.i49.hibiscus.problems.ExclusiveUpperBoundProblem ExclusiveUpperBoundProblem}</td>
 * <td>{@code integer()}, {@code number()}</td>
 * <td>A numeric value is greater than or equal to the upper bound of the valid range.</td>
 * </tr>
 * <tr>
 * <td>8</td>
 * <td>{@link com.github.i49.hibiscus.problems.InclusiveLowerBoundProblem InclusiveLowerBoundProblem}</td>
 * <td>{@code integer()}, {@code number()}</td>
 * <td>A numeric value is less than the lower bound of the valid range.</td>
 * </tr>
 * <tr>
 * <td>9</td>
 * <td>{@link com.github.i49.hibiscus.problems.InclusiveUpperBoundProblem InclusiveUpperBoundProblem}</td>
 * <td>{@code integer()}, {@code number()}</td>
 * <td>A numeric value is greater than the upper bound of the valid range.</td>
 * </tr>
 * <tr>
 * <td>10</td>
 * <td>{@link com.github.i49.hibiscus.problems.InvalidFormatProblem InvalidFormatProblem}</td>
 * <td>{@code string()}</td>
 * <td>A value does not have the expected format declared in the schema.</td>
 * </tr>
 * <tr>
 * <td>11</td>
 * <td>{@link com.github.i49.hibiscus.problems.MissingPropertyProblem MissingPropertyProblem}</td>
 * <td>{@code object()}</td>
 * <td>An object does not have a mandatory property.</td>
 * </tr>
 * <tr>
 * <td>12</td>
 * <td>{@link com.github.i49.hibiscus.problems.NoSuchEnumeratorProblem NoSuchEnumeratorProblem}</td>
 * <td>{@code boolean()}, {@code integer()}, {@code number()}, {@code string()}</td>
 * <td>A value does not match any value in the enumeration allowed for the type.</td>
 * </tr>
 * <tr>
 * <td>13</td>
 * <td>{@link com.github.i49.hibiscus.problems.StringLengthProblem StringLengthProblem}</td>
 * <td>{@code string()}</td>
 * <td>A string does not have exactly the same as expected characters.</td>
 * </tr>
 * <tr>
 * <td>14</td>
 * <td>{@link com.github.i49.hibiscus.problems.StringPatternProblem StringPatternProblem}</td>
 * <td>{@code string()}</td>
 * <td>A string value does not match the expected pattern specified as a regular expression.</td>
 * </tr>
 * <tr>
 * <td>15</td>
 * <td>{@link com.github.i49.hibiscus.problems.StringTooLongProblem StringTooLongProblem}</td>
 * <td>{@code string()}</td>
 * <td>A string is longer than allowed.</td>
 * </tr>
 * <tr>
 * <td>16</td>
 * <td>{@link com.github.i49.hibiscus.problems.StringTooShortProblem StringTooShortProblem}</td>
 * <td>{@code string()}</td>
 * <td>A string is shorter than required.</td>
 * </tr>
 * <tr>
 * <td>16</td>
 * <td>{@link com.github.i49.hibiscus.problems.TypeMismatchProblem TypeMismatchProblem}</td>
 * <td>all</td>
 * <td>The type of a value in JSON document does not match the type declared in the schema.</td>
 * </tr>
 * <tr>
 * <td>17</td>
 * <td>{@link com.github.i49.hibiscus.problems.UnknownPropertyProblem UnknownPropertyProblem}</td>
 * <td>{@code object()}</td>
 * <td>An object has a property which is not explicitly declared in the schema.</td>
 * </tr>
 * </table>
 */
package com.github.i49.hibiscus.problems;
