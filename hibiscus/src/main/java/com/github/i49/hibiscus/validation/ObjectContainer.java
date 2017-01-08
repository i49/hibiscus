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
 * A container that corresponds to a {@JsonObject}.
 */
class ObjectContainer implements ValueContainer {

	private final TransientValueProvider valueProvider;
	private final JsonObjectBuilder builder;
	private final Transient<JsonValue> transientValue = new ObjectTransient();
	private final Transient<JsonValue> effectiveValue = new Transient<JsonValue>();
	private JsonObject object;
	private String name;
	
	ObjectContainer(TransientValueProvider valueProvider, JsonBuilderFactory builderFactory) {
		this.valueProvider = valueProvider;
		this.builder = builderFactory.createObjectBuilder();
	}
	
	void setNextName(String name) {
		this.name = name;
	}
	
	@Override
	public Transient<JsonValue> add(int value) {
		builder.add(name, value);
		JsonNumber number = valueProvider.getNumber(value);
		return transientValue.assign(number);
	}

	@Override
	public Transient<JsonValue> add(long value) {
		builder.add(name, value);
		JsonNumber number = valueProvider.getNumber(value);
		return transientValue.assign(number);
	}

	@Override
	public Transient<JsonValue> add(BigDecimal value) {
		builder.add(name, value);
		JsonNumber number = valueProvider.getNumber(value);
		return transientValue.assign(number);
	}

	@Override
	public Transient<JsonValue> add(String value) {
		builder.add(name, value);
		JsonString string = valueProvider.getString(value);
		return transientValue.assign(string);
	}

	@Override
	public Transient<JsonValue> add(JsonValue value) {
		builder.add(name, value);
		return effectiveValue.assign(value);
	}
	
	public JsonObject build() {
		this.object =  builder.build();
		return this.object;
	}
	
	/**
	 * A special {@link Transient} implementation for {@link JsonObject} members. 
	 */
	private class ObjectTransient extends Transient<JsonValue> {
		@Override
		Future<JsonValue> getFinalValue() {
			return new FutureImpl(name);
		}
	}
	
	/**
	 * A future object that will provide the final {@link JsonValue} determined by a property name.
	 */
	private class FutureImpl extends AbstractFuture<JsonValue> {

		private final String name;
		
		public FutureImpl(String name) {
			this.name = name;
		}
		
		@Override
		public JsonValue get() {
			return (JsonValue)object.get(this.name);
		}
	}
}
