package com.github.i49.hibiscus.schema;

import javax.json.JsonValue;

import com.github.i49.hibiscus.common.TypeId;

/**
 * Implementation of {@code NullType}.
 */
class NullTypeImpl extends AbstractJsonType<JsonValue> implements NullType {

	/** The only available instance of this type. */
	public static final NullType INSTANCE = new NullTypeImpl();
	
	private NullTypeImpl() {
	}

	@Override
	public TypeId getTypeId() {
		return TypeId.NULL;
	}
}
