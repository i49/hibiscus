package com.github.i49.hibiscus.schema.internal;

import com.github.i49.hibiscus.common.TypeId;
import com.github.i49.hibiscus.schema.JsonType;
import com.github.i49.hibiscus.schema.Property;
import com.github.i49.hibiscus.schema.SchemaException;
import com.github.i49.hibiscus.schema.TypeSet;

/**
 * An object property that has a determined name. 
 */
public class NamedProperty implements Property {

	private final String name;
	private final TypeSet typeSet;
	private final boolean required;

	/**
	 * Constructs this property.
	 * @param name the name of this property. Cannot be {@code null}.
	 * @param type the type of this property value. Cannot be {@code null}.
	 * @param moreTypes the other types allowed for this property value. Each type cannot be {@code null}.
	 * @param required whether this property is required or not in the containing object.
	 * @exception SchemaException if name is {@code null} or
	 *                            if one of types has the same {@link TypeId} as others or {@code null}.
	 */
	public NamedProperty(String name, JsonType type, JsonType[] moreTypes, boolean required) {
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
