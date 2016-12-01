package com.github.i49.hibiscus.schema;

import java.util.function.Predicate;
import java.util.regex.PatternSyntaxException;

import javax.json.JsonString;

import com.github.i49.hibiscus.common.TypeId;
import com.github.i49.hibiscus.facets.EnumerationFacet;
import com.github.i49.hibiscus.facets.LengthFacet;
import com.github.i49.hibiscus.facets.MaxLengthFacet;
import com.github.i49.hibiscus.facets.MinLengthFacet;
import com.github.i49.hibiscus.facets.PatternFacet;
import com.github.i49.hibiscus.json.JsonValues;
import com.github.i49.hibiscus.problems.DescriptionSupplier;
import com.github.i49.hibiscus.problems.StringLengthProblem;
import com.github.i49.hibiscus.problems.StringTooLongProblem;
import com.github.i49.hibiscus.problems.StringTooShortProblem;

import static com.github.i49.hibiscus.schema.Enumerations.*;

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
public class StringType extends AbstractRestrictableType<JsonString, StringType> implements AtomicType {
	
	/**
	 * Constructs this type.
	 */
	public StringType() {
	}
	
	@Override
	public TypeId getTypeId() {
		return TypeId.STRING;
	}
	
	/**
	 * Specifies the number of characters expected in this string. 
	 * @param length the number of characters. Must be non-negative value.
	 * @return this type.
	 * @exception SchemaException if length specified is negative.
	 */
	public StringType length(int length) {
		verifyLength(length);
		addFacet(new LengthFacet<JsonString>(length, StringType::getLength, StringLengthProblem::new));
		return this;
	}
	
	/**
	 * Specifies the minimum number of characters in this string. 
	 * @param length the minimum number of characters. Must be non-negative value.
	 * @return this type.
	 * @exception SchemaException if length specified is negative.
	 */
	public StringType minLength(int length) {
		verifyLength(length);
		addFacet(new MinLengthFacet<JsonString>(length, StringType::getLength, StringTooShortProblem::new));
		return this;
	}
	
	/**
	 * Specifies the maximum number of characters in this string. 
	 * @param length the maximum number of characters. Must be non-negative value.
	 * @return this type.
	 * @exception SchemaException if length specified is negative.
	 */
	public StringType maxLength(int length) {
		verifyLength(length);
		addFacet(new MaxLengthFacet<JsonString>(length, StringType::getLength, StringTooLongProblem::new));
		return this;
	}
	
	/**
	 * Specifies set of values allowed for this type.
	 * @param values the values allowed. Each value cannot be {@code null}.
	 * @return this type.
	 * @exception SchemaException if one of values specified is {@code null}.
	 */
	public StringType enumeration(String... values) {
		addFacet(EnumerationFacet.of(valueSet(JsonValues::createString, values)));
		return this;
	}
	
	/**
	 * Specifies the pattern of this string with regular expression.
	 * Note that the pattern string must be compatible with Java regular expression. 
	 * @param regex the regular expression to which this string is to be matched. Cannot be {@code null}.
	 * @return this type.
	 * @exception SchemaException if expression specified is null.
	 * @exception PatternSyntaxException if the expression's syntax is invalid.
	 */
	public StringType pattern(String regex) {
		if (regex == null) {
			throw new SchemaException(Messages.METHOD_PARAMETER_IS_NULL("pattern", "regex"));
		}
		addFacet(new PatternFacet(regex));
		return this;
	}
	
	@Override
	public StringType assertion(Predicate<JsonString> predicate, DescriptionSupplier<JsonString> description) {
		return super.assertion(predicate, description);
	}

	/**
	 * Returns the number of characters in string.
	 * @param value the string value.
	 * @return length of string.
	 */
	private static int getLength(JsonString value) {
		return value.getString().length();
	}

	/**
	 * Verifies value specified as length of string.
	 * @param length the length specified for strings.
	 */
	private static void verifyLength(int length) {
		if (length < 0) {
			throw new SchemaException(Messages.STRING_LENGTH_IS_NEGATIVE(length));
		}
	}
	
}
