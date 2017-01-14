package com.github.i49.hibiscus.validation;

import java.math.BigDecimal;
import java.util.concurrent.Future;

import javax.json.JsonBuilderFactory;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonString;
import javax.json.JsonValue;

import com.github.i49.hibiscus.common.JsonPointer;

/**
 * A context class which will be created per an {@link JsonObject} while validating JSON documents.
 */
class ObjectContext extends AbstractJsonContext {

	private final TransientValueProvider valueProvider;
	private JsonObjectBuilder builder;
	private String currentName;
	private JsonObject result;
	
	/**
	 * Constructs this context.
	 * @param valueProvider the transient {@link JsonValue} provider.
	 * @param factory the factory to be used to build {@link JsonObject}.
	 */
	ObjectContext(TransientValueProvider valueProvider, JsonBuilderFactory factory) {
		this.valueProvider = valueProvider;
		this.builder = factory.createObjectBuilder();
	}
	
	/**
	 * Moves to the next property in this object.
	 * @param name the name of the next property.
	 */
	void nextName(String name) {
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
	public Future<JsonValue> getCurrentValueFuture() {
		return new PropertyValueFuture(this.currentName);
	}
	
	/**
	 * Builds the {@link JsonObject} which is composed of all added properties. 
	 * @return the built {@link JsonObject}.
	 */
	JsonObject getObject() {
		this.result =  this.builder.build();
		this.builder = null;
		return this.result;
	}

	@Override
	public void buildCurrentPointer(JsonPointer.Builder builder) {
		super.buildCurrentPointer(builder);
		builder.append(this.currentName);
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
