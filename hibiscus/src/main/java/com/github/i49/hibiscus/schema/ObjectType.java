package com.github.i49.hibiscus.schema;

import java.util.function.Predicate;

import javax.json.JsonObject;

import com.github.i49.hibiscus.common.TypeId;
import com.github.i49.hibiscus.problems.DescriptionSupplier;

/**
 * JSON object which can hold zero or more key-value pairs as its members.
 */
public interface ObjectType extends CompositeType {

	default TypeId getTypeId() {
		return TypeId.OBJECT;
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
	ObjectType properties(Property... properties);
	
	/**
	 * Permits this object to have more properties than explicitly declared.
	 * By default it will be reported as problem by validation when an object has properties not declared. 
	 * @return this object.
	 */
	ObjectType moreProperties();
	
	/**
	 * Specifies assertion on this type.
	 * @param predicate the lambda expression that will return true if the assertion succeeded or false if failed.
	 * @param description the object to supply a description to be reported when the assertion failed.
	 * @return this type.
	 */
	ObjectType assertion(Predicate<JsonObject> predicate, DescriptionSupplier<JsonObject> description);

	/**
	 * Returns property which this object has.
	 * @param name the name of property.
	 * @return a property if this object has property of specified name, or {@code null} if this object does not have. 
	 */
	Property getProperty(String name);
	
	/**
	 * Returns true if this object can have properties not explicitly declared.
	 * @return true if this object can have properties not explicitly declared, or false if it cannot have. 
	 */
	boolean allowsMoreProperties();
}
