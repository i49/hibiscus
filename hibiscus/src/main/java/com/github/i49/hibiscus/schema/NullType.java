package com.github.i49.hibiscus.schema;

import com.github.i49.hibiscus.common.TypeId;

/**
 * JSON type for null value.
 * 
 * <p>This type cannot be restricted further.</p>
 */
public interface NullType extends AtomicType {

	default TypeId getTypeId() {
		return TypeId.NULL;
	}
}
