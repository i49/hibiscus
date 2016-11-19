package com.github.i49.hibiscus.schema;

import java.util.HashSet;
import java.util.Set;

import javax.json.JsonString;

import com.github.i49.hibiscus.common.TypeId;
import com.github.i49.hibiscus.json.JsonValues;
import com.github.i49.hibiscus.schema.facets.MaxLengthFacet;
import com.github.i49.hibiscus.schema.facets.MinLengthFacet;
import com.github.i49.hibiscus.schema.facets.PatternFacet;
import com.github.i49.hibiscus.schema.facets.ValueSetFacet;

/**
 * JSON type to hold string.
 */
public class StringType extends AbstractSimpleType<JsonString> {
	
	@Override
	public TypeId getTypeId() {
		return TypeId.STRING;
	}
	
	/**
	 * Specifies minimum number of characters in this string. 
	 * @param length the minimum number of characters.
	 * @return this type.
	 */
	public StringType minLength(int length) {
		addFacet(new MinLengthFacet(length));
		return this;
	}
	
	/**
	 * Specifies maximum number of characters in this string. 
	 * @param length the maximum number of characters.
	 * @return this type.
	 */
	public StringType maxLength(int length) {
		addFacet(new MaxLengthFacet(length));
		return this;
	}
	
	/**
	 * Specifies values allowed for this type.
	 * @param values the values allowed.
	 * @return this type.
	 */
	public StringType values(String... values) {
		Set<JsonString> valueSet = new HashSet<>();
		for (String value: values) {
			valueSet.add(JsonValues.createString(value));
		}
		addFacet(ValueSetFacet.of(valueSet));
		return this;
	}
	
	/**
	 * Specifies string pattern.
	 * Note that the pattern string must be compatible with Java regular expression. 
	 * @param regex the pattern string.
	 * @return this type.
	 */
	public StringType pattern(String regex) {
		addFacet(new PatternFacet(regex));
		return this;
	}
}
