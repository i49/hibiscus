package com.github.i49.hibiscus.json;

import javax.json.JsonString;

public abstract class JsonValues {

	public JsonString createString(String value) {
		return new JsonStringImpl(value);
	}
	
	private JsonValues() {
	}
}
