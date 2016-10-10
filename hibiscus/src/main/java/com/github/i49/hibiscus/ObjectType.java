package com.github.i49.hibiscus;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.databind.node.JsonNodeType;

public class ObjectType extends ContainerType {

	private final Map<String, Property> all = new HashMap<>();
	private final Set<Property> required = new HashSet<>();

	public ObjectType(Property[] properties) {
		super(JsonNodeType.OBJECT);
		for (Property p: properties) {
			this.all.put(p.getName(), p);
			if (p.isRequired()) {
				this.required.add(p);
			}
		}
	}

	public boolean containsProperty(String name) {
		return all.containsKey(name); 
	}
	
	public Property getProperty(String name) {
		return this.all.get(name);
	}
	
	public Iterable<Property> required() {
		return required;
	}
}
