package com.github.i49.hibiscus.schema.internal;

import javax.json.JsonValue;

import com.github.i49.hibiscus.schema.NullType;

/**
 * The implementation class of {@link NullType}.
 */
public class NullTypeImpl extends AbstractJsonType<JsonValue, NullType> implements NullType {

	/** The Singleton instance of this type. */
	public static final NullType INSTANCE = new NullTypeImpl();
	
	/**
	 * Constructs this type.
	 */
	private NullTypeImpl() {
	}
}
