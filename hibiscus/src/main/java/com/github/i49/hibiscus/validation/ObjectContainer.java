package com.github.i49.hibiscus.validation;

import java.math.BigDecimal;
import java.util.function.Supplier;

import javax.json.JsonBuilderFactory;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonString;
import javax.json.JsonValue;

import com.github.i49.hibiscus.json.JsonValueFactory;

/**
 * A container that corresponds to a {@JsonObject}.
 */
class ObjectContainer implements ValueContainer {

	private final JsonValueFactory factory;
	private final JsonObjectBuilder builder;
	private final Transient transientValue = new ObjectTransient();
	private final Transient effectiveValue = new Transient();
	private JsonObject object;
	private String name;
	
	ObjectContainer(JsonValueFactory factory, JsonBuilderFactory builderFactory) {
		this.factory = factory;
		this.builder = builderFactory.createObjectBuilder();
	}
	
	void setNextName(String name) {
		this.name = name;
	}
	
	@Override
	public Transient add(int value) {
		builder.add(name, value);
		JsonNumber number = factory.createNumber(value);
		return transientValue.assign(number);
	}

	@Override
	public Transient add(long value) {
		builder.add(name, value);
		JsonNumber number = factory.createNumber(value);
		return transientValue.assign(number);
	}

	@Override
	public Transient add(BigDecimal value) {
		builder.add(name, value);
		JsonNumber number = factory.createNumber(value);
		return transientValue.assign(number);
	}

	@Override
	public Transient add(String value) {
		builder.add(name, value);
		JsonString string = factory.createString(value);
		return transientValue.assign(string);
	}

	@Override
	public Transient add(JsonValue value) {
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
	private class ObjectTransient extends Transient {
		@Override
		Supplier<JsonValue> getFinalValue() {
			return new ObjectMemberSupplier(name);
		}
	}
	
	/**
	 * A supplier that will supply the final {@link JsonValue} determined by a property name.
	 */
	private class ObjectMemberSupplier implements Supplier<JsonValue> {

		private final String name;
		
		public ObjectMemberSupplier(String name) {
			this.name = name;
		}
		
		@Override
		public JsonValue get() {
			if (object == null) {
				return null;
			}
			return (JsonValue)object.get(this.name);
		}
	}
}
