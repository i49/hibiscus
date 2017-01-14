package com.github.i49.hibiscus.validation;

import java.math.BigDecimal;

import javax.json.JsonArrayBuilder;
import javax.json.JsonBuilderFactory;
import javax.json.JsonNumber;
import javax.json.JsonString;
import javax.json.JsonValue;

/**
 * A context class which will be created per a JSON document.
 */
class DocumentContext extends AbstractJsonContext {
	
	private final JsonBuilderFactory factory;
	private JsonValue rootValue;

	/**
	 * Constructs this context.
	 * @param factory the factory to be used to build the JSON document.
	 */
	DocumentContext(JsonBuilderFactory factory) {
		this.factory = factory;
	}
	
	@Override
	public JsonNumber add(int value) {
		JsonNumber number = builder().add(value).build().getJsonNumber(0);
		setRootValue(number);
		return number;
	}

	@Override
	public JsonNumber add(long value) {
		JsonNumber number = builder().add(value).build().getJsonNumber(0); 
		setRootValue(number);
		return number;
	}

	@Override
	public JsonNumber add(BigDecimal value) {
		JsonNumber number = builder().add(value).build().getJsonNumber(0); 
		setRootValue(number);
		return number;
	}

	@Override
	public JsonString add(String value) {
		JsonString string = builder().add(value).build().getJsonString(0); 
		setRootValue(string);
		return string;
	}

	@Override
	public JsonValue add(JsonValue value) {
		setRootValue(value);
		return value;
	}
	
	/**
	 * Return the value at the root of the JSON document.
	 * @return the root value.
	 */
	public JsonValue getRootValue() {
		return rootValue;
	}

	private JsonArrayBuilder builder() {
		return factory.createArrayBuilder();
	}
	
	private void setRootValue(JsonValue rootValue) {
		this.rootValue = rootValue;
	}
}
