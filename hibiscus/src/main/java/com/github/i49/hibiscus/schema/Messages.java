package com.github.i49.hibiscus.schema;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import com.github.i49.hibiscus.common.TypeId;

class Messages {

	private static final String BASE_BUNDLE_NAME = Messages.class.getPackage().getName() + ".messages";
	private static final ResourceBundle bundle = ResourceBundle.getBundle(BASE_BUNDLE_NAME);
	
	static String PROPERTY_NAME_IS_NULL() {
		return localize("PROPERTY_NAME_IS_NULL");
	}
	
	static String ONE_OF_TYPES_IS_NULL(int index) {
		return localize("ONE_OF_TYPES_IS_NULL", index);
	}
	
	static String ONE_OF_TYPES_IS_DUPLICATED(int index, TypeId typeId) {
		return localize("ONE_OF_TYPES_IS_DUPLICATED", index, typeId);
	}

	private static String localize(String key, Object... arguments) {
		String pattern = bundle.getString(key);
		return MessageFormat.format(pattern, arguments);
	}
}
