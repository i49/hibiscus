package com.github.i49.hibiscus.formats;

import java.net.URI;
import java.net.URISyntaxException;

import javax.json.JsonString;

/**
 * String format which represents URI.
 */
public class UriFormat extends AbstractFormat<JsonString> implements StringFormat {

	/**
	 * The one and only instance of this format.
	 */
	private static final UriFormat INSTANCE = new UriFormat();

	/**
	 * Returns the Singleton instance of this format.
	 * @return the instance of this class.
	 */
	public static UriFormat getInstance() {
		return INSTANCE;
	}

	private UriFormat() {
	}

	@Override
	public String getName() {
		return "uri";
	}

	@Override
	public boolean matches(JsonString value) {
		try {
			new URI(value.getString());
			return true;
		} catch (URISyntaxException e) {
			return false;
		}
	}
}
