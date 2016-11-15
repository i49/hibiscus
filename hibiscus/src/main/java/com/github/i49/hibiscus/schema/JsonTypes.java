package com.github.i49.hibiscus.schema;

/**
 * Provides utility methods to create building blocks of schema.
 * All methods of this class are static and this class cannot be instantiated.
 */
public class JsonTypes {

	/**
	 * Creates array type.
	 * @param itemTypes types allowed for elements in array.
	 * @return array type.
	 */
	public static ArrayType array(JsonType... itemTypes) {
		return ArrayType.of(itemTypes);
	}

	/**
	 * Creates boolean type.
	 * @return boolean type.
	 */
	public static BooleanType bool() {
		return new BooleanType();
	}

	/**
	 * Creates integer type.
	 * @return integer type.
	 */
	public static IntegerType integer() {
		return new IntegerType();
	}
	
	/**
	 * Creates number type which is also the base type of integer type. 
	 * @return number type.
	 */
	public static NumberType number() {
		return new NumberType();
	}
	
	/**
	 * Creates null type which is immutable.
	 * @return null type.
	 */
	public static NullType nullValue() {
		return NullType.INSTANCE;
	}

	/**
	 * Creates object type.
	 * @param properties properties that object type have.
	 * @return object type.
	 */
	public static ObjectType object(Property... properties) {
		return ObjectType.of(properties);
	}
	
	/**
	 * Creates string type.
	 * @return string type.
	 */
	public static StringType string() {
		return new StringType();
	}
	
	/**
	 * Creates property which is optional for containing object.
	 * @param name name of property.
	 * @param type type of property value.
	 * @param moreTypes other types allowed for property value.
	 * @return property.
	 */
	public static Property optional(String name, JsonType type, JsonType... moreTypes) {
		return Property.of(name, type, moreTypes, false);
	}
	
	/**
	 * Creates property which is required for containing object.
	 * @param name name of property.
	 * @param type type of property value.
	 * @param moreTypes other types allowed for property value.
	 * @return property.
	 */
	public static Property required(String name, JsonType type, JsonType... moreTypes) {
		return Property.of(name, type, moreTypes, true);
	}
	
	private JsonTypes() {
	}
}
