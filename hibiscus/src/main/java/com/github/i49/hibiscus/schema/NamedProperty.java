package com.github.i49.hibiscus.schema;

/**
 * Property that has a name. 
 */
class NamedProperty implements Property {

	private final String name;
	private final TypeSet typeSet;
	private final boolean required;

	/**
	 * Creates new property.
	 * @param name the name of this property.
	 * @param type the type of this property value.
	 * @param moreTypes the other types allowed for this property value.
	 * @param required whether this property is required or not in the containing object.
	 * @return new property.
	 * @exception SchemaException if name is {@code null} or one of types is {@code null} or duplicated.
	 */
	NamedProperty(String name, JsonType type, JsonType[] moreTypes, boolean required) {
		if (name == null) {
			throw new SchemaException(Messages.PROPERTY_NAME_IS_NULL());
		}
		this.name = name;
		this.typeSet = TypeSet.of(type, moreTypes);
		this.required = required;
	}

	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public TypeSet getTypeSet() {
		return typeSet;
	}
	
	@Override
	public boolean isRequired() {
		return required;
	}
}
