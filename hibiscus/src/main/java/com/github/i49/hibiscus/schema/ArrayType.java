package com.github.i49.hibiscus.schema;


import java.util.function.Predicate;

import javax.json.JsonArray;

import com.github.i49.hibiscus.common.TypeId;
import com.github.i49.hibiscus.facets.Facet;
import com.github.i49.hibiscus.problems.ProblemDescriber;

/**
 * One of built-in types representing JSON array which has {@link TypeId#ARRAY} as a type identifier.
 * 
 * <p>An instance of this type can be created through {@link SchemaComponents#array()}.
 * The method receives instances of {@link JsonType} allowed to appear as an element of the array. 
 * </p>
 * <blockquote><pre><code>
 * import static com.github.i49.hibiscus.schema.SchemaComponents.*;
 * ArrayType t = array(type1, type2, ...);
 * </code></pre></blockquote>
 * 
 * <h3>Restrictions on this type</h3>
 * <p>This type allows you to impose following restrictions on the value space.</p>
 * <ol>
 * <li>length</li>
 * <li>minLength</li>
 * <li>maxLength</li>
 * <li>unique</li>
 * <li>assertion</li>
 * </ol>
 * 
 * <h4>1. length</h4>
 * <p><strong>length</strong> restricts the number of elements in the array.
 * For instance, three-dimensional vector can be defined as follows.</p>
 * <blockquote><pre><code>array(number()).length(3);</code></pre></blockquote>
 *
 * <h4>2. minLength</h4>
 * <p><strong>minLength</strong> restricts the minimum number of elements required in the array.</p>
 * <blockquote><pre><code>array(number()).minLength(3);</code></pre></blockquote>
 *
 * <h4>3. maxLength</h4>
 * <p><strong>maxLength</strong> restricts the maximum number of elements allowed in the array.</p>
 * <blockquote><pre><code>array(number()).maxLength(10);</code></pre></blockquote>
 *
 * <h4>4. unique</h4>
 * <p><strong>unique</strong> specifies that each value of element in the array must be unique.</p>
 * <blockquote><pre><code>array(number()).unique();</code></pre></blockquote>
 * 
 * <h4>5. assertion</h4>
 * <p><strong>assertion</strong> allows you to make a arbitrary assertion on the values of this type.</p>
 *
 * @see SchemaComponents
 */
public interface ArrayType extends CompositeType {

	default TypeId getTypeId() {
		return TypeId.ARRAY;
	}

	/**
	 * Specifies types allowed for elements of this array. 
	 * @param types the types allowed.
	 * @return this type.
	 * @exception SchemaException if one of types specified is {@code null}.
	 */
	ArrayType items(JsonType... types);

	/**
	 * Returns the set of types allowed for elements of this array.
	 * @return the set of types.
	 */
	TypeSet getItemTypes();

	/**
	 * Adds a {@link Facet} which restricts the value space of this type.
	 * @param facet the facet to be added. Cannot be {@code null}.
	 * @return this type.
	 * @exception SchemaException if facet specified is {@code null}.
	 */
	ArrayType facet(Facet<JsonArray> facet);
	
	/**
	 * Specifies the number of elements expected in this array. 
	 * @param length the number of elements. Must be non-negative value.
	 * @return this type.
	 * @exception SchemaException if length specified is negative.
	 */
	ArrayType length(int length);
	
	/**
	 * Specifies the minimum number of elements required in this array. 
	 * @param length the minimum number of elements. Must be non-negative value.
	 * @return this type.
	 * @exception SchemaException if length specified is negative.
	 */
	ArrayType minLength(int length);

	/**
	 * Specifies the maximum number of elements allowed in this array. 
	 * @param length the maximum number of elements. Must be non-negative value.
	 * @return this type.
	 * @exception SchemaException if length specified is negative.
	 */
	ArrayType maxLength(int length);
	
	/**
	 * Specifies that each element of this array must be unique.
	 * @return this type.
	 */
	ArrayType unique();
	
	/**
	 * Makes a assertion on the values of this type.
	 * @param predicate the lambda expression that will return {@code true} if the assertion succeeded or {@code false} if failed.
	 * @param describer the object supplying the description of the problem to be reported when the assertion failed.
	 * @return this type.
	 * @exception SchemaException if any of specified parameters is {@code null}.
	 */
	ArrayType assertion(Predicate<JsonArray> predicate, ProblemDescriber<JsonArray> describer);
}
