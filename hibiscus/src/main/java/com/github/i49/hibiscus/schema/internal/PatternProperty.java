package com.github.i49.hibiscus.schema.internal;

import com.github.i49.hibiscus.schema.JsonType;
import com.github.i49.hibiscus.schema.Property;
import com.github.i49.hibiscus.schema.TypeSet;

/**
 * A skeletal implementation of a pattern property.
 */
abstract class PatternProperty implements Property {

	private final TypeSet typeSet;
	
	PatternProperty(JsonType type, JsonType[] moreTypes) {
		this.typeSet = TypeSet.of(type, moreTypes);
	}

	@Override
	public TypeSet getTypeSet() {
		return typeSet;
	}

	/**
	 * Returns whether this property is required or not in the containing object.
	 * @return {@code false} always. All pattern properties are treated as optional.
	 */
	@Override
	public boolean isRequired() {
		return false;
	}
}
