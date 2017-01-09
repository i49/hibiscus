package com.github.i49.hibiscus.validation;

import java.math.BigDecimal;
import java.util.concurrent.Future;

import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonBuilderFactory;
import javax.json.JsonNumber;
import javax.json.JsonString;
import javax.json.JsonValue;

/**
 * A builder class to build a {@link JsonArray}.
 */
class ArrayBuilder implements JsonBuilder {

	private final TransientValueProvider valueProvider;
	private JsonArrayBuilder builder;
	private int lastIndex;
	private JsonArray result;
	
	ArrayBuilder(TransientValueProvider valueProvider, JsonBuilderFactory builderFactory) {
		this.valueProvider = valueProvider;
		this.builder = builderFactory.createArrayBuilder();
		this.lastIndex = -1;
	}
	
	@Override
	public JsonNumber add(int value) {
		this.lastIndex++;
		builder.add(value);
		return valueProvider.getNumber(value);
	}

	@Override
	public JsonNumber add(long value) {
		this.lastIndex++;
		builder.add(value);
		return valueProvider.getNumber(value);
	}

	@Override
	public JsonNumber add(BigDecimal value) {
		this.lastIndex++;
		builder.add(value);
		return valueProvider.getNumber(value);
	}

	@Override
	public JsonString add(String value) {
		this.lastIndex++;
		builder.add(value);
		return valueProvider.getString(value);
	}

	@Override
	public JsonValue add(JsonValue value) {
		this.lastIndex++;
		builder.add(value);
		return value;
	}

	@Override
	public Future<JsonValue> getFutureOf(JsonValue value) {
		return new ArrayItemFuture(this.lastIndex);
	}
	
	/**
	 * Builds the {@link JsonArray} which is composed of all added elements.
	 * @return the built {@link JsonArray}.
	 */
	public JsonArray build() {
		this.result = this.builder.build();
		this.builder = null;
		return this.result;
	}
	
	/**
	 * A future object that will provide the final {@link JsonValue} determined by the index of the item.
	 */
	private class ArrayItemFuture extends AbstractFuture<JsonValue> {

		private final int index;
		
		public ArrayItemFuture(int index) {
			this.index = index;
		}

		@Override
		public JsonValue get() {
			return result.get(this.index);
		}
	}
}
