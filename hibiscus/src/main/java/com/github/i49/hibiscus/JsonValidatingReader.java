package com.github.i49.hibiscus;

import java.util.ArrayList;
import java.util.List;

import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonStructure;
import javax.json.JsonValue;
import javax.json.stream.JsonLocation;
import javax.json.stream.JsonParser;

import com.github.i49.hibiscus.problems.MissingPropertyProblem;
import com.github.i49.hibiscus.problems.Problem;
import com.github.i49.hibiscus.problems.TypeMismatchProblem;

public class JsonValidatingReader {

	private final JsonParser parser;
	private final JsonBuilderFactory factory;
	private final List<Problem> problems = new ArrayList<>();
	
	public JsonValidatingReader(JsonParser parser, JsonBuilderFactory factory) {
		this.parser = parser;
		this.factory = factory;
	}
	
	public JsonValue readAll(ContainerType rootType) {
		return readRoot(rootType);
	}
	
	public List<Problem> getProblems() {
		return problems;
	}
	
	private JsonStructure readRoot(ContainerType rootType) {
		if (parser.hasNext()) {
			JsonParser.Event e = parser.next();
			if (e == JsonParser.Event.START_ARRAY) {
				if (rootType instanceof ArrayType) {
					return readArray((ArrayType)rootType);
				}
			} else if (e == JsonParser.Event.START_OBJECT) {
				if (rootType instanceof ObjectType) {
					return readObject((ObjectType)rootType);
				}
			} else {
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
	
	private void readProperty(ObjectType object, JsonObjectBuilder builder) {
		String key = parser.getString();
		Property property = object.getProperty(key);
		ValueType type = property.getType();
		JsonParser.Event e = parser.next();
		switch (e) {
		case START_ARRAY:
			validateType(type, ValueType.Type.ARRAY);
			builder.add(key, readArray((ArrayType)type));
			break;
		case START_OBJECT:
			validateType(type, ValueType.Type.OBJECT);
			builder.add(key, readObject((ObjectType)type));
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
	
	private void validateType(ValueType expected, ValueType.Type actual) {
		if (!expected.isTypeOf(actual)) {
			addProblem(new TypeMismatchProblem(expected.getType(), actual, getLocation()));
		}
	}
	
	private void validateObject(ObjectType type, JsonObject object) {
		for (String key: type.getRequiredProperties()) {
			if (!object.containsKey(key)) {
				addProblem(new MissingPropertyProblem(key, getLocation()));
			}
		}
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
