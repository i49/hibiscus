package com.github.i49.hibiscus.formats;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.json.JsonString;

/**
 * <strong>datetime</strong> format which represents date and time as defined in RFC 3339.
 * <p>
 * An instance of this format can be obtained by {@link Formats#datetime()} method.
 * </p>
 * 
 * @see <a href="https://www.ietf.org/rfc/rfc3339.txt">RFC 3339: Date and Time on the Internet: Timestamps</a>
 */
public class DateTimeFormat extends AbstractFormat<JsonString> implements StringFormat {

	/**
	 * The Singleton instance of this format.
	 */
	public static final DateTimeFormat INSTANCE = new DateTimeFormat();
	
	private static final Pattern PARTIAL_TIME_PATTERN;
	private static final Pattern TIME_OFFSET_PATTERN;
	
	static {
		PARTIAL_TIME_PATTERN = Pattern.compile("(\\d\\d)\\:(\\d\\d)\\:(\\d\\d)(\\.\\d+)?");	
		TIME_OFFSET_PATTERN = Pattern.compile("(\\d\\d)\\:(\\d\\d)");
	}
	
	private DateTimeFormat() {
	}

	@Override
	public String getName() {
		return "datetime";
	}

	@Override
	public boolean matches(JsonString jsonValue) {
		String[] parts = jsonValue.getString().split("T");
		if (parts.length != 2) {
			return false;
		}
		return parseFullDate(parts[0]) && parseFullTime(parts[1]);
	}
	
	/**
	 * Parses a date without an offset, such as '2011-12-03'.
	 * @param fullDate the text which represents a date.
	 * @return {@code true} if given text is a valid date, {@code false} otherwise.
	 */
	private static boolean parseFullDate(String fullDate) {
		try {
			DateTimeFormatter.ISO_LOCAL_DATE.parse(fullDate);
			return true;
		} catch (DateTimeParseException e) {
			return false;
		}
	}
	
	/**
	 * Parses a time with or without an offset.
	 * @param fullTime the text which represents a time.
	 * @return {@code true} if given text is a valid time, {@code false} otherwise.
	 */
	private static boolean parseFullTime(String fullTime) {
		if (fullTime.endsWith("Z")) {
			String partialTime = fullTime.substring(0, fullTime.length() - 1);
			return parsePartialTime(partialTime);
		} else {
			String[] parts = fullTime.split("\\+|\\-");
			if (parts.length != 2) {
				return false;
			}
			return parsePartialTime(parts[0]) && parseTimeOffset(parts[1]);
		}
	}
	
	/**
	 * Parses a time without an offset.
	 * @param partialTime the text which represents a time.
	 * @return {@code true} if given text is a valid time, {@code false} otherwise.
	 */
	private static boolean parsePartialTime(String partialTime) {
		Matcher m = PARTIAL_TIME_PATTERN.matcher(partialTime);
		if (!m.matches()) {
			return false;
		}
		int hour = Integer.parseInt(m.group(1));
		if (hour < 0 || hour > 23) {
			return false;
		}
		int minute = Integer.parseInt(m.group(2));
		if (minute < 0 || minute > 59) {
			return false;
		}
		int second = Integer.parseInt(m.group(3));
		// permits leap second.
		if (second < 0 || second > 60) {
			return false;
		}
		return true;
	}
	
	/**
	 * Parses a time offset.
	 * @param timeOffset the text which represents a time offset.
	 * @return {@code true} if given text is a valid time offset, {@code false} otherwise.
	 */
	private static boolean parseTimeOffset(String timeOffset) {
		Matcher m = TIME_OFFSET_PATTERN.matcher(timeOffset);
		if (!m.matches()) {
			return false;
		}
		int hour = Integer.parseInt(m.group(1));
		if (hour < 0 || hour > 23) {
			return false;
		}
		int minute =  Integer.parseInt(m.group(2));
		if (minute < 0 || minute > 59) {
			return false;
		}
		return true;
	}
}
