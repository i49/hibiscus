package com.github.i49.hibiscus.schema;

import java.util.function.Predicate;
import java.util.regex.PatternSyntaxException;

import javax.json.JsonString;

import com.github.i49.hibiscus.common.TypeId;
import com.github.i49.hibiscus.facets.Facet;
import com.github.i49.hibiscus.formats.StringFormat;
import com.github.i49.hibiscus.problems.ProblemDescriber;

/**
 * One of built-in types representing JSON string which has {@link TypeId#STRING} as a type identifier.
 * 
 * <p>An instance of this type can be created through {@link SchemaComponents#string()}.</p>
 * <blockquote><pre><code>
 * import static com.github.i49.hibiscus.schema.SchemaComponents.*;
 * StringType t = string();
 * </code></pre></blockquote>
 * 
 * <h3>Restrictions on this type</h3>
 * <p>This type allows you to impose following restrictions on the value space.</p>
 * <ol>
 * <li>length</li>
 * <li>minLength</li>
 * <li>maxLength</li>
 * <li>enumeration</li>
 * <li>pattern</li>
 * <li>assertion</li>
 * <li>format</li>
 * </ol>
 * 
 * <h4>1. length</h4>
 * <p><strong>length</strong> restricts the string to have a specific length.
 * For instance, password string which consists of exactly eight characters are defined as follows.</p>
 * <blockquote><pre><code>string().length(8);</code></pre></blockquote>
 *
 * <h4>2. minLength</h4>
 * <p><strong>minLength</strong> restricts the length of the string to the range with specific lower bound.</p>
 * <blockquote><pre><code>string().minLength(3);</code></pre></blockquote>
 *
 * <h4>3. maxLength</h4>
 * <p><strong>maxLength</strong> restricts the length of the string to the range with specific upper bound.</p>
 * <blockquote><pre><code>string().maxLength(10);</code></pre></blockquote>
 *
 * <h4>4. enumeration</h4>
 * <p><strong>enumeration</strong> specifies the value space of this type as a set of distinct values.</p>
 * <blockquote><pre><code>string().enumeration("Small", "Medium", "Large");</code></pre></blockquote>
 *
 * <h4>5. pattern</h4>
 * <p><strong>pattern</strong> restricts the string to specified pattern represented by a regular expression.</p>
 * <blockquote><pre><code>string().pattern("\\d{3}-?\\d{2}-?\\d{4}");</code></pre></blockquote>
 * 
 * <h4>6. assertion</h4>
 * <p><strong>assertion</strong> allows you to make a arbitrary assertion on the values of this type.</p>
 * 
 * <h4>7. format</h4>
 * <p><strong>format</strong> allows you to select the format for the values of this type from predefined formats.</p>
 * <blockquote><pre><code>
 * import static com.github.i49.hibiscus.formats.Formats.*;
 * string().format(email());</code></pre></blockquote>
 * 
 * @see SchemaComponents
 */
public interface StringType extends AtomicType {
	
	default TypeId getTypeId() {
		return TypeId.STRING;
	}
	
	/**
	 * Adds a {@link Facet} which restricts the value space of this type.
	 * @param facet the facet to be added. Cannot be {@code null}.
	 * @return this type.
	 * @exception SchemaException if facet specified is {@code null}.
	 */
	StringType facet(Facet<JsonString> facet);
	
	/**
	 * Specifies the number of characters expected in this string. 
	 * @param length the number of characters. Must be non-negative value.
	 * @return this type.
	 * @exception SchemaException if length specified is negative.
	 */
	StringType length(int length);
	
	/**
	 * Specifies the minimum number of characters required in this string. 
	 * @param length the minimum number of characters. Must be non-negative value.
	 * @return this type.
	 * @exception SchemaException if length specified is negative.
	 */
	StringType minLength(int length);
	
	/**
	 * Specifies the maximum number of characters allowed in this string. 
	 * @param length the maximum number of characters. Must be non-negative value.
	 * @return this type.
	 * @exception SchemaException if length specified is negative.
	 */
	StringType maxLength(int length);
	
	/**
	 * Specifies the value space of this type as a set of distinct values.
	 * @param values the values allowed for this type. Each value cannot be {@code null}.
	 * @return this type.
	 * @exception SchemaException if one of values specified is {@code null}.
	 */
	StringType enumeration(String... values);
	
	/**
	 * Specifies the pattern of this string with a regular expression.
	 * Note that the pattern string must be compatible with Java regular expression. 
	 * @param regex the regular expression to which this string is to be matched. Cannot be {@code null}.
	 * @return this type.
	 * @exception SchemaException if expression specified is null.
	 * @exception PatternSyntaxException if the expression's syntax is invalid.
	 */
	StringType pattern(String regex);
	
	/**
	 * Makes a assertion on the values of this type.
	 * @param predicate the lambda expression that will return {@code true} if the assertion succeeded or {@code false} if failed.
	 * @param describer the object supplying the description of the problem to be reported when the assertion failed.
	 * @return this type.
	 * @exception SchemaException if any of specified parameters is {@code null}.
	 */
	StringType assertion(Predicate<JsonString> predicate, ProblemDescriber<JsonString> describer);

	/**
	 * Specifies the format for the values of this type, which is selected from predefined formats.
	 * @param format the first format allowed.
	 * @param moreFormats the other formats allowed.
	 * @return this type.
	 */
	StringType format(StringFormat format, StringFormat... moreFormats);
}
