package com.github.i49.hibiscus.schema;

/**
 * Property of JSON object, which is represented a key-value pair.
 */
public class Property {

	private final String name;
	private final TypeSet typeSet;
	private final boolean required;
	
	/**
	 * Creates new property.
	 * @param name the name of this property.
	 * @param type the type of the property value.
	 * @param moreTypes the other types allowed for property value.
	 * @param required whether the property is required or not in containing object.
	 * @return new property.
	 * @exception SchemaException if name is {@code null} or one of types is {@code null} or duplicated.
	 */
	public static Property of(String name, JsonType type, JsonType[] moreTypes, boolean required) {
		if (name == null) {
			throw new SchemaException(Messages.PROPERTY_NAME_IS_NULL());
		}
		return new Property(name, type, moreTypes, required);
	}
	
	/**
	 * Constructs this property.
	 * @param name the name of this property.
	 * @param type the type of the property value.
	 * @param moreTypes the other types allowed for property value.
	 * @param required whether the property is required or not in containing object.
	 */
	private Property(String name, JsonType type, JsonType[] moreTypes, boolean required) {
		this.name = name;
		this.typeSet = TypeSet.of(type, moreTypes);
		this.required = required;
	}
	
	/**
	 * Returns the name of this property.
	 * @return property name.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Returns the types which property value can have.
	 * @return set of types.
	 */
	public TypeSet getTypeSet() {
		return typeSet;
	}
	
	/**
	 * Returns whether this property is required or not in containing object.
	 * @return {@code true} if this property is required or {@code false} if this property is optional.
	 */
	public boolean isRequired() {
		return required;
	}
	
	/**
	 * Returns whether this property is optional or not in containing object.
	 * @return {@code true} if this property is optional or {@code false} if this property is required.
	 */
	public boolean isOptional() {
		return !required;
	}
}
