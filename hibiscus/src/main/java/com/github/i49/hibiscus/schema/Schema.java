package com.github.i49.hibiscus.schema;

/**
 * JSON schema.
 */
public interface Schema {
	
	/**
	 * Specifies JSON types allowed to be at root of JSON document.
	 * @param types the types allowed to be at root of JSON document.
	 * @return this schema.
	 */
	Schema types(JsonType... types);
	
	/**
	 * Returns set of JSON types allowed to be at root of JSON document.
	 * @return set of JSON types.
	 */
	TypeSet getTypeSet();
}
