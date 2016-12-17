package com.github.i49.hibiscus.validation;

import java.util.function.Predicate;

import javax.json.JsonArray;

import com.github.i49.hibiscus.facets.Facet;
import com.github.i49.hibiscus.problems.DescriptionSupplier;
import com.github.i49.hibiscus.schema.ArrayType;
import com.github.i49.hibiscus.schema.JsonType;
import com.github.i49.hibiscus.schema.TypeSet;

/**
 * Unknown array type found during validation.
 */
class UnknownArrayType implements ArrayType {

	public static final UnknownArrayType INSTANCE = new UnknownArrayType();
	
	private UnknownArrayType() {
	}

	@Override
	public TypeSet getItemTypes() {
		return null;
	}

	@Override
	public ArrayType items(JsonType... types) {
		return this;
	}
	
	@Override
	public ArrayType facet(Facet<JsonArray> facet) {
		return this;
	}

	@Override
	public ArrayType length(int length) {
		return this;
	}

	@Override
	public ArrayType minLength(int length) {
		return this;
	}

	@Override
	public ArrayType maxLength(int length) {
		return this;
	}

	@Override
	public ArrayType unique() {
		return this;
	}

	@Override
	public ArrayType assertion(Predicate<JsonArray> predicate, DescriptionSupplier<JsonArray> description) {
		return this;
	}
}
