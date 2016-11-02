package com.github.i49.schema.types;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.json.stream.JsonLocation;

import com.github.i49.hibiscus.validation.Property;
import com.github.i49.schema.TypeId;
import com.github.i49.schema.problems.MissingPropertyProblem;
import com.github.i49.schema.problems.Problem;

/**
 * JSON object which can hold zero or more key-value pairs as members.
 */
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

	@Override
	public void validateInstance(JsonValue value, JsonLocation location, List<Problem> problems) {
		JsonObject object = (JsonObject)value;
		for (String key: this.required) {
			if (!object.containsKey(key)) {
				problems.add(new MissingPropertyProblem(key, location));
			}
		}
	}

	public Property getProperty(String name) {
		return this.properties.get(name);
	}
	
	public boolean allowsMoreProperties() {
		return moreProperties;
	}
}
