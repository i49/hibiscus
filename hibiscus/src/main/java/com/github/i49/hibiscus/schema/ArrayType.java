package com.github.i49.hibiscus.schema;


import java.util.function.Predicate;

import javax.json.JsonArray;

import com.github.i49.hibiscus.common.TypeId;
import com.github.i49.hibiscus.facets.Facet;
import com.github.i49.hibiscus.problems.DescriptionSupplier;

/**
 * JSON array which can have zero or more values as elements.
 * 
 * <h3>Array type constraints</h3>
 * <p>Array type can impose following constraints on values in JSON document.</p>
 * <ul>
 * <li>length</li>
 * <li>minLength</li>
 * <li>maxLength</li>
 * <li>unique</li>
 * </ul>
 * 
 * <h4>length</h4>
 * <p>{@link #length length} constrains the number of elements in the array.
 * For instance, three-dimensional vector can be defined as follows.</p>
 * <blockquote><pre>array(number()).length(3);</pre></blockquote>
 *
 * <h4>minLength</h4>
 * <p>{@link #minLength minLength} constrains the minimum number of elements in the array.</p>
 * <blockquote><pre>array(number()).minLength(3);</pre></blockquote>
 *
 * <h4>maxLength</h4>
 * <p>{@link #maxLength maxLength} constrains the maximum number of elements in the array.</p>
 * <blockquote><pre>array(number()).maxLength(10);</pre></blockquote>
 *
 * <h4>unique</h4>
 * <p>{@link #unique unique} specifies that each element in the array must be unique.</p>
 * <blockquote><pre>array(number()).unique();</pre></blockquote>
 */
public interface ArrayType extends CompositeType {

	default TypeId getTypeId() {
		return TypeId.ARRAY;
	}

	/**
	 * Specifies allowed types for elements of this array. 
	 * @param types the types allowed.
	 * @return this array.
	 * @exception SchemaException if one of types specified is {@code null}.
	 */
	ArrayType items(JsonType... types);

	/**
	 * Returns the types allowed for elements of this array.
	 * @return the set of types.
	 */
	TypeSet getItemTypes();

	/**
	 * Adds a facet to this type.
	 * @param facet the facet to be added. Cannot be {@code null}.
	 * @return this type.
	 * @exception SchemaException if facet specified is {@code null}.
	 */
	ArrayType facet(Facet<JsonArray> facet);
	
	/**
	 * Specifies the number of elements in this array. 
	 * @param length the number of elements.
	 * @return this array.
	 * @exception SchemaException if length specified is negative.
	 */
	ArrayType length(int length);
	
	/**
	 * Specifies the minimum number of elements in this array. 
	 * @param length the minimum number of elements.
	 * @return this array.
	 * @exception SchemaException if length specified is negative.
	 */
	ArrayType minLength(int length);

	/**
	 * Specifies the maximum number of elements in this array. 
	 * @param length the maximum number of elements.
	 * @return this array.
	 * @exception SchemaException if length specified is negative.
	 */
	ArrayType maxLength(int length);
	
	/**
	 * Specifies that each element of this array must be unique.
	 * @return this array.
	 */
	ArrayType unique();
	
	/**
	 * Specifies assertion on this type.
	 * @param predicate the lambda expression that will return true if the assertion succeeded or false if failed.
	 * @param description the object to supply a description to be reported when the assertion failed.
	 * @return this type.
	 */
	ArrayType assertion(Predicate<JsonArray> predicate, DescriptionSupplier<JsonArray> description);
}
