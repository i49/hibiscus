package com.github.i49.hibiscus.validation;

import java.util.concurrent.Future;

import javax.json.JsonValue;

/**
 * A transient {@link JsonValue} which is only valid before the next value is fetched from the JSON document.
 */
class Transient<V extends JsonValue> {

	private V value;

	/**
	 * Assigns a transient value to this object.
	 * @param value the value to be assigned to this object. 
	 * @return this object.
	 */
	Transient<V> assign(V value) {
		this.value = value;
		return this;
	}

	/**
	 * Returns the transient {@link JsonValue} which is only valid before the next value is fetched from the JSON document.
	 * @return the transient {@link JsonValue}.
	 */
	V get() {
		return value;
	}
	
	/**
	 * Returns a future object that will provide the final {@link JsonValue} after the parsing is completed.
	 * @return a future object to retrieve the final {@link JsonValue}.
	 */
	Future<V> getFinalValue() {
		return new Immediate<V>(this.value);
	}
}
