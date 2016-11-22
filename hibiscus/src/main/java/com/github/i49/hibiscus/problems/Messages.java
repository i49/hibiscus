package com.github.i49.hibiscus.problems;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

import javax.json.JsonNumber;
import javax.json.JsonString;
import javax.json.JsonValue;

import com.github.i49.hibiscus.common.TypeId;

/**
 * Messages for validation problems.
 */
final class Messages {

	private static final String BUNDLE_BASE_NAME = Problem.class.getPackage().getName() + ".messages";

	static String TYPE_MISMATCH(Locale locale, TypeId actualType, Set<TypeId> expectedTypes) {
		return localize(locale, "TYPE_MISMATCH", actualType, expectedTypes);
	}
	
	static String UNKNOWN_VALUE(Locale locale, JsonValue value, Set<JsonValue> allowedValues) {
		return localize(locale, "UNKNOWN_VALUE", value, allowedValues);
	}
	
	static String ARRAY_TOO_LONG(Locale locale, int actualSize, int limitSize) {
		return localize(locale, "ARRAY_TOO_LONG", actualSize, limitSize);
	}

	static String ARRAY_TOO_SHORT(Locale locale, int actualSize, int limitSize) {
		return localize(locale, "ARRAY_TOO_SHORT", actualSize, limitSize);
	}
	
	static String MISSING_PROPERTY(Locale locale, String propertyName) {
		return localize(locale, "MISSING_PROPERTY", propertyName);
	}
	
	static String UNKNOWN_PROPERTY(Locale locale, String propertyName) {
		return localize(locale, "UNKNOWN_PROPERTY", propertyName);
	}
	
	static String STRING_TOO_LONG(Locale locale, int actualLength, int limitLength) {
		return localize(locale, "STRING_TOO_LONG", actualLength, limitLength);
	}
	
	static String STRING_TOO_SHORT(Locale locale, int actualLength, int limitLength) {
		return localize(locale, "STRING_TOO_SHORT", actualLength, limitLength);
	}

	static String STRING_PATTERN(Locale locale, JsonString value) {
		return localize(locale, "STRING_PATTERN", value);
	}
	
	static String LESS_THAN_MINIMUM(Locale locale, JsonNumber value, BigDecimal lowerBound) {
		return localize(locale, "LESS_THAN_MINIMUM", value, lowerBound);
	}
	
	static String MORE_THAN_MAXIMUM(Locale locale, JsonNumber value, BigDecimal upperBound) {
		return localize(locale, "MORE_THAN_MAXIMUM", value, upperBound);
	}
	
	static String NOT_MORE_THAN_MINIMUM(Locale locale, JsonNumber value, BigDecimal lowerBound) {
		return localize(locale, "NOT_MORE_THAN_MINIMUM", value, lowerBound);
	}

	static String NOT_LESS_THAN_MAXIMUM(Locale locale, JsonNumber value, BigDecimal upperBound) {
		return localize(locale, "NOT_LESS_THAN_MAXIMUM", value, upperBound);
	}
	
	private static String localize(Locale locale, String key, Object... arguments) {
		ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_BASE_NAME, locale);
		String pattern = bundle.getString(key);
		Object[] decorated = decorate(bundle, arguments);
		return MessageFormat.format(pattern, decorated);
	}
	
	private static Object[] decorate(ResourceBundle bundle, Object[] arguments) {
		return Arrays.stream(arguments).map(o->decorate(bundle, o)).toArray();
	}
	
	private static Object decorate(ResourceBundle bundle, Object object) {
		if (object instanceof TypeId) {
			object = decorate(bundle, (TypeId)object);
		} if (object instanceof JsonValue) {
			object = decorate(bundle, (JsonValue)object);
		} else if (object instanceof Set<?>) {
			Set<?> set = (Set<?>)object;
			if (set.size() > 0) {
				Object entry = set.iterator().next();
				if (entry instanceof TypeId) {
					@SuppressWarnings("unchecked")
					Set<TypeId> typeSet = (Set<TypeId>)set;
					object = decorateTypeSet(bundle, typeSet);
				} else if (entry instanceof JsonValue) {
					@SuppressWarnings("unchecked")
					Set<JsonValue> valueSet = (Set<JsonValue>)set;
					object = decorateValueSet(bundle, valueSet);
				}
			}
		}
		return object;
	}

	private static String decorate(ResourceBundle bundle, TypeId type) {
		String pattern = bundle.getString("type");
		return MessageFormat.format(pattern, type);
	}
	
	private static String decorateTypeSet(ResourceBundle bundle, Set<TypeId> types) {
		String separator = bundle.getString("type.separator");
		String joined = types.stream().map(type->decorate(bundle, type)).collect(Collectors.joining(separator));
		String pattern = bundle.getString("type.set");
		return MessageFormat.format(pattern, joined);
	}
	
	private static String decorate(ResourceBundle bundle, JsonValue value) {
		String pattern = bundle.getString("value");
		return MessageFormat.format(pattern, value);
	}

	private static String decorateValueSet(ResourceBundle bundle, Set<JsonValue> values) {
		String separator = bundle.getString("value.separator");
		String joined = values.stream().map(value->decorate(bundle, value)).collect(Collectors.joining(separator));
		String pattern = bundle.getString("value.set");
		return MessageFormat.format(pattern, joined);
	}

	private Messages() {
	}
}
