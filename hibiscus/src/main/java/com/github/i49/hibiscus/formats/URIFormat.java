package com.github.i49.hibiscus.formats;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * <strong>anyURI</strong> format which represents URI including relative URI.
 * <p>
 * An instance of this format can be obtained by {@link Formats#anyURI()} method.
 * </p>
 */
public class URIFormat extends StringFormat {

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
	public boolean test(String value) {
		try {
			new URI(value);
			return true;
		} catch (URISyntaxException e) {
			return false;
		}
	}
}
