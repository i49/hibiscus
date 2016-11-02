package com.github.i49.schema.types;

import com.github.i49.schema.TypeId;

/**
 * JSON boolean.
 */
public class BooleanType extends ValueType {
	
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
