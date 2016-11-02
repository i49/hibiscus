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
import com.github.i49.schema.TypeId;
import com.github.i49.schema.problems.Problem;
import com.github.i49.schema.problems.TypeMismatchProblem;
import com.github.i49.schema.problems.UnknownPropertyProblem;
import com.github.i49.schema.types.ArrayType;
import com.github.i49.schema.types.ObjectType;
import com.github.i49.schema.types.ValueType;

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
		JsonArray value = readArray(arrayType);
		arrayType.validateInstance(value, null, problems);
		return value;
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
		JsonObject value = readObject(objectType);
		objectType.validateInstance(value, getLocation(), problems);
		return value;
	}
	
	private JsonObject readObject(ObjectType objectType) {
		JsonObjectBuilder builder = this.factory.createObjectBuilder();
		while (parser.hasNext()) {
			JsonParser.Event e = parser.next();
			if (e == JsonParser.Event.END_OBJECT) {
				return builder.build();
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
		if (event == JsonParser.Event.START_ARRAY) {
			return readArray(candidates);
		} else if (event == JsonParser.Event.START_OBJECT) {
			return  readObject(candidates);
		} else {
			return readSimpleValue(event, candidates);
		}
	}
	
	private JsonValue readSimpleValue(JsonParser.Event event, TypeMap candidates) {
		
		ValueType type = null;
		JsonValue value = null;

		switch (event) {
		case VALUE_NUMBER:
			if (parser.isIntegralNumber()) {
				type = validateType(TypeId.INTEGER, candidates);
				long longValue = parser.getLong();
				if (Integer.MIN_VALUE <= longValue && longValue <= Integer.MAX_VALUE) {
					value = JsonValues.createNumber(Math.toIntExact(longValue));
				} else {
					value = JsonValues.createNumber(longValue);
				}
			} else {
				type = validateType(TypeId.NUMBER, candidates);
				value = JsonValues.createNumber(parser.getBigDecimal());
			}
			break;
		case VALUE_STRING:
			type = validateType(TypeId.STRING, candidates);
			value = JsonValues.createString(parser.getString());
			break;
		case VALUE_TRUE:
			type = validateType(TypeId.BOOLEAN, candidates);
			value = JsonValue.TRUE;
			break;
		case VALUE_FALSE:
			type = validateType(TypeId.BOOLEAN, candidates);
			value = JsonValue.FALSE;
			break;
		case VALUE_NULL:
			type = validateType(TypeId.NULL, candidates);
			value = JsonValue.NULL;
			break;
		default:
			throw internalError();
		}
		
		if (type != null) {
			type.validateInstance(value, null, problems);
		}

		return value;
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
