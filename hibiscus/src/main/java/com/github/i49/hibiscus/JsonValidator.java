package com.github.i49.hibiscus;

import java.io.InputStream;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonStructure;
import javax.json.JsonValue;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParserFactory;

public class JsonValidator {

	private final ContainerType rootType;
	
	private final JsonParserFactory parserFactory;
	private final JsonBuilderFactory builderFactory;
	private JsonParser parser; 
	
	public JsonValidator(ContainerType rootType) {
		this.rootType = rootType;
		this.parserFactory = createParserFactory();
		this.builderFactory = createBuilderFactory();
	}
	
	public Type getRootType() {
		return rootType;
	}
	
	public JsonStructure validate(Reader reader) throws ValidationException {
		this.parser = this.parserFactory.createParser(reader);
		JsonStructure root = readRoot(getRootType());
		return root;
	}
	
	public JsonStructure validate(InputStream stream, Charset charset) throws ValidationException {
		this.parser = this.parserFactory.createParser(stream,  charset);
		JsonStructure root = readRoot(getRootType());
		return root;
	}
	
	private JsonStructure readRoot(Type type) {
		if (parser.hasNext()) {
			JsonParser.Event e = parser.next();
			if (e == JsonParser.Event.START_ARRAY) {
				if (type instanceof ArrayType) {
					return readArray((ArrayType)type);
				}
			} else if (e == JsonParser.Event.START_OBJECT) {
				if (type instanceof ObjectType) {
					return readObject((ObjectType)type);
				}
			} else {
			}
		}
		return null;
	}
	
	private JsonArray readArray(ArrayType type) {
		JsonArrayBuilder builder = this.builderFactory.createArrayBuilder();
		while (parser.hasNext()) {
			JsonParser.Event e = parser.next();
			if (e == JsonParser.Event.END_ARRAY) {
				JsonArray array = builder.build();
				return array;
			} else {
				readItem(type, builder);
			}
		}
		throw internalError();
	}
	
	private void readItem(ArrayType type, JsonArrayBuilder builder) {
		JsonParser.Event e = parser.next();
		switch (e) {
		case START_ARRAY:
			break;
		case START_OBJECT:
			break;
		case VALUE_NUMBER:
			if (parser.isIntegralNumber()) {
				long value = parser.getLong();
				if (checkIfInt(value)) {
					builder.add(Math.toIntExact(value));
				} else {
					builder.add(value);
				}
			} else {
				builder.add(parser.getBigDecimal());
			}
			break;
		case VALUE_STRING:
			builder.add(parser.getString());
			break;
		case VALUE_TRUE:
			builder.add(JsonValue.TRUE);
			break;
		case VALUE_FALSE:
			builder.add(JsonValue.FALSE);
			break;
		case VALUE_NULL:
			builder.addNull();
			break;
		default:
			throw internalError();
		}
	}
	
	private JsonObject readObject(ObjectType type) {
		JsonObjectBuilder builder = this.builderFactory.createObjectBuilder();
		while (parser.hasNext()) {
			JsonParser.Event e = parser.next();
			if (e == JsonParser.Event.END_OBJECT) {
				JsonObject object = builder.build();
				validateObject(type, object);
				return object;
			} else if (e == JsonParser.Event.KEY_NAME) {
				readProperty(type, builder);
			} else {
				throw internalError();
			}
		}
		throw internalError();
	}
	
	private void readProperty(ObjectType object, JsonObjectBuilder builder) {
		String key = parser.getString();
		Property property = object.getProperty(key);
		Type type = property.getType();
		JsonParser.Event e = parser.next();
		switch (e) {
		case START_ARRAY:
			builder.add(key, readArray((ArrayType)type));
			break;
		case START_OBJECT:
			builder.add(key, readObject((ObjectType)type));
			break;
		case VALUE_NUMBER:
			if (parser.isIntegralNumber()) {
				long value = parser.getLong();
				if (checkIfInt(value)) {
					builder.add(key, Math.toIntExact(value));
				} else {
					builder.add(key, value);
				}
			} else {
				builder.add(key, parser.getBigDecimal());
			}
			break;
		case VALUE_STRING:
			builder.add(key, parser.getString());
			break;
		case VALUE_TRUE:
			builder.add(key, JsonValue.TRUE);
			break;
		case VALUE_FALSE:
			builder.add(key, JsonValue.FALSE);
			break;
		case VALUE_NULL:
			builder.addNull(key);
			break;
		default:
			throw internalError();
		}
	}
	
	private void validateObject(ObjectType type, JsonObject object) {
		for (String key: type.getRequiredProperties()) {
			if (!object.containsKey(key)) {
				
			}
		}
	}
	
	private static IllegalStateException internalError() {
		return new IllegalStateException("Internal Error");
	}

	private static boolean checkIfInt(long value) {
		return (Integer.MIN_VALUE <= value && value <= Integer.MAX_VALUE);
	}
	
	private static JsonParserFactory createParserFactory() {
		Map<String, ?> config = new HashMap<>();
		return Json.createParserFactory(config);
	}
	
	private static JsonBuilderFactory createBuilderFactory() {
		Map<String, ?> config = new HashMap<>();
		return Json.createBuilderFactory(config);
	}
}
