package com.github.i49.schema.types;

import com.github.i49.schema.TypeId;

/**
 * JSON string.
 */
public class StringType extends ValueType {
	
	private static final StringType DEFAULT = new StringType();

	/**
	 * Returns this type with default settings.
	 * @return immutable type with default settings.
	 */
	public static StringType getDefault() {
		return DEFAULT;
	}
	
	@Override
	public TypeId getTypeId() {
		return TypeId.STRING;
	}
}
