package com.github.i49.hibiscus.formats;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

import javax.json.JsonString;

/**
 * String format which represents date and time defined in RFC 3339.
 */
public class DateTimeFormat extends AbstractFormat<JsonString> implements StringFormat {

	public static final DateTimeFormat INSTANCE = new DateTimeFormat();
	
	private final DateTimeFormatter formatter;
	
	private DateTimeFormat() {
		formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withResolverStyle(ResolverStyle.LENIENT);
	}

	@Override
	public String getName() {
		return "datetime";
	}

	@Override
	public boolean matches(JsonString value) {
		try {
			formatter.parse(value.getString());
			return true;
		} catch (DateTimeParseException e) {
			return false;
		}
	}
}
