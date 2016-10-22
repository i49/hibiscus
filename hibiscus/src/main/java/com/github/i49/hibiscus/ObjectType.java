package com.github.i49.hibiscus;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ObjectType extends ContainerType {

	private final Map<String, Property> all = new HashMap<>();
	private final Set<String> required = new HashSet<>();

	public ObjectType properties(Property... properties) {
		for (Property p: properties) {
			this.all.put(p.getKey(), p);
			if (p.isRequired()) {
				this.required.add(p.getKey());
			}
		}
		return this;
	}

	@Override
	public Type getType() {
		return Type.OBJECT;
	}

	@Override
	public boolean isTypeOf(Type type) {
		return (type == Type.OBJECT);
	}

	boolean containsProperty(String name) {
		return all.containsKey(name); 
	}
	
	Property getProperty(String name) {
		return this.all.get(name);
	}
	
	Iterable<String> getRequiredProperties() {
		return required;
	}
}
