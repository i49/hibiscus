package com.github.i49.hibiscus.problems;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import javax.json.JsonNumber;
import javax.json.JsonValue;

import com.github.i49.hibiscus.common.TypeId;

/**
 * Messages of problems.
 */
final class Messages {

	private static final String BUNDLE_BASE_NAME = Problem.class.getPackage().getName() + ".messages";

	static String TYPE_MISMATCH(Locale locale, TypeId actualType, Set<TypeId> expectedTypes) {
		ResourceBundle bundle = getBundle(locale);
		String actual = formatType(bundle, actualType);
		String expected = formatTypes(bundle, expectedTypes);
		return localize(bundle, "TYPE_MISMATCH", actual, expected);
	}
	
	static String UNKNOWN_VALUE(Locale locale, JsonValue value, String allowedValues) {
		return localize(getBundle(locale), "UNKNOWN_VALUE", value, allowedValues);
	}
	
	static String ARRAY_TOO_LONG(Locale locale, int actualSize, int limitSize) {
		return localize(getBundle(locale), "ARRAY_TOO_LONG", actualSize, limitSize);
	}

	static String ARRAY_TOO_SHORT(Locale locale, int actualSize, int limitSize) {
		return localize(getBundle(locale), "ARRAY_TOO_SHORT", actualSize, limitSize);
	}
	
	static String MISSING_PROPERTY(Locale locale, String propertyName) {
		return localize(getBundle(locale), "MISSING_PROPERTY", propertyName);
	}
	
	static String UNKNOWN_PROPERTY(Locale locale, String propertyName) {
		return localize(getBundle(locale), "UNKNOWN_PROPERTY", propertyName);
	}
	
	static String STRING_TOO_LONG(Locale locale, int actualLength, int limitLength) {
		return localize(getBundle(locale), "STRING_TOO_LONG", actualLength, limitLength);
	}
	
	static String STRING_TOO_SHORT(Locale locale, int actualLength, int limitLength) {
		return localize(getBundle(locale), "STRING_TOO_SHORT", actualLength, limitLength);
	}

	static String STRING_PATTERN(Locale locale, String value) {
		return localize(getBundle(locale), "STRING_PATTERN", value);
	}
	
	static String LESS_THAN_MINIMUM(Locale locale, JsonNumber value, BigDecimal lowerBound) {
		return localize(getBundle(locale), "LESS_THAN_MINIMUM", value, lowerBound);
	}
	
	static String MORE_THAN_MAXIMUM(Locale locale, JsonNumber value, BigDecimal upperBound) {
		return localize(getBundle(locale), "MORE_THAN_MAXIMUM", value, upperBound);
	}
	
	static String NOT_MORE_THAN_MINIMUM(Locale locale, JsonNumber value, BigDecimal lowerBound) {
		return localize(getBundle(locale), "NOT_MORE_THAN_MINIMUM", value, lowerBound);
	}

	static String NOT_LESS_THAN_MAXIMUM(Locale locale, JsonNumber value, BigDecimal upperBound) {
		return localize(getBundle(locale), "NOT_LESS_THAN_MAXIMUM", value, upperBound);
	}
	
	private static ResourceBundle getBundle(Locale locale) {
		return ResourceBundle.getBundle(BUNDLE_BASE_NAME, locale);
	}
	
	private static String formatType(ResourceBundle bundle, TypeId type) {
		String pattern = bundle.getString("type");
		return MessageFormat.format(pattern, type);
	}
	
	private static String formatTypes(ResourceBundle bundle, Set<TypeId> types) {
		StringBuilder b = new StringBuilder("[");
		int index = 0;
		for (TypeId type: types) {
			if (index++ > 0) {
				b.append(", ");
			}
			b.append(formatType(bundle, type));
		}
		return b.append("]").toString();
	}

	private static String localize(ResourceBundle bundle, String key, Object... arguments) {
		String pattern = bundle.getString(key);
		return MessageFormat.format(pattern, arguments);
	}

	private Messages() {
	}
}
