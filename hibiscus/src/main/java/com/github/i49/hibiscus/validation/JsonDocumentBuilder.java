package com.github.i49.hibiscus.validation;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import javax.json.JsonArrayBuilder;
import javax.json.JsonBuilderFactory;
import javax.json.JsonNumber;
import javax.json.JsonString;
import javax.json.JsonValue;

/**
 * A builder class to build a JSON document.
 */
class JsonDocumentBuilder implements JsonBuilder {
	
	private final JsonBuilderFactory factory;
	private JsonValue rootValue;

	JsonDocumentBuilder(JsonBuilderFactory factory) {
		this.factory = factory;
	}
	
	@Override
	public JsonNumber add(int value) {
		JsonNumber number = getBuilder().add(value).build().getJsonNumber(0);
		setRootValue(number);
		return number;
	}

	@Override
	public JsonNumber add(long value) {
		JsonNumber number = getBuilder().add(value).build().getJsonNumber(0); 
		setRootValue(number);
		return number;
	}

	@Override
	public JsonNumber add(BigDecimal value) {
		JsonNumber number = getBuilder().add(value).build().getJsonNumber(0); 
		setRootValue(number);
		return number;
	}

	@Override
	public JsonString add(String value) {
		JsonString string = getBuilder().add(value).build().getJsonString(0); 
		setRootValue(string);
		return string;
	}

	@Override
	public JsonValue add(JsonValue value) {
		setRootValue(value);
		return value;
	}

	@Override
	public Future<JsonValue> getFutureOf(JsonValue value) {
		return CompletableFuture.completedFuture(value);
	}
	
	public JsonDocument build() {
		if (this.rootValue == null) {
			throw new IllegalStateException();
		}
		return new JsonDocument(this.rootValue);
	}
	
	private JsonArrayBuilder getBuilder() {
		return factory.createArrayBuilder();
	}
	
	private void setRootValue(JsonValue value) {
		if (this.rootValue == null) {
			this.rootValue = value;
		}
	}
}
