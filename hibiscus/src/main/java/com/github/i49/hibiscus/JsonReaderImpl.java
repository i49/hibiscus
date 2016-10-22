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
import javax.json.JsonException;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonStructure;
import javax.json.JsonValue;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParserFactory;

public class JsonReaderImpl implements JsonReader {

	private final JsonParser parser;
	private final JsonBuilderFactory builderFactory;
	private boolean readDone;
	
	public JsonReaderImpl(Reader reader) {
		parser = createParserFactory().createParser(reader);
		builderFactory = createBuilderFactory();
	}
	
	public JsonReaderImpl(InputStream stream) {
		parser = createParserFactory().createParser(stream);
		builderFactory = createBuilderFactory();
	}

	public JsonReaderImpl(InputStream stream, Charset charset) {
		parser = createParserFactory().createParser(stream, charset);
		builderFactory = createBuilderFactory();
	}

	@Override
	public void close() {
		readDone = true;
		parser.close();
	}

	@Override
	public JsonStructure read() {
		checkDone();
		if (parser.hasNext()) {
			JsonParser.Event e = parser.next();
			if (e == JsonParser.Event.START_ARRAY) {
				return getArray();
			} else if (e == JsonParser.Event.START_OBJECT) {
				return getObject();
			}
		}
		throw new JsonException("Internal Error");
	}

	@Override
	public JsonArray readArray() {
		checkDone();
		if (parser.hasNext()) {
			JsonParser.Event e = parser.next();
			if (e == JsonParser.Event.START_ARRAY) {
				return getArray();
			}
		}
		throw new JsonException("Internal Error");
	}

	@Override
	public JsonObject readObject() {
		checkDone();
		if (parser.hasNext()) {
			JsonParser.Event e = parser.next();
			if (e == JsonParser.Event.START_OBJECT) {
				return getObject();
			}
		}
		throw new JsonException("Internal Error");
	}

	private JsonArray getArray() {
		JsonArrayBuilder builder = builderFactory.createArrayBuilder();
		while (parser.hasNext()) {
			JsonParser.Event e = parser.next();
			if (e == JsonParser.Event.END_ARRAY) {
				return builder.build();
			} else {
				addArrayItem(builder, e);
			}
		}
		return null;
	}
	
	private void addArrayItem(JsonArrayBuilder builder, JsonParser.Event e) {
		switch (e) {
		case START_ARRAY:
			builder.add(getArray());
			break;
		case START_OBJECT:
			builder.add(getObject());
			break;
		case VALUE_STRING:
			builder.add(parser.getString());
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
			throw new IllegalStateException();
		}
	}

	private JsonObject getObject() {
		JsonObjectBuilder builder = builderFactory.createObjectBuilder();
		while (parser.hasNext()) {
			JsonParser.Event e = parser.next();
			if (e == JsonParser.Event.END_OBJECT) {
				return builder.build();
			} else if (e == JsonParser.Event.KEY_NAME) {
				String key = parser.getString();
				addObjectProperty(builder, key);
			} else {
				throw new IllegalStateException();
			}
		}
		return null;
	}

	private void addObjectProperty(JsonObjectBuilder builder, String key) {
		JsonParser.Event e = parser.next();
		switch (e) {
		case START_ARRAY:
			builder.add(key, getArray());
			break;
		case START_OBJECT:
			builder.add(key, getObject());
			break;
		case VALUE_STRING:
			builder.add(key, parser.getString());
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
			throw new IllegalStateException();
		}
	}

	private void checkDone() {
		if (readDone) {
			throw new IllegalStateException("Already Read");
		}
		readDone = true;
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
