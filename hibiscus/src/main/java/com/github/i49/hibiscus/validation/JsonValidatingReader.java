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

/**
 * JSON reader which validates contents against given schema at the same time.
 */
class JsonValidatingReader {

	private final JsonParser parser;
	private final JsonBuilderFactory factory;
	private final List<Problem> problems = new ArrayList<>();
	
	private static final ArrayType UNKNOWN_ARRAY_TYPE = new UnknownArrayType();
	private static final ObjectType UNKNOWN_OBJECT_TYPE = new UnknownObjectType();

	public JsonValidatingReader(JsonParser parser, JsonBuilderFactory factory) {
		this.parser = parser;
		this.factory = factory;
	}
	
	public JsonValue readAll(ValueType expected) {
		if (parser.hasNext()) {
			TypeMap typeMap = TypeMap.of(expected);
			return readValue(parser.next(), typeMap);
		} else {
			return null;
		}
	}
	
	/**
	 * Returns all problems found.
	 * @return problems found
	 */
	public List<Problem> getProblems() {
		return problems;
	}
	
	private JsonArray readArray(TypeMap expected) {
		ValueType type = validateType(TypeId.ARRAY, expected);
		ArrayType arrayType = (type != null) ? ((ArrayType)type) : UNKNOWN_ARRAY_TYPE;
		return readArray(arrayType);
	}
	
	private JsonArray readArray(ArrayType type) {
		JsonArrayBuilder builder = this.factory.createArrayBuilder();
		TypeMap itemTypes = type.getItemTypes();
		while (parser.hasNext()) {
			JsonParser.Event event = parser.next();
			if (event == JsonParser.Event.END_ARRAY) {
				return builder.build();
			} else {
				JsonValue item = readValue(event, itemTypes);
				if (item != null) {
					builder.add(item);
				}
			}
		}
		throw internalError();
	}
	
	private JsonObject readObject(TypeMap expected) {
		ValueType type = validateType(TypeId.OBJECT, expected);
		ObjectType objectType = (type != null) ? ((ObjectType)type) : UNKNOWN_OBJECT_TYPE;
		return readObject(objectType);
	}
	
	private JsonObject readObject(ObjectType objectType) {
		JsonObjectBuilder builder = this.factory.createObjectBuilder();
		while (parser.hasNext()) {
			JsonParser.Event e = parser.next();
			if (e == JsonParser.Event.END_OBJECT) {
				JsonObject object = builder.build();
				validateObject(objectType, object);
				return object;
			} else if (e == JsonParser.Event.KEY_NAME) {
				readProperty(objectType, builder);
			} else {
				throw internalError();
			}
		}
		throw internalError();
	}
	
	private void readProperty(ObjectType object, JsonObjectBuilder builder) {
		String name = parser.getString();
		TypeMap candidates = findPropertyType(object, name);
		JsonValue value = readValue(parser.next(), candidates);
		if (value != null) {
			builder.add(name, value);
		}
	}
	
	private JsonValue readValue(JsonParser.Event event, TypeMap candidates) {
		switch (event) {
		case START_ARRAY:
			return readArray(candidates);
		case START_OBJECT:
			return readObject(candidates);
		case VALUE_NUMBER:
			if (parser.isIntegralNumber()) {
				validateType(TypeId.INTEGER, candidates);
				long value = parser.getLong();
				if (Integer.MIN_VALUE <= value && value <= Integer.MAX_VALUE) {
					return JsonValues.createNumber(Math.toIntExact(value));
				} else {
					return JsonValues.createNumber(value);
				}
			} else {
				validateType(TypeId.NUMBER, candidates);
				return JsonValues.createNumber(parser.getBigDecimal());
			}
		case VALUE_STRING:
			validateType(TypeId.STRING, candidates);
			return JsonValues.createString(parser.getString());
		case VALUE_TRUE:
			validateType(TypeId.BOOLEAN, candidates);
			return JsonValue.TRUE;
		case VALUE_FALSE:
			validateType(TypeId.BOOLEAN, candidates);
			return JsonValue.FALSE;
		case VALUE_NULL:
			validateType(TypeId.NULL, candidates);
			return JsonValue.NULL;
		default:
			throw internalError();
		}
	}
	
	private ValueType validateType(TypeId actual, TypeMap candidates) {
		if (candidates == null) {
			return null;
		}
		ValueType type = candidates.getType(actual);
		if (type == null) {
			addProblem(new TypeMismatchProblem(candidates.getTypeIds(), actual, getLocation()));
		}
		return type;
	}
	
	private TypeMap findPropertyType(ObjectType objectType, String propertyName) {
		Property property = objectType.getProperty(propertyName);
		if (property == null) {
			if (!objectType.allowsMoreProperties()) {
				addProblem(new UnknownPropertyProblem(propertyName, getLocation()));
			}
			return null;
		}
		return property.getTypeMap();
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
	
	private static JsonException internalError() {
		return new JsonException("Internal Error");
	}
	
	/**
	 * Unknown array type.
	 */
	private static class UnknownArrayType extends ArrayType {
		@Override
		public TypeMap getItemTypes() {
			return null;
		}
	}
	
	/**
	 * Unknown object type.
	 */
	private static class UnknownObjectType extends ObjectType {
		@Override
		public boolean allowsMoreProperties() {
			return true;
		}
	}
}
