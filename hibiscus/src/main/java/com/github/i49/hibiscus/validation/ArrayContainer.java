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
 * A container that corresponds to a {@JsonArray}.
 */
class ArrayContainer implements ValueContainer {

	private final TransientValueProvider valueProvider;
	private JsonArrayBuilder builder;
	private final Transient<JsonValue> transientValue = new ArrayTransient();
	private final Transient<JsonValue> effectiveValue = new Transient<JsonValue>();
	private JsonArray array;
	private int lastIndex;
	
	ArrayContainer(TransientValueProvider valueProvider, JsonBuilderFactory builderFactory) {
		this.valueProvider = valueProvider;
		this.builder = builderFactory.createArrayBuilder();
		this.lastIndex = -1;
	}
	
	@Override
	public Transient<JsonValue> add(int value) {
		builder.add(value);
		JsonNumber number = valueProvider.getNumber(value);
		this.lastIndex++;
		return transientValue.assign(number);
	}

	@Override
	public Transient<JsonValue> add(long value) {
		builder.add(value);
		JsonNumber number = valueProvider.getNumber(value);
		this.lastIndex++;
		return transientValue.assign(number);
	}

	@Override
	public Transient<JsonValue> add(BigDecimal value) {
		builder.add(value);
		JsonNumber number = valueProvider.getNumber(value);
		this.lastIndex++;
		return transientValue.assign(number);
	}

	@Override
	public Transient<JsonValue> add(String value) {
		builder.add(value);
		JsonString string = valueProvider.getString(value);
		this.lastIndex++;
		return transientValue.assign(string);
	}

	@Override
	public Transient<JsonValue> add(JsonValue value) {
		builder.add(value);
		this.lastIndex++;
		return effectiveValue.assign(value);
	}
	
	JsonArray build() {
		this.array = this.builder.build();
		this.builder = null;
		return this.array;
	}
	
	/**
	 * A special {@link Transient} implementation for {@link JsonArray} elements. 
	 */
	private class ArrayTransient extends Transient<JsonValue> {
		@Override
		Future<JsonValue> getFinalValue() {
			return new FutureImpl(lastIndex);
		}
	}

	/**
	 * A future object that will provide the final {@link JsonValue} determined by a element index.
	 */
	private class FutureImpl extends AbstractFuture<JsonValue> {

		private final int index;
		
		public FutureImpl(int index) {
			this.index = index;
		}

		@Override
		public JsonValue get() {
			return array.get(this.index);
		}
	}
}
