package com.github.i49.hibiscus.schema.internal;

import com.github.i49.hibiscus.schema.JsonType;
import com.github.i49.hibiscus.schema.Schema;
import com.github.i49.hibiscus.schema.TypeSet;

/**
 * The implementation class of {@link Schema}.
 */
public class SchemaImpl implements Schema {

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
