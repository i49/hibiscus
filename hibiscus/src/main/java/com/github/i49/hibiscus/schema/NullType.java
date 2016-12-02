package com.github.i49.hibiscus.schema;

import com.github.i49.hibiscus.common.TypeId;

/**
 * JSON null value.
 */
public interface NullType extends AtomicType {

	default TypeId getTypeId() {
		return TypeId.NULL;
	}
}
