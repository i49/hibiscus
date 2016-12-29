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
import com.github.i49.hibiscus.json.JsonValueFactory;
import com.github.i49.hibiscus.problems.Problem;
import com.github.i49.hibiscus.problems.TypeMismatchProblem;
import com.github.i49.hibiscus.problems.UnknownPropertyProblem;
import com.github.i49.hibiscus.schema.ArrayType;
import com.github.i49.hibiscus.schema.JsonType;
import com.github.i49.hibiscus.schema.ObjectType;
import com.github.i49.hibiscus.schema.Property;
import com.github.i49.hibiscus.schema.Schema;
import com.github.i49.hibiscus.schema.TypeSet;

/**
 * An internal class to be used to parse and validate JSON documents against specified schema.
 * The object of this class should be instantiated on every reading of a new JSON document.
 * 
 * <p>All methods of this object are intended to be invoked from the same thread.</p>
 */
class JsonValidatingReader {

	private final JsonParser parser;
	private final JsonBuilderFactory builderFactory;
	private final JsonValueFactory valueFactory;
	private final List<Problem> problems = new ArrayList<>();
	private final List<Problem> lastProblems = new ArrayList<>();

	/**
	 * Constructs this reader.
	 * @param parser the JSON parser which conforms to Java API for JSON Processing.
	 * @param builderFactory the JSON builder which conforms to Java API for JSON Processing.
	 * @param valueFactory the factory to create JSON values. 
	 */
	public JsonValidatingReader(JsonParser parser, JsonBuilderFactory builderFactory, JsonValueFactory valueFactory) {
		this.parser = parser;
		this.builderFactory = builderFactory;
		this.valueFactory = valueFactory;
	}
	
	/**
	 * Reads all contents of the JSON document.
	 * @param schema the schema against which this reader validates the JSON document.
	 * @return the JSON value found at the root of the JSON document.
	 */
	public JsonValue readAll(Schema schema) {
		if (parser.hasNext()) {
			return readValue(parser.next(), schema.getTypeSet());
		} else {
			return null;
		}
	}
	
	/**
	 * Returns all problems found by the validation against the schema.
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
		JsonArrayBuilder builder = this.builderFactory.createArrayBuilder();
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
		JsonObjectBuilder builder = this.builderFactory.createObjectBuilder();
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
	 * Reads a property of the object. 
	 * @param object the object type which has the property.
	 * @param builder {@link JsonObjectBuilder} to be used to build JSON object.
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
			return readAtomicValue(event, candidates);
		}
	}
	
	/**
	 * Reads an atomic JSON value such as boolean, integer, number, null and string.
	 * @param event the event provided by Streaming API.
	 * @param candidates the type candidates declared in the schema.
	 * @return {@link JsonValue} found in the JSON document.
	 */
	private JsonValue readAtomicValue(JsonParser.Event event, TypeSet candidates) {
		
		JsonType type = null;
		JsonValue value = null;

		switch (event) {
		case VALUE_NUMBER:
			if (parser.isIntegralNumber()) {
				type = matchType(TypeId.INTEGER, candidates);
				long longValue = parser.getLong();
				if (Integer.MIN_VALUE <= longValue && longValue <= Integer.MAX_VALUE) {
					value = valueFactory.createNumber(Math.toIntExact(longValue));
				} else {
					value = valueFactory.createNumber(longValue);
				}
			} else {
				type = matchType(TypeId.NUMBER, candidates);
				value = valueFactory.createNumber(parser.getBigDecimal());
			}
			break;
		case VALUE_STRING:
			type = matchType(TypeId.STRING, candidates);
			value = valueFactory.createString(parser.getString());
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
	 * Adds a problem found to the list of the problems.
	 * @param problem the problem found while validation the JSON document.
	 */
	private void addProblem(Problem problem) {
		problem.setLocation(parser.getLocation());
		this.problems.add(problem);
	}

	/**
	 * Adds multiple problems found to the list of the problems.
	 * @param problems the problems found while validation the JSON document.
	 */
	private void addProblems(Collection<Problem> problems) {
		for (Problem p: problems) {
			addProblem(p);
		}
	}

	/**
	 * Returns the exception to be thrown when internal error occurred.
	 * @return the exception which represents an internal error.
	 */
	private static JsonException internalError() {
		return new JsonException("Internal Error");
	}
}
