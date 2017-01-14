package com.github.i49.hibiscus.problems;

import java.util.Locale;

import javax.json.JsonValue;
import javax.json.stream.JsonLocation;

import com.github.i49.hibiscus.common.JsonDocument;
import com.github.i49.hibiscus.common.JsonPointer;

/**
 * A skeletal class which help implement {@link Problem} interface.
 */
abstract class AbstractProblem implements Problem {

	private JsonLocation location;
	private JsonPointer pointer;
	private JsonDocument document;
	
	@Override
	public JsonLocation getLocation() {
		return location;
	}

	@Override
	public Problem setLocation(JsonLocation location) {
		this.location = location;
		return this;
	}
	
	@Override
	public JsonPointer getPointer() {
		return pointer;
	}
	
	@Override
	public void setPointer(JsonPointer pointer, JsonDocument document) {
		this.pointer = pointer;
		this.document = document;
	}
	
	@Override
	public JsonValue getCauseValue() {
		if (this.document == null || this.pointer == null) {
			return null;
		}
		return this.document.getValueByPointer(this.pointer);
	}

	@Override
	public String getDescription(Locale locale) {
		if (locale == null) {
			locale = Locale.getDefault();
		}
		return buildDescription(locale);
	}
	
	@Override
	public String getMessage(Locale locale) {
		if (locale == null) {
			locale = Locale.getDefault();
		}
		return Messages.PROBLEM_MESSAGE(locale, getLocation(), getDescription(locale));
	}
	
	/**
	 * Returns a string representation of this problem which has the same content as {@link #getMessage()}.
	 * @return a string representation of this problem. 
	 * @see #getMessage()
	 */
	@Override
	public String toString() {
		return getMessage();
	}
	
	/**
	 * Builds the description of this problem.
	 * The description built by this method will be obtained through {@link #getDescription(Locale)}.
	 * @param locale the locale desired for the message. Cannot be {@code null}.
	 * @return built description.
	 * @see #getDescription(Locale)
	 */
	protected abstract String buildDescription(Locale locale); 
}
