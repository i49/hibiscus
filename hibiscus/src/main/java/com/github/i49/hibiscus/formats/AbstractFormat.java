package com.github.i49.hibiscus.formats;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.json.JsonValue;

/**
 * Skeletal class to implement {@code Format}.
 *
 * @param <V> the type of JSON value.
 */
abstract class AbstractFormat<V extends JsonValue> implements Format<V> {

	private static final String BUNDLE_BASE_NAME = AbstractFormat.class.getPackage().getName() + ".messages";
	
	@Override
	public String getLocalizedString(Locale locale) {
		String name = getName();
		if (name == null) {
			return null;
		}
		ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_BASE_NAME, locale);
		return bundle.getString(name);
	}
}
