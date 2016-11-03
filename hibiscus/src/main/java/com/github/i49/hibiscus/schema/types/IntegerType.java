package com.github.i49.hibiscus.schema.types;

import com.github.i49.hibiscus.schema.TypeId;

/**
 * JSON number without a fraction or exponent part.
 */
public class IntegerType extends JsonType {

	private static final IntegerType DEFAULT = new IntegerType();
	
	/**
	 * Returns this type with default settings.
	 * @return immutable type with default settings.
	 */
	public static IntegerType getDefault() {
		return DEFAULT;
	}
	
	@Override
	public TypeId getTypeId() {
		return TypeId.INTEGER;
	}
}
