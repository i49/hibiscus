package com.github.i49.hibiscus.validation;

import java.util.ArrayList;
import java.util.List;

import javax.json.JsonArray;
import javax.json.JsonBuilderFactory;
import javax.json.JsonException;
import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.json.stream.JsonParser;

import com.github.i49.hibiscus.common.JsonDocument;
import com.github.i49.hibiscus.common.JsonPointer;
import com.github.i49.hibiscus.common.TypeId;
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
	private final List<Problem> valueProblems = new ArrayList<>();
	private JsonDocument document = new JsonDocument();
	private JsonContext currentContext;
	
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
			DocumentContext context = new DocumentContext(this.document, this.builderFactory);
			pushContext(context);
			readValue(parser.next(), schema.getTypeSet());
			popContext();
			return this.document.getRootValue();
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
	 */
	private void readValue(JsonParser.Event event, TypeSet candidates) {
		if (event == JsonParser.Event.START_ARRAY) {
			readArray(candidates);
		} else if (event == JsonParser.Event.START_OBJECT) {
			readObject(candidates);
		} else {
			readAtomicValue(event, candidates);
		}
	}
	
	private void readArray(TypeSet expected) {
		JsonType type = matchType(TypeId.ARRAY, expected);
		ArrayType arrayType = (type != null) ? ((ArrayType)type) : UnknownArrayType.INSTANCE;
		JsonArray value = buildArray(arrayType);
		getContext().add(value);
		validateValue(arrayType, value);
	}
	
	private JsonArray buildArray(ArrayType type) {
		ArrayContext context = new ArrayContext(this.transientValueProvider, this.builderFactory);
		pushContext(context);
		TypeSet itemTypes = type.getItemTypes();
		while (parser.hasNext()) {
			JsonParser.Event event = parser.next();
			if (event == JsonParser.Event.END_ARRAY) {
				popContext();
				return context.getArray();
			} else {
				context.nextItem();
				readValue(event, itemTypes);
			}
		}
		throw internalError();
	}
	
	private void readObject(TypeSet expected) {
		JsonType type = matchType(TypeId.OBJECT, expected);
		ObjectType objectType = (type != null) ? ((ObjectType)type) : UnknownObjectType.INSTANCE;
		JsonObject value = buildObject(objectType);
		getContext().add(value);
		validateValue(objectType, value);
	}
	
	private JsonObject buildObject(ObjectType objectType) {
		ObjectContext context = new ObjectContext(this.transientValueProvider, this.builderFactory);
		pushContext(context);
		while (parser.hasNext()) {
			JsonParser.Event e = parser.next();
			if (e == JsonParser.Event.END_OBJECT) {
				popContext();
				return context.getObject();
			} else if (e == JsonParser.Event.KEY_NAME) {
				readProperty(objectType, context);
			} else {
				throw internalError();
			}
		}
		throw internalError();
	}
	
	/**
	 * Reads a property of the object. 
	 * @param object the object type which has the property.
	 * @param context the context of the current object.
	 */
	private void readProperty(ObjectType object, ObjectContext context) {
		String name = parser.getString();
		TypeSet typeCandidates = findPropertyType(object, name);
		if (typeCandidates == null) {
			if (!object.allowsMoreProperties()) {
				addProblem(new UnknownPropertyProblem(name), context.getBasePointer());
			}
		}
		context.nextName(name);
		readValue(parser.next(), typeCandidates);
	}
	
	/**
	 * Reads an atomic JSON value such as boolean, integer, number, null and string.
	 * @param event the event provided by Streaming API.
	 * @param candidates the type candidates declared in the schema.
	 * @return {@link JsonValue} found in the JSON document.
	 */
	private void readAtomicValue(JsonParser.Event event, TypeSet candidates) {
		
		JsonContext context = getContext();
		JsonType type = null;
		JsonValue value = null;

		switch (event) {
		case VALUE_NUMBER:
			if (parser.isIntegralNumber()) {
				type = matchType(TypeId.INTEGER, candidates);
				long longValue = parser.getLong();
				if (Integer.MIN_VALUE <= longValue && longValue <= Integer.MAX_VALUE) {
					value = context.add(Math.toIntExact(longValue));
				} else {
					value = context.add(longValue);
				}
			} else {
				type = matchType(TypeId.NUMBER, candidates);
				value = context.add(parser.getBigDecimal());
			}
			break;
		case VALUE_STRING:
			type = matchType(TypeId.STRING, candidates);
			value = context.add(parser.getString());
			break;
		case VALUE_TRUE:
			type = matchType(TypeId.BOOLEAN, candidates);
			value = context.add(JsonValue.TRUE);
			break;
		case VALUE_FALSE:
			type = matchType(TypeId.BOOLEAN, candidates);
			value = context.add(JsonValue.FALSE);
			break;
		case VALUE_NULL:
			type = matchType(TypeId.NULL, candidates);
			value = context.add(JsonValue.NULL);
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
			JsonContext context = getContext();
			addProblem(new TypeMismatchProblem(actual, candidates.getTypeIds()), context.getCurrentPointer());
		}
		return type;
	}
	
	/**
	 * Validates a transient JSON value.
	 * @param type the type of JSON value.
	 * @param value the value to be validated.
	 */
	private void validateValue(JsonType type, JsonValue value) {
		if (type == null) {
			return;
		}
		List<Problem> problems = this.valueProblems;
		type.validateInstance(value, problems);
		if (!problems.isEmpty()) {
			JsonContext context = getContext();
			JsonPointer pointer = context.getCurrentPointer();
			for (Problem p: problems) {
				addProblem(p, pointer);
			}
			problems.clear();
		}
	}
	
	private TypeSet findPropertyType(ObjectType objectType, String propertyName) {
		Property property = objectType.getProperty(propertyName);
		return (property == null) ? null : property.getTypeSet();
	}

	/**
	 * Adds a problem found to the list of the problems.
	 * @param problem the problem found while validating the JSON document.
	 * @param pointer the JSON pointer which refers to the value that caused the problem.
	 */
	private void addProblem(Problem problem, JsonPointer pointer) {
		problem.setPointer(pointer, this.document);
		problem.setLocation(parser.getLocation());
		this.problems.add(problem);
	}
	
	private void pushContext(JsonContext context) {
		context.setParent(this.currentContext);
		this.currentContext = context;
	}
	
	private void popContext() {
		this.currentContext = this.currentContext.getParent();
	}
	
	private JsonContext getContext() {
		return this.currentContext;
	}
	
	/**
	 * Returns the exception to be thrown when internal error occurred.
	 * @return the exception which represents an internal error.
	 */
	private static JsonException internalError() {
		return new JsonException("Internal Error");
	}
}
