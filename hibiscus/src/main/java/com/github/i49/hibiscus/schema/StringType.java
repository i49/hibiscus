package com.github.i49.hibiscus.schema;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.json.JsonString;
import javax.json.JsonValue;

import com.github.i49.hibiscus.common.IntRange;
import com.github.i49.hibiscus.common.TypeId;
import com.github.i49.hibiscus.json.JsonValues;
import com.github.i49.hibiscus.problems.Problem;
import com.github.i49.hibiscus.problems.StringPatternProblem;
import com.github.i49.hibiscus.problems.StringTooLongProblem;
import com.github.i49.hibiscus.problems.StringTooShortProblem;

/**
 * JSON type to hold string.
 */
public class StringType extends SimpleType {
	
	private int minLength = -1;
	private int maxLength = -1;
	private Pattern pattern;
	
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
		this.setValueSet(valueSet);
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
}
