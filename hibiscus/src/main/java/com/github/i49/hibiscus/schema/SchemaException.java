package com.github.i49.hibiscus.schema;

/**
 * Thrown to indicate that a method has been passed an illegal or inappropriate argument
 * while defining a schema.
 */
public class SchemaException extends IllegalArgumentException {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs this exception with the specified detail message.
	 * @param message the detail message.
	 */
	public SchemaException(String message) {
		super(message);
	}
}
