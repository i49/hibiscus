package com.github.i49.hibiscus.validation;

import java.math.BigDecimal;

import javax.json.JsonArrayBuilder;
import javax.json.JsonBuilderFactory;
import javax.json.JsonNumber;
import javax.json.JsonString;
import javax.json.JsonValue;

import com.github.i49.hibiscus.common.JsonDocument;

/**
 * A context class which will be created per a JSON document.
 */
class DocumentContext extends AbstractJsonContext {
	
	private final JsonDocument document;
	private final JsonBuilderFactory factory;

	/**
	 * Constructs this context.
	 * @param document the JSON document to be built.
	 * @param factory the factory to be used to build the JSON document.
	 */
	DocumentContext(JsonDocument document, JsonBuilderFactory factory) {
		this.document = document;
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

	private JsonArrayBuilder builder() {
		return factory.createArrayBuilder();
	}
	
	private void setRootValue(JsonValue value) {
		this.document.setRootValue(value);
	}
}
