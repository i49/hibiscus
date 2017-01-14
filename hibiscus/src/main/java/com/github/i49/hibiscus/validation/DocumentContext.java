package com.github.i49.hibiscus.validation;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

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
	public Future<JsonValue> getCurrentValueFuture() {
		if (this.rootValue != null) {
			// The root value has been determined already.
			return CompletableFuture.completedFuture(this.rootValue);
		} else {
			return new RootValueFuture();
		}
	}
	
	/**
	 * Builds the JSON document. 
	 * @return the built JSON document.
	 */
	JsonDocument getDocument() {
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
	
	private class RootValueFuture extends AbstractFuture<JsonValue> {
		@Override
		public JsonValue get() throws InterruptedException, ExecutionException {
			return rootValue;
		}
	}
}
