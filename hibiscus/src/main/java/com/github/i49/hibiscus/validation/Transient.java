package com.github.i49.hibiscus.validation;

import java.util.function.Supplier;

import javax.json.JsonValue;

/**
 * A transient {@link JsonValue}.
 */
class Transient {

	private JsonValue value;

	/**
	 * Assigns a transient value to this object.
	 * @param value the value to be assigned to this object. 
	 * @return this object.
	 */
	Transient assign(JsonValue value) {
		this.value = value;
		return this;
	}

	/**
	 * Returns the transient {@link JsonValue} that can be effective until next {@link #assign(JsonValue)} is called.
	 * @return the transient {@link JsonValue}.
	 */
	JsonValue getTransientValue() {
		return value;
	}
	
	/**
	 * Returns a supplier that will supply the final {@link JsonValue}.
	 * @return a final {@link JsonValue} supplier.
	 */
	Supplier<JsonValue> getFinalValue() {
		return new DirectValueSupplier(this.value);
	}
	
	/**
	 * A supplier that supply the cached value.
	 */
	private static class DirectValueSupplier implements Supplier<JsonValue> {

		private final JsonValue value;
		
		/**
		 * Constructs this supplier.
		 * @param value the value to be cached.
		 */
		public DirectValueSupplier(JsonValue value) {
			this.value = value;
		}
		
		@Override
		public JsonValue get() {
			return value;
		}
	}
}
