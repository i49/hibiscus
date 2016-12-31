package com.github.i49.hibiscus.schema;

import com.github.i49.hibiscus.common.TypeId;

/**
 * One of built-in types representing JSON null which has {@link TypeId#NULL} as a type identifier.
 * 
 * <p>An instance of this type can be created through {@link SchemaComponents#nil()}.</p>
 * <blockquote><pre><code>
 * import static com.github.i49.hibiscus.schema.SchemaComponents.*;
 * NullType t = nil();
 * </code></pre></blockquote>
 * 
 * <p>This type does not allow to be restricted further.</p>
 *
 * @see SchemaComponents
 */
public interface NullType extends AtomicType {

	default TypeId getTypeId() {
		return TypeId.NULL;
	}
}
