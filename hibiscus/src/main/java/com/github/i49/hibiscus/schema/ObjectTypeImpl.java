package com.github.i49.hibiscus.schema;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import javax.json.JsonObject;
import javax.json.JsonValue;

import com.github.i49.hibiscus.problems.DescriptionSupplier;
import com.github.i49.hibiscus.problems.MissingPropertyProblem;
import com.github.i49.hibiscus.problems.Problem;

class ObjectTypeImpl extends AbstractJsonType<JsonObject, ObjectType> implements ObjectType {

	private final Map<String, Property> properties = new HashMap<>();
	private final Set<String> required = new HashSet<>();
	private boolean moreProperties = false;
	private List<PatternProperty> patternProperties;
	
	/**
	 * Constructs this type.
	 */
	ObjectTypeImpl() {
	}

	@Override
	public ObjectType properties(Property... properties) {
		this.properties.clear();
		this.required.clear();
		addProperties(properties);
		return this;
	}
	
	@Override
	public void validateInstance(JsonValue value, List<Problem> problems) {
		super.validateInstance(value, problems);
		JsonObject object = (JsonObject)value;
		for (String name: this.required) {
			if (!object.containsKey(name)) {
				problems.add(new MissingPropertyProblem(name));
			}
		}
	}

	@Override
	public ObjectType moreProperties() {
		this.moreProperties = true;
		return this;
	}
	
	@Override
	public ObjectType assertion(Predicate<JsonObject> predicate, DescriptionSupplier<JsonObject> description) {
		return super.assertion(predicate, description);
	}

	@Override
	public Property getProperty(String name) {
		if (name == null) {
			return null;
		}
		Property found = this.properties.get(name);
		if (found == null) {
			found = findPatternProperty(name);
		}
		return found;
	}
	
	@Override
	public boolean allowsMoreProperties() {
		return moreProperties;
	}
	
	private void addProperties(Property[] properties) {
		int index = 0;
		for (Property p: properties) {
			if (p == null) {
				throw new SchemaException(Messages.PROPERTY_IS_NULL(index));
			}
			if (p instanceof PatternProperty) {
				addProperty((PatternProperty)p);
			} else {
				addProperty((NamedProperty)p);
			}
			index++;
		}
	}
	
	private void addProperty(PatternProperty property) {
		if (patternProperties == null) {
			patternProperties = new ArrayList<>();
		}
		patternProperties.add(property);
	}

	private void addProperty(NamedProperty property) {
		this.properties.put(property.getName(), property);
		if (property.isRequired()) {
			this.required.add(property.getName());
		}
	}
	
	private PatternProperty findPatternProperty(String name) {
		if (patternProperties == null) {
			return null;
		}
		for (PatternProperty p: patternProperties) {
			if (p.matches(name)) {
				return p;
			}
		}
		return null;
	}
}
