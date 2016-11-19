package com.github.i49.hibiscus.schema;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.json.JsonObject;
import javax.json.JsonValue;

import com.github.i49.hibiscus.common.TypeId;
import com.github.i49.hibiscus.problems.MissingPropertyProblem;
import com.github.i49.hibiscus.problems.Problem;

/**
 * JSON object which can hold zero or more key-value pairs as members.
 */
public class ObjectType extends ComplexType {

	private final Map<String, Property> properties = new HashMap<>();
	private final Set<String> required = new HashSet<>();
	private boolean moreProperties = false;
	
	/**
	 * Constructs this type.
	 */
	public ObjectType() {
	}

	/**
	 * Specifies all properties this object may have.
	 * @param properties the properties this object may have.
	 * @return this object.
	 */
	public ObjectType properties(Property... properties) {
		
		this.properties.clear();
		this.required.clear();

		for (Property p: properties) {
			this.properties.put(p.getName(), p);
			if (p.isRequired()) {
				this.required.add(p.getName());
			}
		}

		return this;
	}
	
	public ObjectType moreProperties() {
		this.moreProperties = true;
		return this;
	}
	
	@Override
	public TypeId getTypeId() {
		return TypeId.OBJECT;
	}

	@Override
	public void validateInstance(JsonValue value, List<Problem> problems) {
		JsonObject object = (JsonObject)value;
		for (String name: this.required) {
			if (!object.containsKey(name)) {
				problems.add(new MissingPropertyProblem(name));
			}
		}
	}

	/**
	 * Returns property which this object has.
	 * @param name name of property.
	 * @return a property if this object has property of specified name or null. 
	 */
	public Property getProperty(String name) {
		return this.properties.get(name);
	}
	
	public boolean allowsMoreProperties() {
		return moreProperties;
	}
}
