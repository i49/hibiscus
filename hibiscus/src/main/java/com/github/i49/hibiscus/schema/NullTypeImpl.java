package com.github.i49.hibiscus.schema;

import javax.json.JsonValue;

/**
 * Implementation of {@code NullType}.
 */
class NullTypeImpl extends AbstractJsonType<JsonValue> implements NullType {

	/** The only available instance of this type. */
	public static final NullType INSTANCE = new NullTypeImpl();
	
	/**
	 * Constructs this type.
	 */
	private NullTypeImpl() {
	}
}
