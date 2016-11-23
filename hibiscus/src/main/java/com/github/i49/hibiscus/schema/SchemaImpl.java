package com.github.i49.hibiscus.schema;

/**
 * Implementation of {@link Schema} interface.
 */
class SchemaImpl implements Schema {

	private TypeSet typeSet = TypeSet.empty();
	
	@Override
	public Schema types(JsonType... types) {
		this.typeSet = TypeSet.of(types);
		return this;
	}

	@Override
	public TypeSet getTypeSet() {
		return typeSet;
	}
}
