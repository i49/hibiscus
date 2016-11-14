package com.github.i49.hibiscus.schema;

import static com.github.i49.hibiscus.schema.Arguments.*;

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
		return BooleanType.getDefault();
	}

	/**
	 * Creates integer type.
	 * @return integer type.
	 */
	public static IntegerType integer() {
		return IntegerType.getDefault();
	}
	
	/**
	 * Creates number type which is also the base type of integer type. 
	 * @return number type.
	 */
	public static NumberType number() {
		return NumberType.getDefault();
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
		return StringType.getDefault();
	}
	
	/**
	 * Creates property which is optional for containing object.
	 * @param name name of property.
	 * @param type type of property value.
	 * @param moreTypes other types allowed for property value.
	 * @return property.
	 */
	public static Property optional(String name, JsonType type, JsonType... moreTypes) {
		return createProperty(name, type, moreTypes, false);
	}
	
	/**
	 * Creates property which is required for containing object.
	 * @param name name of property.
	 * @param type type of property value.
	 * @param moreTypes other types allowed for property value.
	 * @return property.
	 */
	public static Property required(String name, JsonType type, JsonType... moreTypes) {
		return createProperty(name, type, moreTypes, true);
	}
	
	private static Property createProperty(String name, JsonType type, JsonType[] moreTypes, boolean required) {
		checkNotNull(name, ()->Messages.PROPERTY_NAME_IS_NULL());
		checkNotNull(type, moreTypes, i->Messages.PROPERTY_TYPE_IS_NULL(i));
		return new Property(name, type, moreTypes, required);
	}
	
	private JsonTypes() {
	}
}
