package com.github.i49.hibiscus.schema;

/**
 * A property or <i>field</i> which is a name-value pair composing an {@link ObjectType}.
 * 
 * <p>An instance of this type can be created through
 * {@link SchemaComponents#required SchemaComponents.required()}, 
 * {@link SchemaComponents#optional SchemaComponents.optional()}, or 
 * {@link SchemaComponents#pattern SchemaComponents.pattern()}</p>
 * <blockquote><pre><code>
 * import static com.github.i49.hibiscus.schema.SchemaComponents.*;
 * Property p = required("name", string());
 * </code></pre></blockquote>
 * 
 * @see SchemaComponents
 */
public interface Property {

	/**
	 * Returns the set of types allowed for this property.
	 * @return the set of types allowed for this property.
	 */
	TypeSet getTypeSet();
	
	/**
	 * Returns whether this property is required or not in the containing object.
	 * @return {@code true} if this property is required or {@code false} if this property is optional.
	 * @see #isOptional()
	 */
	boolean isRequired();
	
	/**
	 * Returns whether this property is optional or not in the containing object.
	 * @return {@code true} if this property is optional or {@code false} if this property is required.
	 * @see #isRequired()
	 */
	default boolean isOptional() {
		return !isRequired();
	}

	/**
	 * Matches the given name to this property.
	 * @param name the name of the property which may match this property.
	 * @return {@code true} if the name given matched this property, {@code false} otherwise.
	 */
	boolean matches(String name);
}
