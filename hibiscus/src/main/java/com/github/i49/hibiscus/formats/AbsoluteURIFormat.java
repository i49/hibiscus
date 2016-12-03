package com.github.i49.hibiscus.formats;

import java.net.URI;
import java.net.URISyntaxException;

import javax.json.JsonString;

/**
 * String format which represents absolute URIs.
 */
public class AbsoluteURIFormat extends URIFormat {

	public static final AbsoluteURIFormat INSTANCE = new AbsoluteURIFormat();
	
	AbsoluteURIFormat() {}

	@Override
	public String getName() {
		return "absoluteURI";
	}

	@Override
	public boolean matches(JsonString value) {
		try {
			return new URI(value.getString()).isAbsolute();
		} catch (URISyntaxException e) {
			return false;
		}
	}
}
