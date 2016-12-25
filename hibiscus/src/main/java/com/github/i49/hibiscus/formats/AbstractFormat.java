package com.github.i49.hibiscus.formats;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.json.JsonValue;

/**
 * A skeletal class to help implementing {@link Format} interface.
 *
 * @param <V> the type of {@link JsonValue} to be validated against this format.
 * 
 * @see Format
 */
abstract class AbstractFormat<V extends JsonValue> implements Format<V> {

	// The base name of resource bundle from which messages will be obtained.
	private static final String BUNDLE_BASE_NAME = AbstractFormat.class.getPackage().getName() + ".messages";
	
	@Override
	public String getLocalizedName(Locale locale) {
		String name = getName();
		if (name == null) {
			return null;
		}
		ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_BASE_NAME, locale);
		return bundle.getString(name);
	}
}
