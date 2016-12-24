package com.github.i49.hibiscus.common;

/**
 * Identifiers of seven built-in types available in JSON schema definitions.
 * <p>
 * Each constants in this enumeration has a corresponding type class
 * in {@link com.github.i49.hibiscus.schema} package.
 * These types can be instantiated by methods provided by
 * {@link com.github.i49.hibiscus.schema.SchemaComponents SchemaComponents}. 
 * </p>
 * <p>
 * Basically, matching of types between JSON document and JSON schema will be
 * achieved by comparing their identifiers defined in this enumeration.
 * </p>
 */
public enum TypeId {

	/** 
	 * The identifier of JSON array type, 
	 * which is represented by {@link com.github.i49.hibiscus.schema.ArrayType ArrayType} class.
	 * @see com.github.i49.hibiscus.schema.ArrayType 
	 * @see com.github.i49.hibiscus.schema.SchemaComponents#array()
	 */
	ARRAY,

	/** 
	 * The identifier of JSON boolean type,
	 * which is represented by {@link com.github.i49.hibiscus.schema.BooleanType BooleanType} class.
	 * @see com.github.i49.hibiscus.schema.BooleanType 
	 * @see com.github.i49.hibiscus.schema.SchemaComponents#bool()
	 */
	BOOLEAN,

	/**
	 * The identifier of JSON number without a fraction or exponent part,
	 * which is represented by {@link com.github.i49.hibiscus.schema.IntegerType IntegerType} class.
	 * @see com.github.i49.hibiscus.schema.IntegerType 
	 * @see com.github.i49.hibiscus.schema.SchemaComponents#integer()
	 */
	INTEGER,

	/**
	 * The identifier of JSON number type,
	 * which is represented by {@link com.github.i49.hibiscus.schema.NumberType NumberType} class.
	 * This type is the base type of integer type.
	 * @see com.github.i49.hibiscus.schema.NumberType 
	 * @see com.github.i49.hibiscus.schema.SchemaComponents#number()
	 */
	NUMBER,

	/**
	 * The identifier of JSON null type,
	 * which is represented by {@link com.github.i49.hibiscus.schema.NullType NullType} class.
	 * @see com.github.i49.hibiscus.schema.NullType 
	 * @see com.github.i49.hibiscus.schema.SchemaComponents#nil()
	 */
	NULL,

	/**
	 * The identifier of JSON object type,
	 * which is represented by {@link com.github.i49.hibiscus.schema.ObjectType ObjectType} class.
	 * @see com.github.i49.hibiscus.schema.ObjectType 
	 * @see com.github.i49.hibiscus.schema.SchemaComponents#object()
	 */
	OBJECT,

	/**
	 * The identifier of JSON string type,
	 * which is represented by {@link com.github.i49.hibiscus.schema.StringType StringType} class.
	 * @see com.github.i49.hibiscus.schema.StringType 
	 * @see com.github.i49.hibiscus.schema.SchemaComponents#string()
	 */
	STRING;
}
