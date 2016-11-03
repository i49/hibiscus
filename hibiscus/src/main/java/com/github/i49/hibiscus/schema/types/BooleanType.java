package com.github.i49.hibiscus.schema.types;

import com.github.i49.hibiscus.schema.TypeId;

/**
 * JSON boolean.
 */
public class BooleanType extends JsonType {
	
	private static final BooleanType DEFAULT = new BooleanType();
	
	/**
	 * Returns this type with default settings.
	 * @return immutable type with default settings.
	 */
	public static BooleanType getDefault() {
		return DEFAULT;
	}

	@Override
	public TypeId getTypeId() {
		return TypeId.BOOLEAN;
	}
}
