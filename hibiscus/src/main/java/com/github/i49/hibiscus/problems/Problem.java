package com.github.i49.hibiscus.problems;

import java.util.Locale;

import javax.json.JsonValue;
import javax.json.stream.JsonLocation;

import com.github.i49.hibiscus.common.JsonDocument;
import com.github.i49.hibiscus.common.JsonPointer;

/**
 * Common interface which should be implemented by all classes representing problems 
 * that will be detected by validation of JSON documents.
 * 
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
 * 
 * <p>Each kind of problems is encapsulated in its own class defined in this package. 
 * These problem classes implement the common interface {@link Problem}.
 * This interface provides several useful methods in order to 
 * give users the clues on the found problem.
 * </p> 
 * <ol>
 * <li><p>location of problem</p>
 * <p>{@link #getLocation()} method provides
 * <i>where</i> the problem was detected in original JSON document. The information includes
 * line and column numbers in the JSON document.</p>
 * </li>
 * <li><p>value that caused problem</p>
 * <p>{@link #getCauseValue()} method provides
 * {@link JsonValue} that caused the problem.</p>
 * </li>
 * <li><p>JSON pointer</p>
 * <p>{@link #getPointer()} method provides
 * {@link JsonPointer} which refers to the JSON value that caused the problem.</p>
 * </li>
 * <li><p>description of problem</p>
 * <p>{@link #getDescription()} method gives
 * the textural description of the problem detected.</p>
 * </li>
 * <li><p>string representing of problem</p>
 * <p>{@link #getMessage()} method gives
 * the string representation of the problem which includes the location and the description of the problem.
 * This is handy method to display the problem for the application users instantly.</p>
 * </li>
 * </ol>
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
 * <td>{@link ArrayDuplicateItemProblem}</td>
 * <td>{@code array()}</td>
 * <td>Two elements in an array have the same value.</td>
 * </tr>
 * <tr>
 * <td>2</td>
 * <td>{@link ArrayLengthProblem}</td>
 * <td>{@code array()}</td>
 * <td>The number of elements in an array is not same exactly as expected.</td>
 * </tr>
 * <tr>
 * <td>3</td>
 * <td>{@link ArrayTooLongProblem}</td>
 * <td>{@code array()}</td>
 * <td>Array has elements more than allowed.</td>
 * </tr>
 * <tr>
 * <td>4</td>
 * <td>{@link ArrayTooShortProblem}</td>
 * <td>{@code array()}</td>
 * <td>Array has elements fewer than required.</td>
 * </tr>
 * <tr>
 * <td>5</td>
 * <td>{@link AssertionFailureProblem}</td>
 * <td>all but {@code nil()}</td>
 * <td>An assertion made on a type was failed.</td>
 * </tr>
 * <tr>
 * <td>6</td>
 * <td>{@link ExclusiveLowerBoundProblem}</td>
 * <td>{@code integer()}, {@code number()}</td>
 * <td>A numeric value is less than or equal to the lower bound of the valid range.</td>
 * </tr>
 * <tr>
 * <td>7</td>
 * <td>{@link ExclusiveUpperBoundProblem}</td>
 * <td>{@code integer()}, {@code number()}</td>
 * <td>A numeric value is greater than or equal to the upper bound of the valid range.</td>
 * </tr>
 * <tr>
 * <td>8</td>
 * <td>{@link InclusiveLowerBoundProblem}</td>
 * <td>{@code integer()}, {@code number()}</td>
 * <td>A numeric value is less than the lower bound of the valid range.</td>
 * </tr>
 * <tr>
 * <td>9</td>
 * <td>{@link InclusiveUpperBoundProblem}</td>
 * <td>{@code integer()}, {@code number()}</td>
 * <td>A numeric value is greater than the upper bound of the valid range.</td>
 * </tr>
 * <tr>
 * <td>10</td>
 * <td>{@link InvalidFormatProblem}</td>
 * <td>{@code string()}</td>
 * <td>A value does not have the expected format declared in the schema.</td>
 * </tr>
 * <tr>
 * <td>11</td>
 * <td>{@link MissingPropertyProblem}</td>
 * <td>{@code object()}</td>
 * <td>An object does not have a mandatory property.</td>
 * </tr>
 * <tr>
 * <td>12</td>
 * <td>{@link NoSuchEnumeratorProblem}</td>
 * <td>{@code boolean()}, {@code integer()}, {@code number()}, {@code string()}</td>
 * <td>A value does not match any value in the enumeration allowed for the type.</td>
 * </tr>
 * <tr>
 * <td>13</td>
 * <td>{@link StringLengthProblem}</td>
 * <td>{@code string()}</td>
 * <td>A string does not have exactly the same as expected characters.</td>
 * </tr>
 * <tr>
 * <td>14</td>
 * <td>{@link StringPatternProblem}</td>
 * <td>{@code string()}</td>
 * <td>A string value does not match the expected pattern specified as a regular expression.</td>
 * </tr>
 * <tr>
 * <td>15</td>
 * <td>{@link StringTooLongProblem}</td>
 * <td>{@code string()}</td>
 * <td>A string is longer than allowed.</td>
 * </tr>
 * <tr>
 * <td>16</td>
 * <td>{@link StringTooShortProblem}</td>
 * <td>{@code string()}</td>
 * <td>A string is shorter than required.</td>
 * </tr>
 * <tr>
 * <td>16</td>
 * <td>{@link TypeMismatchProblem}</td>
 * <td>all</td>
 * <td>The type of a value in JSON document does not match the type declared in the schema.</td>
 * </tr>
 * <tr>
 * <td>17</td>
 * <td>{@link UnknownPropertyProblem}</td>
 * <td>{@code object()}</td>
 * <td>An object has a property which is not explicitly declared in the schema.</td>
 * </tr>
 * </table>
 * 
 * @see com.github.i49.hibiscus.validation.ValidationResult
 */
