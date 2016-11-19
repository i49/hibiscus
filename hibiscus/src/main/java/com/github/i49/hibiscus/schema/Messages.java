package com.github.i49.hibiscus.schema;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import com.github.i49.hibiscus.common.TypeId;

/**
 * Messages for exceptions that will occur while building schema.
 */
class Messages {

	private static final String BASE_BUNDLE_NAME = Messages.class.getPackage().getName() + ".messages";
	private static final ResourceBundle bundle = ResourceBundle.getBundle(BASE_BUNDLE_NAME);

	static String METHOD_PARAMETER_IS_NULL(String method, String parameter) {
		return localize("METHOD_PARAMETER_IS_NULL", method, parameter);
	}
	
	static String PROPERTY_NAME_IS_NULL() {
		return localize("PROPERTY_NAME_IS_NULL");
	}
	
	static String ONE_OF_TYPES_IS_NULL(int index) {
		return localize("ONE_OF_TYPES_IS_NULL", index);
	}
	
	static String ONE_OF_TYPES_IS_DUPLICATED(int index, TypeId typeId) {
		return localize("ONE_OF_TYPES_IS_DUPLICATED", index, typeId);
	}
	
	static String ONE_OF_VALUES_IS_NULL(int index) {
		return localize("ONE_OF_VALUES_IS_NULL", index);
	}
	
	static String STRING_LENGTH_IS_NEGATIVE(int length) {
		return localize("STRING_LENGTH_IS_NEGATIVE", length);
	}

	private static String localize(String key, Object... arguments) {
		String pattern = bundle.getString(key);
		return MessageFormat.format(pattern, arguments);
	}
}
