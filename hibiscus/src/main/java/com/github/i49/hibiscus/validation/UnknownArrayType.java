package com.github.i49.hibiscus.validation;

import com.github.i49.schema.types.ArrayType;
import com.github.i49.schema.types.TypeSet;

/**
 * Unknown array type found during validation.
 */
class UnknownArrayType extends ArrayType {

	public static final UnknownArrayType INSTANCE = new UnknownArrayType();
	
	private UnknownArrayType() {
		super(TypeSet.EMPTY);
	}

	@Override
	public TypeSet getItemTypes() {
		return null;
	}
}
