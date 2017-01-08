package com.github.i49.hibiscus.validation;

import java.math.BigDecimal;

import javax.json.JsonArrayBuilder;
import javax.json.JsonBuilderFactory;
import javax.json.JsonNumber;
import javax.json.JsonValue;

/**
 * A container that corresponds to a whole JSON document.
 */
class RootContainer implements ValueContainer {
	
	private final JsonBuilderFactory factory;
	private final Transient<JsonValue> transientValue = new Transient<JsonValue>();

	RootContainer(JsonBuilderFactory factory) {
		this.factory = factory;
	}
	
	@Override
	public Transient<JsonValue> add(int value) {
		JsonNumber number = builder().add(value).build().getJsonNumber(0); 
		return transientValue.assign(number);
	}

	@Override
	public Transient<JsonValue> add(long value) {
		JsonNumber number = builder().add(value).build().getJsonNumber(0); 
		return transientValue.assign(number);
	}

	@Override
	public Transient<JsonValue> add(BigDecimal value) {
		JsonNumber number = builder().add(value).build().getJsonNumber(0); 
		return transientValue.assign(number);
	}

	@Override
	public Transient<JsonValue> add(String value) {
		JsonNumber string = builder().add(value).build().getJsonNumber(0); 
		return transientValue.assign(string);
	}

	@Override
	public Transient<JsonValue> add(JsonValue value) {
		return transientValue.assign(value);
	}
	
	/**
	 * Returns the value at the root of the JSON document.
	 * @return the value at the root of the JSON document.
	 */
	public JsonValue getRootValue() {
		return transientValue.get();
	}
	
	private JsonArrayBuilder builder() {
		return factory.createArrayBuilder();
	}
}
