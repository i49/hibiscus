package com.github.i49.hibiscus.formats;

import java.net.URI;
import java.net.URISyntaxException;

import javax.json.JsonString;

/**
 * <strong>anyURI</strong> format which represents URI including relative URI.
 * <p>
 * An instance of this format can be obtained by {@link Formats#anyURI()} method.
 * </p>
 */
public class URIFormat extends AbstractFormat<JsonString> implements StringFormat {

	/**
	 * The Singleton instance this format.
	 */
	public static final URIFormat INSTANCE = new URIFormat();

	/**
	 * Constructs this format.
	 */
	protected URIFormat() {
	}

	@Override
	public String getName() {
		return "anyURI";
	}

	@Override
	public boolean matches(JsonString jsonValue) {
		try {
			new URI(jsonValue.getString());
			return true;
		} catch (URISyntaxException e) {
			return false;
		}
	}
}
