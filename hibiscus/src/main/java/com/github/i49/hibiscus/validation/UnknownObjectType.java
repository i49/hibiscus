package com.github.i49.hibiscus.validation;

import java.util.function.Predicate;

import javax.json.JsonObject;

import com.github.i49.hibiscus.problems.DescriptionSupplier;
import com.github.i49.hibiscus.schema.ObjectType;
import com.github.i49.hibiscus.schema.Property;

/**
 * Unknown object type found during validation.
 */
class UnknownObjectType implements ObjectType {
	
	public static final ObjectType INSTANCE = new UnknownObjectType();

	private UnknownObjectType() {
	}

	@Override
	public ObjectType properties(Property... properties) {
		return null;
	}

	@Override
	public ObjectType moreProperties() {
		return null;
	}

	@Override
	public ObjectType assertion(Predicate<JsonObject> predicate, DescriptionSupplier<JsonObject> description) {
		return null;
	}

	@Override
	public Property getProperty(String name) {
		return null;
	}

	@Override
	public boolean allowsMoreProperties() {
		return true;
	}
}
