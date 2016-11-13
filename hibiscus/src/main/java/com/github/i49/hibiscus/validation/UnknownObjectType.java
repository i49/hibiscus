package com.github.i49.hibiscus.validation;

import com.github.i49.hibiscus.schema.ObjectType;

/**
 * Unknown object type found during validation.
 */
class UnknownObjectType extends ObjectType {
	
	public static final ObjectType INSTANCE = new UnknownObjectType();

	private UnknownObjectType() {
	}

	@Override
	public boolean allowsMoreProperties() {
		return true;
	}
}
