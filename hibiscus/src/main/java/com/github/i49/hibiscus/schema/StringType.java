package com.github.i49.hibiscus.schema;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.json.JsonString;
import javax.json.JsonValue;

import com.github.i49.hibiscus.common.TypeId;
import com.github.i49.hibiscus.json.JsonValues;
import com.github.i49.hibiscus.problems.Problem;
import com.github.i49.hibiscus.problems.StringPatternProblem;
import com.github.i49.hibiscus.problems.StringTooLongProblem;
import com.github.i49.hibiscus.problems.StringTooShortProblem;

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
	 * @param length minimum number of characters.
	 * @return this type.
	 */
	public StringType minLength(int length) {
		addFacet(new MinLengthFacet(length));
		return this;
	}
	
	/**
	 * Specifies maximum number of characters in this string. 
	 * @param length maximum number of characters.
	 * @return this type.
	 */
	public StringType maxLength(int length) {
		addFacet(new MaxLengthFacet(length));
		return this;
	}
	
	/**
	 * Specifies values allowed for this type.
	 * @param values values allowed.
	 * @return this type.
	 */
	public StringType values(String... values) {
		Set<JsonValue> valueSet = new HashSet<>();
		for (String value: values) {
			valueSet.add(JsonValues.createString(value));
		}
		addFacet(new ValueSetFacet<JsonString>(valueSet));
		return this;
	}
	
	/**
	 * Specifies string pattern.
	 * @param regex string compatible with Java regular expression.
	 * @return this type.
	 */
	public StringType pattern(String regex) {
		addFacet(new PatternFacet(regex));
		return this;
	}
	
	/**
	 * Facet constraining value space to values with at least the specific number of characters. 
	 */
	private static class MinLengthFacet implements Facet<JsonString> {

		private final int minLength;
		
		public MinLengthFacet(int minLength) {
			this.minLength = minLength;
		}
		
		@Override
		public void apply(JsonString value, List<Problem> problems) {
			int length = value.getString().length();
			if (length < minLength) {
				problems.add(new StringTooShortProblem(value, length, minLength));
			}
		}
	}

	/**
	 * Facet constraining value space to values with at most the specific number of characters. 
	 */
	private static class MaxLengthFacet implements Facet<JsonString> {

		private final int maxLength;
		
		public MaxLengthFacet(int maxLength) {
			this.maxLength = maxLength;
		}
		
		@Override
		public void apply(JsonString value, List<Problem> problems) {
			int length = value.getString().length();
			if (length > maxLength) {
				problems.add(new StringTooLongProblem(value, length, maxLength));
			}
		}
	}
	
	/**
	 * Facet constraining a value space to values that matches a regular expression.  
	 */
	private static class PatternFacet implements Facet<JsonString> {

		private final Pattern pattern;

		/**
		 * Constructs this facet.
		 * @param regex the string compatible with Java regular expression.
		 */
		public PatternFacet(String regex) {
			this.pattern = Pattern.compile(regex);
		}
		
		/**
		 * Applies facet to the instance.
		 * @param value the string value.
		 * @param problems the list to which detected problems to be added.
		 */
		@Override
		public void apply(JsonString value, List<Problem> problems) {
			Matcher m = pattern.matcher(value.getString());
			if (!m.matches()) {
				problems.add(new StringPatternProblem(value));
			}
		}
	}
}
