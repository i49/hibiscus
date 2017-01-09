package com.github.i49.hibiscus.formats;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * <strong>absoluteURI</strong> format which represents URI excluding relative URI.
 * <p>
 * An instance of this format can be obtained by {@link Formats#absoluteURI()} method.
 * </p>
 * 
 * @see Formats
 */
public class AbsoluteURIFormat extends URIFormat {

	/**
	 * The Singleton instance of this format.
	 */
	public static final AbsoluteURIFormat INSTANCE = new AbsoluteURIFormat();
	
	/**
	 * Constructs this format.
	 */
	private AbsoluteURIFormat() {
	}

	@Override
	public String getName() {
		return "absoluteURI";
	}

	@Override
	public boolean test(String value) {
		try {
			return new URI(value).isAbsolute();
		} catch (URISyntaxException e) {
			return false;
		}
	}
}
