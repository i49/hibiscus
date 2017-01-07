package com.github.i49.hibiscus.validation;

import java.math.BigDecimal;
import java.util.function.Supplier;

import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonBuilderFactory;
import javax.json.JsonNumber;
import javax.json.JsonString;
import javax.json.JsonValue;

import com.github.i49.hibiscus.json.JsonValueFactory;

/**
 * A container that corresponds to a {@JsonArray}.
 */
class ArrayContainer implements ValueContainer {

	private final JsonValueFactory factory;
	private final JsonArrayBuilder builder;
	private final Transient transientValue = new ArrayTransient();
	private final Transient effectiveValue = new Transient();
	private JsonArray array;
	private int index;
	
	ArrayContainer(JsonValueFactory factory, JsonBuilderFactory builderFactory) {
		this.factory = factory;
		this.builder = builderFactory.createArrayBuilder();
	}
	
	void setNextIndex(int index) {
		this.index = index;
	}

	@Override
	public Transient add(int value) {
		builder.add(value);
		JsonNumber number = factory.createNumber(value);
		return transientValue.assign(number);
	}

	@Override
	public Transient add(long value) {
		builder.add(value);
		JsonNumber number = factory.createNumber(value);
		return transientValue.assign(number);
	}

	@Override
	public Transient add(BigDecimal value) {
		builder.add(value);
		JsonNumber number = factory.createNumber(value);
		return transientValue.assign(number);
	}

	@Override
	public Transient add(String value) {
		builder.add(value);
		JsonString string = factory.createString(value);
		return transientValue.assign(string);
	}

	@Override
	public Transient add(JsonValue value) {
		builder.add(value);
		return effectiveValue.assign(value);
	}
	
	JsonArray build() {
		this.array = builder.build();
		return this.array;
	}
	
	/**
	 * A special {@link Transient} implementation for {@link JsonArray} elements. 
	 */
	private class ArrayTransient extends Transient {
		@Override
		Supplier<JsonValue> getFinalValue() {
			return new ArrayElementSupplier(index);
		}
	}

	/**
	 * A supplier that will supply the final {@link JsonValue} determined by a element index.
	 */
	private class ArrayElementSupplier implements Supplier<JsonValue> {

		private final int index;
		
		public ArrayElementSupplier(int index) {
			this.index = index;
		}

		@Override
		public JsonValue get() {
			if (array == null) {
				return null;
			}
			return array.get(this.index);
		}
	}
}
