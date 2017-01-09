package com.github.i49.hibiscus.validation;

import java.math.BigDecimal;
import java.util.concurrent.Future;

import javax.json.JsonBuilderFactory;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonString;
import javax.json.JsonValue;

/**
 * A builder class to build a {@link JsonObject}.
 */
class ObjectBuilder implements JsonBuilder {

	private final TransientValueProvider valueProvider;
	private JsonObjectBuilder builder;
	private String currentName;
	private JsonObject result;
	
	ObjectBuilder(TransientValueProvider valueProvider, JsonBuilderFactory builderFactory) {
		this.valueProvider = valueProvider;
		this.builder = builderFactory.createObjectBuilder();
	}
	
	void setNextName(String name) {
		this.currentName = name;
	}
	
	@Override
	public JsonNumber add(int value) {
		builder.add(currentName, value);
		return valueProvider.getNumber(value);
	}

	@Override
	public JsonNumber add(long value) {
		builder.add(currentName, value);
		return valueProvider.getNumber(value);
	}

	@Override
	public JsonNumber add(BigDecimal value) {
		builder.add(currentName, value);
		return valueProvider.getNumber(value);
	}

	@Override
	public JsonString add(String value) {
		builder.add(currentName, value);
		return valueProvider.getString(value);
	}

	@Override
	public JsonValue add(JsonValue value) {
		builder.add(currentName, value);
		return value;
	}
	
	@Override
	public Future<JsonValue> getFutureOf(JsonValue value) {
		return new PropertyValueFuture(this.currentName);
	}

	/**
	 * Builds the {@link JsonObject} which is composed of all added properties. 
	 * @return the built {@link JsonObject}.
	 */
	public JsonObject build() {
		this.result =  this.builder.build();
		this.builder = null;
		return this.result;
	}
	
	/**
	 * A future object that will provide the final {@link JsonValue} determined by a property name.
	 */
	private class PropertyValueFuture extends AbstractFuture<JsonValue> {

		private final String name;
		
		public PropertyValueFuture(String name) {
			this.name = name;
		}
		
		@Override
		public JsonValue get() {
			return (JsonValue)result.get(this.name);
		}
	}
}
