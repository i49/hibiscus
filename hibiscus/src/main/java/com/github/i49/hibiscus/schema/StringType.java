package com.github.i49.hibiscus.schema;

import java.util.function.Predicate;
import java.util.regex.PatternSyntaxException;

import javax.json.JsonString;

import com.github.i49.hibiscus.problems.DescriptionSupplier;

/**
 * JSON type for string value.
 * 
 * <h3>String type constraints</h3>
 * <p>String type can have following facets constraining its value space.</p>
 * <ul>
 * <li>length</li>
 * <li>minLength</li>
 * <li>maxLength</li>
 * <li>enumeration</li>
 * <li>pattern</li>
 * </ul>
 * 
 * <h4>length</h4>
 * <p>{@link #length length} restricts the string to have a specific length.
 * For instance, password string which consists of exactly eight characters are defined as follows.</p>
 * <blockquote><pre>string().length(8);</pre></blockquote>
 *
 * <h4>minLength</h4>
 * <p>{@link #minLength minLength} limits the length of string to the range with specific lower bound.</p>
 * <blockquote><pre>string().minLength(3);</pre></blockquote>
 *
 * <h4>maxLength</h4>
 * <p>{@link #maxLength maxLength} limits the length of string to the range with specific upper bound.</p>
 * <blockquote><pre>string().maxLength(10);</pre></blockquote>
 *
 * <h4>enumeration</h4>
 * <p>{@link #enumeration enumeration} specifies a distinct set of valid values for the type.
 * <blockquote><pre>string().enumeration("Spring", "Summer", "Autumn", "Winter");</pre></blockquote>
 *
 * <h4>pattern</h4>
 * <p>{@link #pattern pattern} restricts the string to specified pattern represented by a regular expression.</p>
 * <blockquote><pre>string().pattern("\\d{3}-?\\d{2}-?\\d{4}");</pre></blockquote>
 */
public interface StringType extends AtomicType {
	
	/**
	 * Specifies the number of characters expected in this string. 
	 * @param length the number of characters. Must be non-negative value.
	 * @return this type.
	 * @exception SchemaException if length specified is negative.
	 */
	StringType length(int length);
	
	/**
	 * Specifies the minimum number of characters in this string. 
	 * @param length the minimum number of characters. Must be non-negative value.
	 * @return this type.
	 * @exception SchemaException if length specified is negative.
	 */
	StringType minLength(int length);
	
	/**
	 * Specifies the maximum number of characters in this string. 
	 * @param length the maximum number of characters. Must be non-negative value.
	 * @return this type.
	 * @exception SchemaException if length specified is negative.
	 */
	StringType maxLength(int length);
	
	/**
	 * Specifies set of values allowed for this type.
	 * @param values the values allowed. Each value cannot be {@code null}.
	 * @return this type.
	 * @exception SchemaException if one of values specified is {@code null}.
	 */
	StringType enumeration(String... values);
	
	/**
	 * Specifies the pattern of this string with regular expression.
	 * Note that the pattern string must be compatible with Java regular expression. 
	 * @param regex the regular expression to which this string is to be matched. Cannot be {@code null}.
	 * @return this type.
	 * @exception SchemaException if expression specified is null.
	 * @exception PatternSyntaxException if the expression's syntax is invalid.
	 */
	StringType pattern(String regex);
	
	/**
	 * Specifies assertion on this type.
	 * @param predicate the lambda expression that will return true if the assertion succeeded or false if failed.
	 * @param description the object to supply a description to be reported when the assertion failed.
	 * @return this type.
	 */
	StringType assertion(Predicate<JsonString> predicate, DescriptionSupplier<JsonString> description);
}
