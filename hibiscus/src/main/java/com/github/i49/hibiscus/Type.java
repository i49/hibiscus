package com.github.i49.hibiscus;

import com.fasterxml.jackson.databind.node.JsonNodeType;

public abstract class Type {

	private final JsonNodeType nodeType;
	
	public Type(JsonNodeType nodeType) {
		this.nodeType = nodeType;
	}
	
	public JsonNodeType getNodeType() {
		return nodeType;
	}
}
