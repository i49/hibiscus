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
	private final Transient transientValue = new Transient();

	RootContainer(JsonBuilderFactory factory) {
		this.factory = factory;
	}
	
	@Override
	public Transient add(int value) {
		JsonNumber number = builder().add(value).build().getJsonNumber(0); 
		return transientValue.assign(number);
	}

	@Override
	public Transient add(long value) {
		JsonNumber number = builder().add(value).build().getJsonNumber(0); 
		return transientValue.assign(number);
	}

	@Override
	public Transient add(BigDecimal value) {
		JsonNumber number = builder().add(value).build().getJsonNumber(0); 
		return transientValue.assign(number);
	}

	@Override
	public Transient add(String value) {
		JsonNumber string = builder().add(value).build().getJsonNumber(0); 
		return transientValue.assign(string);
	}

	@Override
	public Transient add(JsonValue value) {
		return transientValue.assign(value);
	}
	
	public JsonValue getRootValue() {
		return transientValue.getTransientValue();
	}
	
	private JsonArrayBuilder builder() {
		return factory.createArrayBuilder();
	}
}
