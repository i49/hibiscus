package com.github.i49.hibiscus.validation;

import javax.json.JsonValue;

/**
 * A JSON document.
 */
class JsonDocument {

	private final JsonValue rootValue;
	
	/**
	 * Constructs this document.
	 * @param rootValue the value at the root of this document.
	 */
	JsonDocument(JsonValue rootValue) {
		this.rootValue = rootValue;
	}
	
	/**
	 * Returns the value at the root of this document.
	 * @return the value at the root of this document.
	 */
	JsonValue getRootValue() {
		return rootValue;
	}
}
