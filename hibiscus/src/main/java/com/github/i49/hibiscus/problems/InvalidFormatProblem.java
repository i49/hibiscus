package com.github.i49.hibiscus.problems;

import java.util.Locale;
import java.util.Set;

import javax.json.JsonValue;

import com.github.i49.hibiscus.formats.Format;

/**
 * Problem that a value in JSON document does not have the expected format declared in the schema.
 *
 * <p>At the present moment, this problem can be caused by {@code string()} type only.</p>
 * 
 * @param <V> the type of {@link JsonValue} which caused this problem.
 */
public class InvalidFormatProblem<V extends JsonValue> extends TypedJsonValueProblem<V> {

	private final Set<Format<V>> formats;
	
	/**
	 * Constructs this problem.
	 * @param formats the expected formats declared in the schema.
	 */
	public InvalidFormatProblem(Set<Format<V>> formats) {
		this.formats = formats;
	}
	
	/**
	 * Returns the expected formats for the type declared in the schema.
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
