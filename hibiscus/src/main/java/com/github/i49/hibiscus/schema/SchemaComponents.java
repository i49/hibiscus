package com.github.i49.hibiscus.schema;

/**
 * Provides utility methods to create building blocks of the schema.
 * <p>All methods of this class are static and this class cannot be instantiated.</p>
 */
public final class SchemaComponents {

	/**
	 * Creates a schema.
	 * @return created schema object.
	 */
	public static Schema schema() {
		return new SchemaImpl();
	}
	
	/**
	 * Creates a schema with root types.
	 * @param types the JSON types allowed to be at root of JSON document.
	 * @return created schema object.
	 */
	public static Schema schema(JsonType... types) {
		return schema().types(types);
	}
	
	/**
	 * Creates an array type.
	 * @return created array type.
	 */
	public static ArrayType array() {
		return new ArrayTypeImpl();
	}

	/**
	 * Creates array type with element types.
	 * Calling this methods is equivalent to {@code array().items(...)}. 
	 * 
	 * @param types the types allowed for elements of the array. Each type cannot be {@code null}.
	 * @return created array type.
	 * @exception SchemaException if one of types specified is {@code null} or duplicated.
	 */
	public static ArrayType array(JsonType... types) {
		return array().items(types);
	}

	/**
	 * Creates boolean type.
	 * @return boolean type.
	 */
	public static BooleanType bool() {
		return new BooleanTypeImpl();
	}

	/**
	 * Creates integer type.
	 * @return integer type.
	 */
	public static IntegerType integer() {
		return new IntegerTypeImpl();
	}
	
	/**
	 * Creates number type which is also the base type of integer type. 
	 * @return number type.
	 */
	public static NumberType number() {
		return new NumberTypeImpl();
	}
	
	/**
	 * Creates null type which is immutable.
	 * @return null type.
	 */
	public static NullType nil() {
		return NullTypeImpl.INSTANCE;
	}

	/**
	 * Creates object type.
	 * @return object type.
	 */
	public static ObjectType object() {
		return new ObjectTypeImpl();
	}
	
	/**
	 * Creates object type with properties.
	 * Calling this methods is equivalent to {@code object().properties(...)}. 
	 * 
	 * @param properties the properties that object type have. Each property cannot be {@code null}. 
	 * @return object type.
	 * @exception SchemaException if one of properties specified is {@code null}.
	 */
	public static ObjectType object(Property... properties) {
		return object().properties(properties);
	}
	
	/**
	 * Creates string type.
	 * @return string type.
	 */
	public static StringType string() {
		return new StringTypeImpl();
	}
	
	/**
	 * Creates a property which is optional for the containing object.
	 * @param name the name of property. Cannot be {@code null}.
	 * @param type the type of property value. Cannot be {@code null}.
	 * @param moreTypes the other types allowed for property value. Each type cannot be {@code null}.
	 * @return created property.
	 * @exception SchemaException if name is {@code null} or one of types is {@code null} or duplicated.
	 */
	public static Property optional(String name, JsonType type, JsonType... moreTypes) {
		return new NamedProperty(name, type, moreTypes, false);
	}
	
	/**
	 * Creates a property which is required for the containing object.
	 * @param name the name of property. Cannot be {@code null}.
	 * @param type the type of property value. Cannot be {@code null}.
	 * @param moreTypes the other types allowed for property value. Each type cannot be {@code null}.
	 * @return created property.
	 * @exception SchemaException if name is {@code null} or one of types is {@code null} or duplicated.
	 */
	public static Property required(String name, JsonType type, JsonType... moreTypes) {
		return new NamedProperty(name, type, moreTypes, true);
	}
	
	/**
	 * Creates property which name is specified as a regular expression.
	 * @param pattern the pattern of the name specified as a Java regular expression. Cannot be {@code null}.
	 * @param type the type of property value. Cannot be {@code null}.
	 * @param moreTypes the other types allowed for property value. Each type cannot be {@code null}.
	 * @return created property.
	 * @exception SchemaException if pattern is {@code null} or one of types is {@code null} or duplicated.
	 */
	public static Property pattern(String pattern, JsonType type, JsonType... moreTypes) {
		return new PatternProperty(pattern, type, moreTypes);
	}
	
	private SchemaComponents() {
	}
}
