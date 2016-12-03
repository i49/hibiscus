package com.github.i49.hibiscus.problems;

import java.util.Locale;
import java.util.Set;

import javax.json.JsonValue;

import com.github.i49.hibiscus.formats.Format;

/**
 * Problem that the format of the value is invalid.
 *
 * @param <V> the type of JSON values.
 */
public class InvalidFormatProblem<V extends JsonValue> extends ValueProblem<V> {

	private final Set<Format<V>> formats;
	
	/**
	 * Constructs this problem.
	 * @param value the actual value in JSON document.
	 * @param formats the expected formats.
	 */
	public InvalidFormatProblem(V value, Set<Format<V>> formats) {
		super(value);
		this.formats = formats;
	}
	
	/**
	 * Returns the expected formats for the type.
	 * @return the expected formats.
	 */
	public Set<Format<V>> getExpectedFormats() {
		return formats;
	}

	@Override
	protected String buildDescription(Locale locale) {
		return Messages.INVALID_FORMAT_PROBLEM(locale, getActualValue(), getExpectedFormats());
	}
}
