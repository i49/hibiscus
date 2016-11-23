package com.github.i49.hibiscus.schema;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.PatternSyntaxException;

import javax.json.JsonString;

import com.github.i49.hibiscus.common.TypeId;
import com.github.i49.hibiscus.json.JsonValues;
import com.github.i49.hibiscus.schema.facets.StringLengthFacet;
import com.github.i49.hibiscus.schema.facets.MaxLengthFacet;
import com.github.i49.hibiscus.schema.facets.MinLengthFacet;
import com.github.i49.hibiscus.schema.facets.PatternFacet;
import com.github.i49.hibiscus.schema.facets.ValueSetFacet;

/**
 * JSON type for string value.
 * 
 * <h3>Overview of string type</h3>
 * <p>String type can have following facets constraining its value space.</p>
 * <ul>
 * <li>minLength</li>
 * <li>maxLength</li>
 * <li>length</li>
 * <li>values</li>
 * <li>pattern</li>
 * </ul>
 *
 * <h3>String type facets</h3>
 * 
 * <h4>minLength</h4>
 * <p>{@link #minLength} constrains the minimum number of characters in the string.</p>
 * <blockquote><pre>string().minLength(3);</pre></blockquote>
 *
 * <h4>maxLength</h4>
 * <p>{@link #maxLength} constrains the maximum number of characters in the string.</p>
 * <blockquote><pre>string().maxLength(10);</pre></blockquote>
 *
 * <h4>length</h4>
 * <p>{@link #length} constrains the number of characters in the string.
 * For instance, password string which consists of exactly eight characters are defined as follows.</p>
 * <blockquote><pre>string().length(8);</pre></blockquote>
 *
 * <h4>values</h4>
 * <p>{@link #values} constrains the set of values allowed in the string.</p>
 * <blockquote><pre>string().values("Spring", "Summer", "Autumn", "Winter");</pre></blockquote>
 *
 * <h4>pattern</h4>
 * <p>{@link #pattern} constrains the pattern of the string with a regular expression.</p>
 * <blockquote><pre>string().pattern("\\d{3}-?\\d{2}-?\\d{4}");</pre></blockquote>
 */
public class StringType extends AbstractSimpleType<JsonString> implements SimpleType {
	
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
	 * Specifies the minimum number of characters in this string. 
	 * @param length the minimum number of characters. Must be non-negative value.
	 * @return this type.
	 * @exception SchemaException if length specified is negative.
	 */
	public StringType minLength(int length) {
		checkLength(length);
		addFacet(new MinLengthFacet(length));
		return this;
	}
	
	/**
	 * Specifies the maximum number of characters in this string. 
	 * @param length the maximum number of characters. Must be non-negative value.
	 * @return this type.
	 * @exception SchemaException if length specified is negative.
	 */
	public StringType maxLength(int length) {
		checkLength(length);
		addFacet(new MaxLengthFacet(length));
		return this;
	}
	
	/**
	 * Specifies the number of characters expected in this string. 
	 * @param length the number of characters. Must be non-negative value.
	 * @return this type.
	 * @exception SchemaException if length specified is negative.
	 */
	public StringType length(int length) {
		checkLength(length);
		addFacet(new StringLengthFacet(length));
		return this;
	}
	
	/**
	 * Specifies set of values allowed for this type.
	 * @param values the values allowed. Each value cannot be {@code null}.
	 * @return this type.
	 * @exception SchemaException if one of values specified is null.
	 */
	public StringType values(String... values) {
		Set<JsonString> valueSet = new HashSet<>();
		int index = 0;
		for (String value: values) {
			if (value == null) {
				throw new SchemaException(Messages.ONE_OF_VALUES_IS_NULL(index));
			}
			valueSet.add(JsonValues.createString(value));
			index++;
		}
		addFacet(ValueSetFacet.of(valueSet));
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
	
	private static void checkLength(int length) {
		if (length < 0) {
			throw new SchemaException(Messages.STRING_LENGTH_IS_NEGATIVE(length));
		}
	}
}
