package com.github.i49.schema.types;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.github.i49.hibiscus.validation.Property;
import com.github.i49.schema.TypeId;

public class ObjectType extends ContainerType {

	private final Map<String, Property> properties = new HashMap<>();
	private final Set<String> required = new HashSet<>();
	private boolean moreProperties = false;
	
	public static ObjectType of(Property[] properties) {
		return new ObjectType(properties);
	}
	
	protected ObjectType() {
	}

	private ObjectType(Property[] properties) {
		for (Property p: properties) {
			this.properties.put(p.getName(), p);
			if (p.isRequired()) {
				this.required.add(p.getName());
			}
		}
	}
	
	public ObjectType moreProperties() {
		this.moreProperties = true;
		return this;
	}
	
	@Override
	public TypeId getTypeId() {
		return TypeId.OBJECT;
	}

	public Property getProperty(String name) {
		return this.properties.get(name);
	}
	
	public Iterable<String> getRequiredProperties() {
		return required;
	}
	
	public boolean allowsMoreProperties() {
		return moreProperties;
	}
}
