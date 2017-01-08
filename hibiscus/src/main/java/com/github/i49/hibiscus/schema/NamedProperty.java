package com.github.i49.hibiscus.schema;

/**
 * An object property that has a determined name. 
 */
public interface NamedProperty extends Property {

	/**
	 * Returns the name of this property.
	 * @return the name of this property.
	 */
	String getName();
}
