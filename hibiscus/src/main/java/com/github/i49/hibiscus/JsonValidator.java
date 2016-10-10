package com.github.i49.hibiscus;

import java.util.Iterator;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;

public abstract class JsonValidator {

	public JsonValidator() {
	}
	
	public void validate(JsonNode node) throws ValidationException {
		validateAgainstSchema(node, getSchema());
	} 
	
	protected abstract Type getSchema();
	
	private void validateAgainstSchema(JsonNode node, Type type) throws ValidationException {
		validate("(root node)", node, type);
	}
	
	private void validate(String name, JsonNode node, Type type) throws ValidationException {
		JsonNodeType nodeType = node.getNodeType();
		if (nodeType != type.getNodeType()) {
			throw new TypeMismatchException(name, type.getNodeType(), nodeType);
		}
		
		if (type instanceof ObjectType) {
			validateObject(node, (ObjectType)type);
		} else if (type instanceof ArrayType) {
			validateArray(name, node, (ArrayType)type);
		}
	}
	
	private void validateObject(JsonNode node, ObjectType type) throws ValidationException {
		
		for (Property p: type.required()) {
			if (!node.hasNonNull(p.getName())) {
				throw new MissingPropertyException(p.getName());
			}
		}

		Iterator<Map.Entry<String, JsonNode>> it = node.fields();
		while (it.hasNext()) {
			Map.Entry<String, JsonNode> entry = it.next();
			String name = entry.getKey();
			if (type.containsProperty(name)) {
				validate(name, entry.getValue(), type.getProperty(name).getType());
			} else {
				throw new UnknownPropertyException(name);
			}
		}
	}
	
	private void validateArray(String name, JsonNode node, ArrayType type) throws ValidationException {
		int index = 0;
		for (JsonNode item: node) {
			String itemName = name + "[" + index++ + "]";
			validate(itemName, item, type.getItemType()); 
		}
	}
}
