package com.github.i49.hibiscus.problems;

import java.math.BigDecimal;
import static java.text.MessageFormat.format;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

import javax.json.JsonNumber;
import javax.json.JsonString;
import javax.json.JsonValue;
import javax.json.stream.JsonLocation;

import com.github.i49.hibiscus.common.TypeId;
import com.github.i49.hibiscus.formats.Format;

/**
 * Messages for validation problems.
 * Used internally by each {@link Problem} derived class.
 */
final class Messages {

	// Name of the resource bundle to be loaded.
	private static final String BUNDLE_BASE_NAME = Problem.class.getPackage().getName() + ".messages";

	/**
	 * Returns the message that represents the problem as a whole.
	 * @param locale the locale for the message.
	 * @param location the location where the problem was found. This can be {@code null}.
	 * @param description the description of the problem.
	 * @return the message for the problem.
	 */
	static String PROBLEM_MESSAGE(Locale locale, JsonLocation location, String description) {
		ResourceBundle bundle = getBundle(locale);
		String locationPart = null;
		if (location == null) {
			locationPart = bundle.getString("problem.location.unknown");
		} else {
			locationPart = format(bundle.getString("problem.location"), location.getLineNumber(), location.getColumnNumber());
		}
		return format(bundle.getString("problem.message"), locationPart, description);
	}
	
	static String TYPE_MISMATCH_PROBLEM(Locale locale, TypeId actualType, Set<TypeId> expectedTypes) {
		return localize(locale, "TYPE_MISMATCH_PROBLEM", actualType, expectedTypes);
	}
	
	static String NO_SUCH_ENUMERATOR_PROBLEM(Locale locale, JsonValue value, Set<Object> allowedValues) {
		return localize(locale, "NO_SUCH_ENUMERATOR_PROBLEM", value, allowedValues);
	}
	
	static String ARRAY_LENGTH_PROBLEM(Locale locale, int actualLength, int expectedLength) {
		return localize(locale, "ARRAY_LENGTH_PROBLEM", actualLength, expectedLength);
	}

	static String ARRAY_TOO_LONG_PROBLEM(Locale locale, int actualLength, int limitLength) {
		return localize(locale, "ARRAY_TOO_LONG_PROBLEM", actualLength, limitLength);
	}

	static String ARRAY_TOO_SHORT_PROBLEM(Locale locale, int actualLength, int limitLength) {
		return localize(locale, "ARRAY_TOO_SHORT_PROBLEM", actualLength, limitLength);
	}
	
	static String DUPLICATE_ITEM_PROBLEM(Locale locale, int itemIndex, JsonValue itemValue) {
		return localize(locale, "DUPLICATE_ITEM_PROBLEM", itemIndex, itemValue);
	}
	
	static String MISSING_PROPERTY_PROBLEM(Locale locale, String propertyName) {
		return localize(locale, "MISSING_PROPERTY_PROBLEM", propertyName);
	}
	
	static String UNKNOWN_PROPERTY_PROBLEM(Locale locale, String propertyName) {
		return localize(locale, "UNKNOWN_PROPERTY_PROBLEM", propertyName);
	}
	
	static String STRING_LENGTH_PROBLEM(Locale locale, int actualLength, int expectedLength) {
		return localize(locale, "STRING_LENGTH_PROBLEM", actualLength, expectedLength);
	}

	static String STRING_TOO_LONG_PROBLEM(Locale locale, int actualLength, int limitLength) {
		return localize(locale, "STRING_TOO_LONG_PROBLEM", actualLength, limitLength);
	}
	
	static String STRING_TOO_SHORT_PROBLEM(Locale locale, int actualLength, int limitLength) {
		return localize(locale, "STRING_TOO_SHORT_PROBLEM", actualLength, limitLength);
	}

	static String STRING_PATTERN_PROBLEM(Locale locale, JsonString value) {
		return localize(locale, "STRING_PATTERN_PROBLEM", value);
	}
	
	static String EXCLUSIVE_LOWER_BOUND_PROBLEM(Locale locale, JsonNumber value, BigDecimal lowerBound) {
		return localize(locale, "EXCLUSIVE_LOWER_BOUND_PROBLEM", value, lowerBound);
	}

	static String EXCLUSIVE_UPPER_BOUND_PROBLEM(Locale locale, JsonNumber value, BigDecimal upperBound) {
		return localize(locale, "EXCLUSIVE_UPPER_BOUND_PROBLEM", value, upperBound);
	}
	
