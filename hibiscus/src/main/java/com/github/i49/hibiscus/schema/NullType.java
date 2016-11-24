package com.github.i49.hibiscus.schema;

import javax.json.JsonValue;

import com.github.i49.hibiscus.common.TypeId;

/**
 * JSON null value.
 */
public class NullType extends AbstractJsonType<JsonValue> implements SimpleType {

	/** The only available instance of this type. */
	public static final NullType INSTANCE = new NullType();
	
	private NullType() {
	}
	
	@Override
	public TypeId getTypeId() {
		return TypeId.NULL;
	}
}
