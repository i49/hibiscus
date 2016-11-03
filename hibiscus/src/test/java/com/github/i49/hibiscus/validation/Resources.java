package com.github.i49.hibiscus.validation;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

public class Resources {

	public static InputStream newInputStream(String name) {
		return Resources.class.getResourceAsStream(name);
	}
	
	public static Reader newReader(String name) {
		InputStream stream = newInputStream(name);
		return new InputStreamReader(stream, StandardCharsets.UTF_8);
	}
	
	private Resources() {
	}
}
