package com.github.i49.hibiscus.validation;

import com.github.i49.hibiscus.schema.ArrayType;
import com.github.i49.hibiscus.schema.TypeSet;

/**
 * Unknown array type found during validation.
 */
class UnknownArrayType extends ArrayType {

	public static final UnknownArrayType INSTANCE = new UnknownArrayType();
	
	private UnknownArrayType() {
	}

	@Override
	public TypeSet getItemTypes() {
		return null;
	}
}
