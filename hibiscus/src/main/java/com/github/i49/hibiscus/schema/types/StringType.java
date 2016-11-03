package com.github.i49.hibiscus.schema.types;

import java.util.List;

import javax.json.JsonString;
import javax.json.JsonValue;

import com.github.i49.hibiscus.schema.TypeId;
import com.github.i49.hibiscus.schema.problems.Problem;
import com.github.i49.hibiscus.schema.problems.StringLengthProblem;

/**
 * JSON type to hold string.
 */
public class StringType extends ValueType {
	
	private static final StringType DEFAULT = new DefaultStringType();

	private int minLength = 0;
	private int maxLength = Integer.MAX_VALUE;
	
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
		JsonString string = (JsonString)value;
		int length = string.getString().length();
		if (length < minLength) {
			problems.add(new StringLengthProblem(minLength, length));
		}
		if (length > maxLength) {
			problems.add(new StringLengthProblem(maxLength, length));
		}
	}

	/**
	 * Specifies minimum number of characters in this string. 
	 * @param length minimum number of characters.
	 * @return this string.
	 */
	public StringType minLength(int length) {
		this.minLength = length;
		return this;
	}
	
	/**
	 * Specifies maximum number of characters in this string. 
	 * @param length maximum number of characters.
	 * @return this string.
	 */
	public StringType maxLength(int length) {
		this.maxLength = length;
		return this;
	}
	
	/**
	 * String type without any constraints.
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
	}
}
