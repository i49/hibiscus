package com.github.i49.hibiscus.schema;

import com.github.i49.hibiscus.common.TypeId;

/**
 * A schema which describes constraints on the structure and the content of JSON documents to be validated.
 * This object is the top level component for any schema to be built.
 * 
 * <p>An instance of a schema can be created through {@link SchemaComponents#schema()}.
 * The following code shows a sample schema which requires a JSON object at the root of JSON documents.</p>
 * <blockquote><pre><code>
 * import static com.github.i49.hibiscus.schema.SchemaComponents.*;
 * Schema t = schema(object());
 * </code></pre></blockquote>
 * 
 * @see SchemaComponents
 */
public interface Schema {
	
	/**
	 * Specifies all {@link JsonType}s allowed to be at the root of JSON documents.
	 * <p>
	 * If this method is called multiple times on the same instance,
	 * all previously declared types are not preserved and removed from this schema.
	 * </p>
	 * @param types the {@link JsonType}s allowed to be at the root of JSON documents.
	 *              Each type must have a unique {@link TypeId} and cannot be {@code null}.
	 * @return this schema.
	 * @exception SchemaException if one of types given has the same {@link TypeId} as others or {@code null}.
	 */
	Schema types(JsonType... types);
	
	/**
	 * Returns the set of {@link JsonType}s allowed to be at the root of JSON documents.
	 * @return the set of {@link JsonType}s allowed to be at the root of JSON documents.
	 * 
	 * @see #types
	 */
	TypeSet getTypeSet();
}
