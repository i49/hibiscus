package com.github.i49.hibiscus.formats;

import java.net.URI;
import java.net.URISyntaxException;

import javax.json.JsonString;

/**
 * String format which represents URIs including relative URI.
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
