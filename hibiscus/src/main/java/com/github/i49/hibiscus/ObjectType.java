package com.github.i49.hibiscus;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ObjectType extends ContainerType {

	private final Map<String, Property> properties = new HashMap<>();
	private final Set<String> required = new HashSet<>();
	private boolean moreProperties = false;
	
	public ObjectType(Property[] properties) {
		for (Property p: properties) {
			this.properties.put(p.getKey(), p);
			if (p.isRequired()) {
				this.required.add(p.getKey());
			}
		}
	}
	
	public ObjectType moreProperties() {
		this.moreProperties = true;
		return this;
	}
	
	@Override
	public TypeId getType() {
		return TypeId.OBJECT;
	}

	@Override
	public boolean isTypeOf(TypeId type) {
		return (type == TypeId.OBJECT);
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
