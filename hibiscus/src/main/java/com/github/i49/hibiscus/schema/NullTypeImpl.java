package com.github.i49.hibiscus.schema;

import javax.json.JsonValue;

/**
 * The implementation class of {@link NullType}.
 */
class NullTypeImpl extends AbstractJsonType<JsonValue, NullType> implements NullType {

	/** The Singleton instance of this type. */
	public static final NullType INSTANCE = new NullTypeImpl();
	
	/**
	 * Constructs this type.
	 */
	private NullTypeImpl() {
	}
}
