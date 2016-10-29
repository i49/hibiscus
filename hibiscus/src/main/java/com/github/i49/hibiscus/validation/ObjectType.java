package com.github.i49.hibiscus.validation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ObjectType extends ContainerType {

	private final Map<String, Property> properties = new HashMap<>();
	private final Set<String> required = new HashSet<>();
	private boolean moreProperties;
	
	private static final ObjectType GENERIC_OBJECT_TYPE = new ObjectType(); 
	
	public static ObjectType of(Property[] properties) {
		return new ObjectType(properties);
	}
	
	public static ObjectType getGeneric() {
		return GENERIC_OBJECT_TYPE;
	}
	
	private ObjectType() {
		moreProperties = true;
	}

	private ObjectType(Property[] properties) {
		for (Property p: properties) {
			this.properties.put(p.getName(), p);
			if (p.isRequired()) {
				this.required.add(p.getName());
			}
		}
		moreProperties = false;
	}
	
	public ObjectType moreProperties() {
		this.moreProperties = true;
		return this;
	}
	
	@Override
	public TypeId getTypeId() {
		return TypeId.OBJECT;
	}

	Property getProperty(String name) {
		return this.properties.get(name);
	}
	
	Iterable<String> getRequiredProperties() {
		return required;
	}
	
	boolean allowsMoreProperties() {
		return moreProperties;
	}
}
