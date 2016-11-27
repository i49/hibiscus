package com.github.i49.hibiscus.schema;

/**
 * Property of JSON object, which is represented a key-value pair.
 */
public interface Property {

	/**
	 * Returns the name of this property.
	 * @return property name.
	 */
	String getName();
	
	/**
	 * Returns the types allowed for this property.
	 * @return the set of types allowed for this property.
	 */
	TypeSet getTypeSet();
	
	/**
	 * Returns whether this property is required or not in the containing object.
	 * @return {@code true} if this property is required or {@code false} if this property is optional.
	 */
	boolean isRequired();
	
	/**
	 * Returns whether this property is optional or not in the containing object.
	 * @return {@code true} if this property is optional or {@code false} if this property is required.
	 */
	default boolean isOptional() {
		return !isRequired();
	}
}
