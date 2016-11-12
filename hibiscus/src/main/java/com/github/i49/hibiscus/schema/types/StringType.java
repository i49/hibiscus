package com.github.i49.hibiscus.schema.types;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.json.JsonString;
import javax.json.JsonValue;

import com.github.i49.hibiscus.json.JsonValues;
import com.github.i49.hibiscus.schema.IntRange;
import com.github.i49.hibiscus.schema.TypeId;
import com.github.i49.hibiscus.schema.problems.Problem;
import com.github.i49.hibiscus.schema.problems.StringPatternProblem;
import com.github.i49.hibiscus.schema.problems.StringTooLongProblem;
import com.github.i49.hibiscus.schema.problems.StringTooShortProblem;

/**
 * JSON type to hold string.
 */
public class StringType extends SimpleType {
	
	private static final StringType DEFAULT = new DefaultStringType();

	private int minLength = -1;
	private int maxLength = -1;
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
		StringType self = modifiable();
		self.minLength = length;
		return self;
	}
	
	/**
	 * Specifies maximum number of characters in this string. 
	 * @param length maximum number of characters.
	 * @return this type.
	 */
	public StringType maxLength(int length) {
		StringType self = modifiable();
		self.maxLength = length;
		return self;
	}
	
	/**
	 * Specifies values allowed for this type.
	 * @param values values allowed.
	 * @return this type.
	 */
	public StringType values(String... values) {
		StringType self = modifiable();
		Set<JsonValue> valueSet = new HashSet<>();
		for (String value: values) {
			valueSet.add(JsonValues.createString(value));
		}
		self.setValueSet(valueSet);
		return self;
	}
	
	/**
	 * Specifies string pattern.
	 * @param regex string compatible with Java regular expression.
	 * @return this type.
	 */
	public StringType pattern(String regex) {
		StringType self = modifiable();
		self.pattern = Pattern.compile(regex);
		return self;
	}
	
	protected StringType modifiable() {
		return this;
	}

	/**
	 * Validates instance length.
	 * @param value string value in JSON instance.
	 * @param problems list to which detected problems to be added.
	 */
	private void validateLength(JsonString value, List<Problem> problems) {
		int length = value.getString().length();
		if (minLength != -1 && length < minLength) {
			problems.add(new StringTooShortProblem(value, length, IntRange.of(minLength, maxLength)));
		}
		if (maxLength != -1 && length > maxLength) {
			problems.add(new StringTooLongProblem(value, length, IntRange.of(minLength, maxLength)));
		}
	}
	
	/**
	 * Validates instance against specified pattern.
	 * @param value the string value.
	 * @param problems the list to which detected problems to be added.
	 */
	private void validateAgainstPattern(JsonString value, List<Problem> problems) {
		if (pattern == null) {
			return;
		}
		Matcher m = pattern.matcher(value.getString());
		if (!m.matches()) {
			problems.add(new StringPatternProblem(value));
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
		public StringType modifiable() {
			return new StringType();
		}
	}
}
