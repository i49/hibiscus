package com.github.i49.hibiscus.schema;

/**
 * Property of JSON object, which is represented a key-value pair.
 */
public class Property {

	private final String name;
	private final TypeSet typeSet;
	private final boolean required;
	
	public static Property of(String name, JsonType type, JsonType[] moreTypes, boolean required) {
		if (name == null) {
			throw new SchemaException(Messages.PROPERTY_NAME_IS_NULL());
		}
		return new Property(name, type, moreTypes, required);
	}
	
	/**
	 * Constructs property.
	 * @param name property name.
	 * @param type type of property value.
	 * @param moreTypes other types allowed for property value.
	 * @param required whether the property is required or not in containing object.
	 */
	private Property(String name, JsonType type, JsonType[] moreTypes, boolean required) {
		this.name = name;
		this.typeSet = TypeSet.of(type, moreTypes);
		this.required = required;
	}
	
	/**
	 * Returns name of this property.
	 * @return property name.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Returns types which property value can have.
	 * @return set of types.
	 */
	public TypeSet getTypeSet() {
		return typeSet;
	}
	
	/**
	 * Returns whether this property is required or not in containing object.
	 * @return true if this property is required or false.
	 */
	public boolean isRequired() {
		return required;
	}
}
