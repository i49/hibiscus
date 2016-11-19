package com.github.i49.hibiscus.validation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonBuilderFactory;
import javax.json.JsonException;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;
import javax.json.stream.JsonParser;

import com.github.i49.hibiscus.common.TypeId;
import com.github.i49.hibiscus.json.JsonValues;
import com.github.i49.hibiscus.problems.Problem;
import com.github.i49.hibiscus.problems.TypeMismatchProblem;
import com.github.i49.hibiscus.problems.UnknownPropertyProblem;
import com.github.i49.hibiscus.schema.ArrayType;
import com.github.i49.hibiscus.schema.JsonType;
import com.github.i49.hibiscus.schema.ObjectType;
import com.github.i49.hibiscus.schema.Property;
import com.github.i49.hibiscus.schema.TypeSet;

/**
 * JSON reader which reads and validates contents against specified schema.
 * This class is to be instantiated on each reading of JSON content.
 */
class JsonValidatingReader {

	private final JsonParser parser;
	private final JsonBuilderFactory factory;
	private final List<Problem> problems = new ArrayList<>();
	private final List<Problem> lastProblems = new ArrayList<>();

	/**
	 * Constructs this reader.
	 * @param parser JSON parser which conforms to JSON Processing API.
	 * @param factory JSON builder which conforms to JSON Processing API. 
	 */
	public JsonValidatingReader(JsonParser parser, JsonBuilderFactory factory) {
		this.parser = parser;
		this.factory = factory;
	}
	
	/**
	 * Reads all JSON contents.
	 * @param expected expected root type which must be array or object.
	 * @return JSON value at root.
	 */
	public JsonValue readAll(JsonType expected) {
		if (parser.hasNext()) {
			TypeSet typeSet = TypeSet.of(expected);
			return readValue(parser.next(), typeSet);
		} else {
			return null;
		}
	}
	
	/**
	 * Returns all problems found by validation against schema.
	 * @return all problems found.
	 */
	public List<Problem> getProblems() {
		return problems;
	}
	
	private JsonArray readArray(TypeSet expected) {
		JsonType type = matchType(TypeId.ARRAY, expected);
		ArrayType arrayType = (type != null) ? ((ArrayType)type) : UnknownArrayType.INSTANCE;
		JsonArray value = readArray(arrayType);
		validateInstance(arrayType, value);
		return value;
	}
	
	private JsonArray readArray(ArrayType type) {
		JsonArrayBuilder builder = this.factory.createArrayBuilder();
		TypeSet itemTypes = type.getItemTypes();
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
	
	private JsonObject readObject(TypeSet expected) {
		JsonType type = matchType(TypeId.OBJECT, expected);
		ObjectType objectType = (type != null) ? ((ObjectType)type) : UnknownObjectType.INSTANCE;
		JsonObject value = readObject(objectType);
		validateInstance(objectType, value);
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
	
	/**
	 * Reads a property of object. 
	 * @param object object containing the property.
	 * @param builder object builder.
	 */
	private void readProperty(ObjectType object, JsonObjectBuilder builder) {
		String propertyName = parser.getString();
		TypeSet typeCandidates = findPropertyType(object, propertyName);
		JsonValue propertyValue = readValue(parser.next(), typeCandidates);
		if (propertyValue != null) {
			builder.add(propertyName, propertyValue);
		}
	}
	
	private JsonValue readValue(JsonParser.Event event, TypeSet candidates) {
		if (event == JsonParser.Event.START_ARRAY) {
			return readArray(candidates);
		} else if (event == JsonParser.Event.START_OBJECT) {
			return  readObject(candidates);
		} else {
			return readSimpleValue(event, candidates);
		}
	}
	
	/**
	 * Reads simple JSON value such as string, integer, number, boolean or null.
	 * @param event event provided by Streaming API.
	 * @param candidates candidates of type.
	 * @return instance value read.
	 */
	private JsonValue readSimpleValue(JsonParser.Event event, TypeSet candidates) {
		
		JsonType type = null;
		JsonValue value = null;

		switch (event) {
		case VALUE_NUMBER:
			if (parser.isIntegralNumber()) {
				type = matchType(TypeId.INTEGER, candidates);
				long longValue = parser.getLong();
				if (Integer.MIN_VALUE <= longValue && longValue <= Integer.MAX_VALUE) {
					value = JsonValues.createNumber(Math.toIntExact(longValue));
				} else {
					value = JsonValues.createNumber(longValue);
				}
			} else {
				type = matchType(TypeId.NUMBER, candidates);
				value = JsonValues.createNumber(parser.getBigDecimal());
			}
			break;
		case VALUE_STRING:
			type = matchType(TypeId.STRING, candidates);
			value = JsonValues.createString(parser.getString());
			break;
		case VALUE_TRUE:
			type = matchType(TypeId.BOOLEAN, candidates);
			value = JsonValue.TRUE;
			break;
		case VALUE_FALSE:
			type = matchType(TypeId.BOOLEAN, candidates);
			value = JsonValue.FALSE;
			break;
		case VALUE_NULL:
			type = matchType(TypeId.NULL, candidates);
			value = JsonValue.NULL;
			break;
		default:
			throw internalError();
		}
		
		return validateInstance(type, value);
	}
	
	private JsonType matchType(TypeId actual, TypeSet candidates) {
		if (candidates == null) {
			return null;
		}
		JsonType type = candidates.getType(actual);
		if (type == null) {
			addProblem(new TypeMismatchProblem(actual, candidates.getTypeIds()));
		}
		return type;
	}
	
	private JsonValue validateInstance(JsonType type, JsonValue value) {
		if (type == null) {
			return value;
		}
		type.validateInstance(value, lastProblems);
		if (!lastProblems.isEmpty()) {
			addProblems(lastProblems);
			lastProblems.clear();
		}
		return value;
	}
	
	private TypeSet findPropertyType(ObjectType objectType, String propertyName) {
		Property property = objectType.getProperty(propertyName);
		if (property == null) {
			if (!objectType.allowsMoreProperties()) {
				addProblem(new UnknownPropertyProblem(propertyName));
			}
			return null;
		}
		return property.getTypeSet();
	}

	/**
	 * Adds problem found to list of problems.
	 * @param problem problem found during the validation.
	 */
	private void addProblem(Problem problem) {
		problem.setLocation(parser.getLocation());
		this.problems.add(problem);
	}

	/**
	 * Adds multiple problems found to list of problems.
	 * @param problems problems found during the validation.
	 */
	private void addProblems(Collection<Problem> problems) {
		for (Problem p: problems) {
			addProblem(p);
		}
	}

	/**
	 * Returns exception to be thrown when internal error was found.
	 * @return exception which means internal error.
	 */
	private static JsonException internalError() {
		return new JsonException("Internal Error");
	}
}