public interface Problem {

	/**
	 * Returns the location where this problem was found including line and column numbers
	 * on the input JSON document.
	 * @return {@link JsonLocation} object which holds the location of this problem.
	 * @see JsonLocation
	 */
	JsonLocation getLocation();

	/**
	 * Assigns the location where this problem was found.
	 * @param location the location which indicates where this problem was found. 
	 *                 {@code null} indicates the location is unknown.
	 * @return this problem.
	 */
	Problem setLocation(JsonLocation location);
	
	/**
	 * Returns the JSON pointer which refers to the value that caused this problem.
	 * @return the JSON pointer to the cause value.
	 */
	JsonPointer getPointer();

	/**
	 * Assigns the JSON pointer which refers to the value that caused this problem.
	 * @param pointer the JSON pointer to the cause value.
	 * @param document the JSON document to which the JSON pointer refers. 
	 */
	void setPointer(JsonPointer pointer, JsonDocument document);
	
	/**
	 * Returns the actual {@link JsonValue} which caused this problem.
	 * @return the actual {@link JsonValue} which caused this problem.
	 */
	JsonValue getCauseValue();
	
	/**
	 * Returns the description of this problem for the default locale.
	 * <p>Calling this method is equivalent to call {@code getDescription(Locale.getDefault())}.</p>
	 * @return the description of this problem.
	 * @see #getDescription(Locale)
	 */
	default String getDescription() {
		return getDescription(Locale.getDefault());
	}
	
	/**
	 * Returns the description of this problem for specific locale.
	 * @param locale the locale for which the description is desired.
	 *               if the argument is {@code null}, default locale is used instead.
	 * @return the description of this problem.
	 */
	String getDescription(Locale locale);
	
	/**
	 * Returns the message of this problem for the default locale.
	 * <p>This is a handy method to display this problem to the application users.
	 * The message to be returned includes both the location and the description of this problem.</p>
	 * <p>Calling this method is equivalent to call {@code getMessage(Locale.getDefault())}.</p>
	 * 
	 * @return the message of this problem.
	 * @see #getMessage(Locale)
	 */
	default String getMessage() {
		return getMessage(Locale.getDefault());
	}
	
	/**
	 * Returns the message of this problem for specific locale.
	 * <p>This is a handy method to display this problem to the application users.
	 * The message to be returned includes both the location and the description of this problem.</p>
	 * 
	 * @param locale the locale for which the message is desired.
	 *               if the argument is {@code null}, default locale is used instead.
	 * @return the message of this problem.
	 */
	String getMessage(Locale locale); 
}
