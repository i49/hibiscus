package com.github.i49.hibiscus.schema.types;

import com.github.i49.hibiscus.schema.TypeId;

/**
 * JSON null value.
 */
public class NullType extends JsonType {

	/** The only available instance of this type. */
	public static final NullType INSTANCE = new NullType();
	
	private NullType() {
	}
	
	@Override
	public TypeId getTypeId() {
		return TypeId.NULL;
	}
}
