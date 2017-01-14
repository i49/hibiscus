package com.github.i49.hibiscus.validation;

import java.math.BigDecimal;

import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonBuilderFactory;
import javax.json.JsonNumber;
import javax.json.JsonString;
import javax.json.JsonValue;

import com.github.i49.hibiscus.common.JsonPointer;

/**
 * A context class which will be created per an {@link JsonArray} while validating JSON documents.
 */
class ArrayContext extends AbstractJsonContext {

	private final TransientValueProvider valueProvider;
	private JsonArrayBuilder builder;
	private int currentIndex;
	private JsonArray result;
	
	/**
	 * Constructs this context.
	 * @param valueProvider the transient {@link JsonValue} provider.
	 * @param factory the factory to be used to build {@link JsonArray}.
	 */
	ArrayContext(TransientValueProvider valueProvider, JsonBuilderFactory factory) {
		this.valueProvider = valueProvider;
		this.builder = factory.createArrayBuilder();
		this.currentIndex = -1;
	}
	
	/**
	 * Moves to the next item in this array.
	 */
	void nextItem() {
		this.currentIndex++;
	}
	
	@Override
	public JsonNumber add(int value) {
		builder.add(value);
		return valueProvider.getNumber(value);
	}

	@Override
	public JsonNumber add(long value) {
		builder.add(value);
		return valueProvider.getNumber(value);
	}

	@Override
	public JsonNumber add(BigDecimal value) {
		builder.add(value);
		return valueProvider.getNumber(value);
	}

	@Override
	public JsonString add(String value) {
		builder.add(value);
		return valueProvider.getString(value);
	}

	@Override
	public JsonValue add(JsonValue value) {
		builder.add(value);
		return value;
	}

	/**
	 * Builds the {@link JsonArray} which is composed of all added elements.
	 * @return the built {@link JsonArray}.
	 */
	JsonArray getArray() {
		this.result = this.builder.build();
		this.builder = null;
		return this.result;
	}

	@Override
	public void buildCurrentPointer(JsonPointer.Builder builder) {
		super.buildCurrentPointer(builder);
		builder.append(this.currentIndex);
	}
}
