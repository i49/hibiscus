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

import com.github.i49.hibiscus.common.TypeId;
import com.github.i49.hibiscus.problems.DescriptionSupplier;
import com.github.i49.hibiscus.problems.MissingPropertyProblem;
import com.github.i49.hibiscus.problems.Problem;

/**
 * JSON object which can hold zero or more key-value pairs as its members.
 */
public class ObjectType extends AbstractRestrictableType<JsonObject, ObjectType> implements CompositeType {

	private final Map<String, Property> properties = new HashMap<>();
	private final Set<String> required = new HashSet<>();
	private boolean moreProperties = false;
	private List<PatternProperty> patternProperties;
	
	/**
	 * Constructs this type.
	 */
	public ObjectType() {
	}

	/**
	 * Declares all properties this object may have.
	 * 
	 * If this method is called multiple times for the same object,
	 * all previously declared properties are removed from this object.
	 * 
	 * @param properties the properties this object may have.
	 * @return this object.
	 * @exception SchemaException if one of properties specified is {@code null}.
	 */
	public ObjectType properties(Property... properties) {
		this.properties.clear();
		this.required.clear();
		addProperties(properties);
		return this;
	}
	
	@Override
	public TypeId getTypeId() {
		return TypeId.OBJECT;
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

	/**
	 * Permits this object to have more properties than explicitly declared.
	 * By default it will be reported as problem by validation when an object has properties not declared. 
	 * @return this object.
	 */
	public ObjectType moreProperties() {
		this.moreProperties = true;
		return this;
	}
	
	@Override
	public ObjectType assertion(Predicate<JsonObject> predicate, DescriptionSupplier<JsonObject> description) {
		return super.assertion(predicate, description);
	}

	/**
	 * Returns property which this object has.
	 * @param name the name of property.
	 * @return a property if this object has property of specified name, or {@code null} if this object does not have. 
	 */
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
	
	/**
	 * Returns true if this object can have properties not explicitly declared.
	 * @return true if this object can have properties not explicitly declared, or false if it cannot have. 
	 */
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
