package com.github.i49.hibiscus.schema;

import java.util.function.Predicate;

import javax.json.JsonObject;

import com.github.i49.hibiscus.common.TypeId;
import com.github.i49.hibiscus.facets.Facet;
import com.github.i49.hibiscus.problems.ProblemDescriber;

/**
 * One of built-in types representing JSON object which has {@link TypeId#OBJECT} as a type identifier.
 * 
 * <p>An instance of this type can be created through {@link SchemaComponents#object()}.
 * The method receives instances of {@link Property} allowed to appear in the containing object.</p>
 * <blockquote><pre><code>
 * import static com.github.i49.hibiscus.schema.SchemaComponents.*;
 * ObjectType t = object(property1, property2, ...);
 * </code></pre></blockquote>
 *
 * <h3>Restrictions on this type</h3>
 * <p>This type allows you to impose following restrictions on the value space.</p>
 * <ol>
 * <li>assertion</li>
 * </ol>
 * 
 * <h4>1. assertion</h4>
 * <p><strong>assertion</strong> allows you to make a arbitrary assertion on the values of this type.</p>
 * 
 * @see SchemaComponents
 */
public interface ObjectType extends CompositeType {

	default TypeId getTypeId() {
		return TypeId.OBJECT;
	}

	/**
	 * Declares all properties which this object may have.
	 * <p>If this method is called multiple times for the same object,
	 * all previously declared properties are removed from this object.
	 * </p>
	 * 
	 * @param properties the properties this object may have.
	 * @return this type.
	 * @exception SchemaException if one of properties specified is {@code null}.
	 */
	ObjectType properties(Property... properties);
	
	/**
	 * Allows this object to have properties more than explicitly declared.
	 * By default it will be reported as a problem by validation that an object has properties not declared. 
	 * @return this type.
	 */
	ObjectType moreProperties();
	
	/**
	 * Adds a {@link Facet} which restricts the value space of this type.
	 * @param facet the facet to be added. Cannot be {@code null}.
	 * @return this type.
	 * @exception SchemaException if facet specified is {@code null}.
	 */
	ObjectType facet(Facet<JsonObject> facet);
	
	/**
	 * Makes a assertion on the values of this type.
	 * @param predicate the lambda expression that will return {@code true} if the assertion succeeded or {@code false} if failed.
	 * @param describer the object supplying the description of the problem to be reported when the assertion failed.
	 * @return this type.
	 * @exception SchemaException if any of specified parameters is {@code null}.
	 */
	ObjectType assertion(Predicate<JsonObject> predicate, ProblemDescriber<JsonObject> describer);

	/**
	 * Returns the property of this object which has the specified name. 
	 * @param name the name of the property to be returned. Cannot be {@code null}.
	 * @return a property if this object has the property of specified name, or {@code null} if this object does not have such a property. 
	 */
	Property getProperty(String name);
	
	/**
	 * Returns {@code true} if this object can have properties more than explicitly declared.
	 * @return {@code true} if this object can have properties more than explicitly declared, or {@code false} if it cannot have. 
	 * @see #moreProperties()
	 */
	boolean allowsMoreProperties();
}
