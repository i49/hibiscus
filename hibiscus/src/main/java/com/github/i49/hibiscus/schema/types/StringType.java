package com.github.i49.hibiscus.schema.types;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.json.JsonString;
import javax.json.JsonValue;

import com.github.i49.hibiscus.json.JsonValues;
import com.github.i49.hibiscus.schema.TypeId;
import com.github.i49.hibiscus.schema.problems.Problem;
import com.github.i49.hibiscus.schema.problems.StringLengthProblem;
import com.github.i49.hibiscus.schema.problems.StringPatternProblem;

/**
 * JSON type to hold string.
 */
public class StringType extends SimpleType {
	
	private static final StringType DEFAULT = new DefaultStringType();

	private int minLength = 0;
	private int maxLength = Integer.MAX_VALUE;
	private Pattern pattern;
	
	/**
	 * Returns this type with default settings.
	 * @return immutable type with default settings.
	 */
	public static StringType getDefault() {
		return DEFAULT;
	}
	
	@Override
	public TypeId getTypeId() {
		return TypeId.STRING;
	}
	
	@Override
	public void validateInstance(JsonValue value, List<Problem> problems) {
		super.validateInstance(value, problems);
		JsonString string = (JsonString)value;
		validateLength(string, problems);
		validateAgainstPattern(string, problems);
	}

	/**
	 * Specifies minimum number of characters in this string. 
	 * @param length minimum number of characters.
	 * @return this type.
	 */
	public StringType minLength(int length) {
		this.minLength = length;
		return this;
	}
	
	/**
	 * Specifies maximum number of characters in this string. 
	 * @param length maximum number of characters.
	 * @return this type.
	 */
	public StringType maxLength(int length) {
		this.maxLength = length;
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
		setValueSet(valueSet);
		return this;
	}
	
	/**
	 * Specifies string pattern.
	 * @param regex string compatible with Java regular expression.
	 * @return this type.
	 */
	public StringType pattern(String regex) {
		this.pattern = Pattern.compile(regex);
		return this;
	}

	/**
	 * Validates instance length.
	 * @param value string value.
	 * @param problems list to which detected problems to be added.
	 */
	private void validateLength(JsonString value, List<Problem> problems) {
		int length = value.getString().length();
		if (length < minLength) {
			problems.add(new StringLengthProblem(minLength, length));
		}
		if (length > maxLength) {
			problems.add(new StringLengthProblem(maxLength, length));
		}
	}
	
	/**
	 * Validates instance against specified pattern.
	 * @param value string value.
	 * @param problems list to which detected problems to be added.
	 */
	private void validateAgainstPattern(JsonString value, List<Problem> problems) {
		if (pattern == null) {
			return;
		}
		Matcher m = pattern.matcher(value.getString());
		if (!m.matches()) {
			problems.add(new StringPatternProblem(value.getString()));
		}
	}
	
	/**
	 * String type without any constraints.
	 * When one of mutating method is called, the method creates new instance.
	 */
	private static class DefaultStringType extends StringType {

		@Override
		public void validateInstance(JsonValue value, List<Problem> problems) {
			// We don't need to validate against default string.
		}

		@Override
		public StringType minLength(int length) {
			return new StringType().minLength(length);
		}

		@Override
		public StringType maxLength(int length) {
			return new StringType().maxLength(length);
		}
		
		@Override
		public StringType values(String... values) {
			return new StringType().values(values);
		}
		
		@Override
		public StringType pattern(String regex) {
			return new StringType().pattern(regex);
		}
	}
}