	static String INCLUSIVE_LOWER_BOUND_PROBLEM(Locale locale, JsonNumber value, BigDecimal lowerBound) {
		return localize(locale, "INCLUSIVE_LOWER_BOUND_PROBLEM", value, lowerBound);
	}
	
	static String INCLUSIVE_UPPER_BOUND_PROBLEM(Locale locale, JsonNumber value, BigDecimal upperBound) {
		return localize(locale, "INCLUSIVE_UPPER_BOUND_PROBLEM", value, upperBound);
	}
	
	static <V extends JsonValue> String INVALID_FORMAT_PROBLEM(Locale locale, JsonValue value, Set<Format<V>> formats) {
		return localize(locale, "INVALID_FORMAT_PROBLEM", value, formats);
	}

	private static ResourceBundle getBundle(Locale locale) {
		return ResourceBundle.getBundle(BUNDLE_BASE_NAME, locale);
	}
	
	private static String localize(Locale locale, String key, Object... arguments) {
		ResourceBundle bundle = getBundle(locale);
		String pattern = bundle.getString(key);
		Object[] decorated = decorateObjects(bundle, arguments);
		return format(pattern, decorated);
	}

	private static Object[] decorateObjects(ResourceBundle bundle, Object[] arguments) {
		return Arrays.stream(arguments).map(o->decorateObject(bundle, o)).toArray();
	}

	private static Object decorateObject(ResourceBundle bundle, Object object) {
		if (object instanceof TypeId) {
			object = decorateType(bundle, (TypeId)object);
		} if (object instanceof JsonValue) {
			object = decorateJsonValue(bundle, (JsonValue)object);
		} else if (object instanceof Set<?>) {
			Set<?> set = (Set<?>)object;
			if (set.size() > 0) {
				Object entry = set.iterator().next();
				if (entry instanceof TypeId) {
					@SuppressWarnings("unchecked")
					Set<TypeId> typeSet = (Set<TypeId>)set;
					object = decorateTypeSet(bundle, typeSet);
				} else if (entry instanceof Format) {
					@SuppressWarnings("unchecked")
					Set<Format<?>> formatSet = (Set<Format<?>>)set;
					object = decorateFormatSet(bundle, formatSet);
				} else if (entry instanceof String) {
					@SuppressWarnings("unchecked")
					Set<String> stringSet = (Set<String>)set;
					object = decorateStringSet(bundle, stringSet);
				} else {
					@SuppressWarnings("unchecked")
					Set<Object> objectSet = (Set<Object>)set;
					object = decorateObjectSet(bundle, objectSet);
				}
			} else {
				object = bundle.getString("list.empty");
			}
		}
		return object;
	}

	private static String decorateType(ResourceBundle bundle, TypeId type) {
		return bundle.getString(type.name());
	}
	
	private static String decorateTypeSet(ResourceBundle bundle, Set<TypeId> types) {
		List<String> items = types.stream().map(type->decorateType(bundle, type)).collect(Collectors.toList());
		return join(bundle, items);
	}
	
	private static String decorateJsonValue(ResourceBundle bundle, JsonValue value) {
		return value.toString();
	}

	private static String decorateStringSet(ResourceBundle bundle, Set<String> values) {
		List<String> items = values.stream().map(s->'"' + s + '"').collect(Collectors.toList());
		return join(bundle, items);
	}

	private static String decorateObjectSet(ResourceBundle bundle, Set<Object> values) {
		List<String> items = values.stream().map(Object::toString).collect(Collectors.toList());
		return join(bundle, items);
	}
	
	private static String decorateFormatSet(ResourceBundle bundle, Set<Format<?>> formats) {
		Locale locale = bundle.getLocale();
		List<String> items = formats.stream().map(f->f.getLocalizedName(locale)).collect(Collectors.toList());
		return join(bundle, items);
	}
	
	private static String join(ResourceBundle bundle, List<String> items) {
		String opening = bundle.getString("list.open");
		String closing = bundle.getString("list.close");
		String separator = bundle.getString("list.separator");
		String lastSeparator = bundle.getString("list.separator.last");
		StringBuilder b = new StringBuilder(opening);
		int count = 0;
		int size = items.size();
		for (String item: items) {
			if (count == size - 1 && size > 1) {
				b.append(lastSeparator);
			} else if (count > 0) {
				b.append(separator);
			}
			b.append(item);
			count++;
		}
		return b.append(closing).toString();
	}

	private Messages() {
	}
}
