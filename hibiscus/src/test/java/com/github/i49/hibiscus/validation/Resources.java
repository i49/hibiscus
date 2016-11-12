package com.github.i49.hibiscus.validation;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

/**
 * Utility class to handle resources contained in this package with ease.
 */
class Resources {

	/**
	 * Creates an InputStream object from specified resource.
	 * @param name name of resource.
	 * @return InputStream object.
	 */
	public static InputStream newInputStream(String name) {
		return Resources.class.getResourceAsStream(name);
	}
	
	/**
	 * Creates an Reader object from specified resource.
	 * @param name name of resource.
	 * @return Reader object.
	 */
	public static Reader newReader(String name) {
		InputStream stream = newInputStream(name);
		return new InputStreamReader(stream, StandardCharsets.UTF_8);
	}
	
	private Resources() {
	}
}
