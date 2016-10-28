package com.github.i49.hibiscus.validation;

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

import com.github.i49.hibiscus.json.JsonValues;
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
	
	private JsonValue readRoot(ValueType type) {
		if (parser.hasNext()) {
			return readValue(type, parser.next());
		} else {
			return null;
		}
	}
	
	private JsonArray readArray(ValueType type) {
		if (!validateType(type, TypeId.ARRAY)) {
			type = null;
		}
		return readArray((ArrayType)type);
	}
	
	private JsonArray readArray(ArrayType type) {
		JsonArrayBuilder builder = this.factory.createArrayBuilder();
		while (parser.hasNext()) {
			JsonParser.Event event = parser.next();
			if (event == JsonParser.Event.END_ARRAY) {
				JsonArray array = builder.build();
				return array;
			} else {
				readItem(type, builder, event);
			}
		}
		throw internalError();
	}
	
	private void readItem(ArrayType type, JsonArrayBuilder builder, JsonParser.Event event) {
		ValueType expectedType = null;
		JsonValue value = readValue(expectedType, event);
		if (value != null) {
			builder.add(value);
		}
	}
	
	private JsonObject readObject(ValueType type) {
		if (!validateType(type, TypeId.OBJECT)) {
			type = null;
		}
		return readObject((ObjectType)type);
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
		ValueType expectedType = validateProperty(object, key);
		JsonParser.Event event = parser.next();
		JsonValue value = readValue(expectedType, event);
		if (value != null) {
			builder.add(key, value);
		}
	}
	
	private JsonValue readValue(ValueType expectedType, JsonParser.Event event) {
		switch (event) {
		case START_ARRAY:
			return readArray(expectedType);
		case START_OBJECT:
			return readObject(expectedType);
		case VALUE_NUMBER:
			if (parser.isIntegralNumber()) {
				validateType(expectedType, TypeId.INTEGER);
				long value = parser.getLong();
				if (checkIfInt(value)) {
					return JsonValues.createNumber(Math.toIntExact(value));
				} else {
					return JsonValues.createNumber(value);
				}
			} else {
				validateType(expectedType, TypeId.NUMBER);
				return JsonValues.createNumber(parser.getBigDecimal());
			}
		case VALUE_STRING:
			validateType(expectedType, TypeId.STRING);
			return JsonValues.createString(parser.getString());
		case VALUE_TRUE:
			validateType(expectedType, TypeId.BOOLEAN);
			return JsonValue.TRUE;
		case VALUE_FALSE:
			validateType(expectedType, TypeId.BOOLEAN);
			return JsonValue.FALSE;
		case VALUE_NULL:
			validateType(expectedType, TypeId.NULL);
			return JsonValue.NULL;
		default:
			throw internalError();
		}
	}
	
	private boolean validateType(ValueType expected, TypeId actual) {
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
	
	private static JsonException internalError() {
		return new JsonException("Internal Error");
	}

	private static boolean checkIfInt(long value) {
		return (Integer.MIN_VALUE <= value && value <= Integer.MAX_VALUE);
	}
}
