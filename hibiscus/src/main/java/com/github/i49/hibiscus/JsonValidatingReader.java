package com.github.i49.hibiscus;

import java.util.ArrayList;
import java.util.List;

import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonBuilderFactory;
import javax.json.JsonException;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;
import javax.json.stream.JsonLocation;
import javax.json.stream.JsonParser;

import com.github.i49.hibiscus.problems.MissingPropertyProblem;
import com.github.i49.hibiscus.problems.Problem;
import com.github.i49.hibiscus.problems.TypeMismatchProblem;
import com.github.i49.hibiscus.problems.UnknownPropertyProblem;

public class JsonValidatingReader {

	private final JsonParser parser;
	private final JsonBuilderFactory factory;
	private final List<Problem> problems = new ArrayList<>();
	
	public JsonValidatingReader(JsonParser parser, JsonBuilderFactory factory) {
		this.parser = parser;
		this.factory = factory;
	}
	
	public JsonValue readAll(ValueType rootType) {
		if (rootType instanceof AnyType) {
			rootType = null;
		}
		return readRoot(rootType);
	}
	
	public List<Problem> getProblems() {
		return problems;
	}
	
	private JsonValue readRoot(ValueType rootType) {
		if (parser.hasNext()) {
			JsonParser.Event e = parser.next();
			if (e == JsonParser.Event.START_ARRAY) {
				if (validateType(rootType, ValueType.Type.ARRAY)) {
					return readArray((ArrayType)rootType);
				} else {
					return readArray(null);
				}
			} else if (e == JsonParser.Event.START_OBJECT) {
				if (validateType(rootType, ValueType.Type.OBJECT)) {
					return readObject((ObjectType)rootType);
				} else {
					return readObject(null);
				}
			} else {
				throw new JsonException("Internal Error"); 
			}
			
		}
		return null;
	}

	private JsonArray readArray(ArrayType type) {
		JsonArrayBuilder builder = this.factory.createArrayBuilder();
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
	
	private JsonArray readArray(ValueType type) {
		if (!validateType(type, ValueType.Type.ARRAY)) {
			type = null;
		}
		return readArray((ArrayType)type);
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
		JsonObjectBuilder builder = this.factory.createObjectBuilder();
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
	
	private JsonObject readObject(ValueType type) {
		if (!validateType(type, ValueType.Type.OBJECT)) {
			type = null;
		}
		return readObject((ObjectType)type);
	}
	
	private void readProperty(ObjectType object, JsonObjectBuilder builder) {
		String key = parser.getString();
		ValueType type = validateProperty(object, key);
		JsonParser.Event e = parser.next();
		switch (e) {
		case START_ARRAY:
			builder.add(key, readArray(type));
			break;
		case START_OBJECT:
			builder.add(key, readObject(type));
			break;
		case VALUE_NUMBER:
			if (parser.isIntegralNumber()) {
				validateType(type, ValueType.Type.INTEGER);
				long value = parser.getLong();
				if (checkIfInt(value)) {
					builder.add(key, Math.toIntExact(value));
				} else {
					builder.add(key, value);
				}
			} else {
				validateType(type, ValueType.Type.NUMBER);
				builder.add(key, parser.getBigDecimal());
			}
			break;
		case VALUE_STRING:
			validateType(type, ValueType.Type.STRING);
			builder.add(key, parser.getString());
			break;
		case VALUE_TRUE:
			validateType(type, ValueType.Type.BOOLEAN);
			builder.add(key, JsonValue.TRUE);
			break;
		case VALUE_FALSE:
			validateType(type, ValueType.Type.BOOLEAN);
			builder.add(key, JsonValue.FALSE);
			break;
		case VALUE_NULL:
			validateType(type, ValueType.Type.NULL);
			builder.addNull(key);
			break;
		default:
			throw internalError();
		}
	}
	
	private boolean validateType(ValueType expected, ValueType.Type actual) {
		if (expected == null) {
			return true;
		} else if (expected.isTypeOf(actual)) {
			return true;
		} else {
			addProblem(new TypeMismatchProblem(expected.getType(), actual, getLocation()));
			return false;
		}
	}
	
	private void validateObject(ObjectType type, JsonObject object) {
		if (type == null) {
			return;
		}
		for (String key: type.getRequiredProperties()) {
			if (!object.containsKey(key)) {
				addProblem(new MissingPropertyProblem(key, getLocation()));
			}
		}
	}
	
	private ValueType validateProperty(ObjectType objectType, String propertyName) {
		if (objectType == null) {
			return null;
		}
		Property property = objectType.getProperty(propertyName);
		if (property == null) {
			if (!objectType.allowsMoreProperties()) {
				addProblem(new UnknownPropertyProblem(propertyName, getLocation()));
			}
			return null;
		}
		return property.getType();
	}

	private JsonLocation getLocation() {
		return parser.getLocation();
	}
	
	private void addProblem(Problem problem) {
		this.problems.add(problem);
	}
	
	private static IllegalStateException internalError() {
		return new IllegalStateException("Internal Error");
	}

	private static boolean checkIfInt(long value) {
		return (Integer.MIN_VALUE <= value && value <= Integer.MAX_VALUE);
	}
}
