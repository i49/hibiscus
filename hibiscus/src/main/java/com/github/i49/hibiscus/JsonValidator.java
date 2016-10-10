package com.github.i49.hibiscus;

import java.util.EnumMap;
import java.util.Iterator;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;

public class JsonValidator {

	private final Type schemaObject;

	private static final EnumMap<JsonNodeType, TypeKind> typeMap = new EnumMap<>(JsonNodeType.class);
	
	static {
		typeMap.put(JsonNodeType.ARRAY, TypeKind.ARRAY);
		typeMap.put(JsonNodeType.BOOLEAN, TypeKind.BOOLEAN);
		typeMap.put(JsonNodeType.NUMBER, TypeKind.NUMBER);
		typeMap.put(JsonNodeType.OBJECT, TypeKind.OBJECT);
		typeMap.put(JsonNodeType.STRING, TypeKind.STRING);
	}
	
	public JsonValidator(Type schemaObject) {
		this.schemaObject = schemaObject;
	}
	
	public Type getSchemaObject() {
		return schemaObject;
	}
	
	public void validate(JsonNode node) throws ValidationException {
		validateAgainstSchema(node, getSchemaObject());
	} 
	
	private void validateAgainstSchema(JsonNode node, Type type) throws ValidationException {
		validate("(root node)", node, type);
	}
	
	private void validate(String name, JsonNode node, Type type) throws ValidationException {
		if (!matchTypes(node, type)) {
			throw new TypeMismatchException(name, type.getTypeKind(), getTypeOf(node));
		}
		
		if (type instanceof ObjectType) {
			validateObject(node, (ObjectType)type);
		} else if (type instanceof ArrayType) {
			validateArray(name, node, (ArrayType)type);
		}
	}
	
	private void validateObject(JsonNode node, ObjectType type) throws ValidationException {
		
		for (Property p: type.getRequiredProperties()) {
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

	private static boolean matchTypes(JsonNode node, Type type) {
		return getTypeOf(node).isCompatible(type.getTypeKind());
	}
	
	private static TypeKind getTypeOf(JsonNode node) {
		TypeKind type = typeMap.get(node.getNodeType());
		if (type == TypeKind.NUMBER && !node.isFloatingPointNumber()) {
			type = TypeKind.INTEGER;
		}
		return type;
	}
}
