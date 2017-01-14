package com.github.i49.hibiscus.common;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;

/**
 * A JSON document.
 */
public class JsonDocument {

	private JsonValue rootValue;
	
	/**
	 * Constructs empty document.
	 */
	public JsonDocument() {
	}
	
	/**
	 * Returns the {@link JsonValue} at the root of this document.
	 * @return the value at the root of this document.
	 */
	public JsonValue getRootValue() {
		return rootValue;
	}
	
	/**
	 * Assigns the {@link JsonValue} at the root of this document.
	 * @param rootValue the value at the root of this document.
	 */
	public void setRootValue(JsonValue rootValue) {
		this.rootValue = rootValue;
	}
	
	/**
	 * Looks up a {@link JsonValue} which is referenced by a given JSON pointer.
	 * @param pointer the JSON pointer which refers to a {@link JsonValue}.
	 * @return the {@link JsonValue} if found, {@code null} otherwise. 
	 * @exception IllegalArgumentException if specified pointer is {@code null}.
	 */
	public JsonValue getValueByPointer(JsonPointer pointer) {
		if (pointer == null) {
			throw new IllegalArgumentException();
		}
		JsonValue current = getRootValue();
		for (Object token: pointer) {
			if (current == null) {
				break;
			}
			JsonValue.ValueType type = current.getValueType();
			if (type == JsonValue.ValueType.ARRAY) {
				if ((token instanceof Integer)) {
					int index = ((Integer)token).intValue();
					current = ((JsonArray)current).get(index);
				} else {
					current = null;
				}
			} else if (type == JsonValue.ValueType.OBJECT) {
				current = ((JsonObject)current).get(token);
			} else {
				break;
			}
		}
		return current;
	}
}
