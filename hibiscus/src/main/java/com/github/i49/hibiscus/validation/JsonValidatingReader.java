package com.github.i49.hibiscus.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import javax.json.JsonArray;
import javax.json.JsonBuilderFactory;
import javax.json.JsonException;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;
import javax.json.stream.JsonParser;

import com.github.i49.hibiscus.common.TypeId;
import com.github.i49.hibiscus.problems.JsonValueProblem;
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
	private final TransientValueProvider transientValueProvider = new TransientValueProvider();
	private final List<Problem> problems = new ArrayList<>();
	private final List<JsonValueProblem> valueProblems = new ArrayList<>();
	
	/**
	 * Constructs this reader.
	 * @param parser the JSON parser which conforms to Java API for JSON Processing.
	 * @param builderFactory the JSON builder which conforms to Java API for JSON Processing.
	 */
	public JsonValidatingReader(JsonParser parser, JsonBuilderFactory builderFactory) {
		this.parser = parser;
		this.builderFactory = builderFactory;
	}
	
	/**
	 * Reads all contents of the JSON document.
	 * @param schema the schema against which this reader validates the JSON document.
	 * @return the JSON value found at the root of the JSON document.
	 */
	public JsonValue readAll(Schema schema) {
		if (parser.hasNext()) {
			RootContainer container = new RootContainer(this.builderFactory);
			readValue(parser.next(), schema.getTypeSet(), container);
			return container.getRootValue();
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

	/**
	 * Reads JSON array, object, or other values.
	 * @param event the event which {@link JsonParser} emits.
	 * @param candidates the type candidates of the value to be read. 
	 * @param container the container of the value to be read.
	 */
	private void readValue(JsonParser.Event event, TypeSet candidates, ValueContainer container) {
		if (event == JsonParser.Event.START_ARRAY) {
			container.add(readArray(candidates)).get();
		} else if (event == JsonParser.Event.START_OBJECT) {
			container.add(readObject(candidates)).get();
		} else {
			readAtomicValue(event, candidates, container);
		}
	}
	
	private JsonArray readArray(TypeSet expected) {
		JsonType type = matchType(TypeId.ARRAY, expected);
		ArrayType arrayType = (type != null) ? ((ArrayType)type) : UnknownArrayType.INSTANCE;
		JsonArray value = buildArray(arrayType);
		validateValue(arrayType, value);
		return value;
	}
	
	private JsonArray buildArray(ArrayType type) {
		ArrayContainer container = new ArrayContainer(this.transientValueProvider, this.builderFactory);
		TypeSet itemTypes = type.getItemTypes();
		while (parser.hasNext()) {
			JsonParser.Event event = parser.next();
			if (event == JsonParser.Event.END_ARRAY) {
				return container.build();
			} else {
				readValue(event, itemTypes, container);
			}
		}
		throw internalError();
	}
	
	private JsonObject readObject(TypeSet expected) {
		JsonType type = matchType(TypeId.OBJECT, expected);
		ObjectType objectType = (type != null) ? ((ObjectType)type) : UnknownObjectType.INSTANCE;
		JsonObject value = buildObject(objectType);
		validateValue(objectType, value);
		return value;
	}
	
	private JsonObject buildObject(ObjectType objectType) {
		ObjectContainer container = new ObjectContainer(this.transientValueProvider, this.builderFactory);
		while (parser.hasNext()) {
			JsonParser.Event e = parser.next();
			if (e == JsonParser.Event.END_OBJECT) {
				return container.build();
			} else if (e == JsonParser.Event.KEY_NAME) {
				readProperty(objectType, container);
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
	private void readProperty(ObjectType object, ObjectContainer container) {
		String name = parser.getString();
		TypeSet typeCandidates = findPropertyType(object, name);
		container.setNextName(name);
		readValue(parser.next(), typeCandidates, container);
	}
	
	/**
	 * Reads an atomic JSON value such as boolean, integer, number, null and string.
	 * @param event the event provided by Streaming API.
	 * @param candidates the type candidates declared in the schema.
	 * @return {@link JsonValue} found in the JSON document.
	 */
	private void readAtomicValue(JsonParser.Event event, TypeSet candidates, ValueContainer container) {
		
		JsonType type = null;
		Transient<JsonValue> value = null;

		switch (event) {
		case VALUE_NUMBER:
			if (parser.isIntegralNumber()) {
				type = matchType(TypeId.INTEGER, candidates);
				long longValue = parser.getLong();
				if (Integer.MIN_VALUE <= longValue && longValue <= Integer.MAX_VALUE) {
					value = container.add(Math.toIntExact(longValue));
				} else {
					value = container.add(longValue);
				}
			} else {
				type = matchType(TypeId.NUMBER, candidates);
				value = container.add(parser.getBigDecimal());
			}
			break;
		case VALUE_STRING:
			type = matchType(TypeId.STRING, candidates);
			value = container.add(parser.getString());
			break;
		case VALUE_TRUE:
			type = matchType(TypeId.BOOLEAN, candidates);
			value = container.add(JsonValue.TRUE);
			break;
		case VALUE_FALSE:
			type = matchType(TypeId.BOOLEAN, candidates);
			value = container.add(JsonValue.FALSE);
			break;
		case VALUE_NULL:
			type = matchType(TypeId.NULL, candidates);
			value = container.add(JsonValue.NULL);
			break;
		default:
			throw internalError();
		}
		
		validateValue(type, value);
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
	
	/**
	 * Validates a JSON value.
	 * @param type the type of JSON value.
	 * @param value the value to be validated.
	 */
	private void validateValue(JsonType type, JsonValue value) {
		if (type == null) {
			return;
		}
		List<JsonValueProblem> problems = this.valueProblems;
		type.validateInstance(value, problems);
		if (!problems.isEmpty()) {
			Future<JsonValue> future = CompletableFuture.completedFuture(value);
			for (JsonValueProblem p: problems) {
				p.setActualValue(future);
				addProblem(p);
			}
			problems.clear();
		}
	}
	
	/**
	 * Validates a transient JSON value.
	 * @param type the type of JSON value.
	 * @param value the value to be validated.
	 */
	private void validateValue(JsonType type, Transient<JsonValue> value) {
		if (type == null) {
			return;
		}
		List<JsonValueProblem> problems = this.valueProblems;
		type.validateInstance(value.get(), problems);
		if (!problems.isEmpty()) {
			Future<JsonValue> future = value.getFinalValue();
			for (JsonValueProblem p: problems) {
				p.setActualValue(future);
				addProblem(p);
			}
			problems.clear();
		}
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
	 * Returns the exception to be thrown when internal error occurred.
	 * @return the exception which represents an internal error.
	 */
	private static JsonException internalError() {
		return new JsonException("Internal Error");
	}
}
