package com.github.i49.hibiscus.schema;


import java.util.function.Predicate;

import javax.json.JsonArray;

import com.github.i49.hibiscus.common.TypeId;
import com.github.i49.hibiscus.problems.ArrayLengthProblem;
import com.github.i49.hibiscus.problems.ArrayTooLongProblem;
import com.github.i49.hibiscus.problems.ArrayTooShortProblem;
import com.github.i49.hibiscus.problems.DescriptionSupplier;
import com.github.i49.hibiscus.schema.facets.UniqueItemFacet;
import com.github.i49.hibiscus.schema.facets.LengthFacet;
import com.github.i49.hibiscus.schema.facets.MaxLengthFacet;
import com.github.i49.hibiscus.schema.facets.MinLengthFacet;

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
public class ArrayType extends AbstractRestrictableType<JsonArray, ArrayType> implements CompositeType {

	private TypeSet typeSet = TypeSet.empty();
	
	/**
	 * Constructs this type.
	 */
	public ArrayType() {
	}
	
	@Override
	public TypeId getTypeId() {
		return TypeId.ARRAY;
	}
	
	/**
	 * Specifies allowed types for elements of this array. 
	 * @param types the types allowed.
	 * @return this array.
	 * @exception SchemaException if one of types specified is {@code null}.
	 */
	public ArrayType items(JsonType... types) {
		this.typeSet = TypeSet.of(types);
		return this;
	}

	/**
	 * Returns the types allowed for elements of this array.
	 * @return the set of types.
	 */
	public TypeSet getItemTypes() {
		return typeSet;
	}

	/**
	 * Specifies the number of elements in this array. 
	 * @param length the number of elements.
	 * @return this array.
	 * @exception SchemaException if length specified is negative.
	 */
	public ArrayType length(int length) {
		verifyLength(length);
		addFacet(new LengthFacet<JsonArray>(length, ArrayType::getLength, ArrayLengthProblem::new));
		return this;
	}
	
	/**
	 * Specifies the minimum number of elements in this array. 
	 * @param length the minimum number of elements.
	 * @return this array.
	 * @exception SchemaException if length specified is negative.
	 */
	public ArrayType minLength(int length) {
		verifyLength(length);
		addFacet(new MinLengthFacet<JsonArray>(length, ArrayType::getLength, ArrayTooShortProblem::new));
		return this;
	}

	/**
	 * Specifies the maximum number of elements in this array. 
	 * @param length the maximum number of elements.
	 * @return this array.
	 * @exception SchemaException if length specified is negative.
	 */
	public ArrayType maxLength(int length) {
		verifyLength(length);
		addFacet(new MaxLengthFacet<JsonArray>(length, ArrayType::getLength, ArrayTooLongProblem::new));
		return this;
	}
	
	/**
	 * Specifies that each element of this array must be unique.
	 * @return this array.
	 */
	public ArrayType unique() {
		addFacet(UniqueItemFacet.INSTANCE);
		return this;
	}
	
	@Override
	public ArrayType assertion(Predicate<JsonArray> predicate, DescriptionSupplier description) {
		return super.assertion(predicate, description);
	}

	/**
	 * Returns the number of elements in array.
	 * @param value the array value.
	 * @return length of array.
	 */
	private static int getLength(JsonArray value) {
		return value.size();
	}
	
	/**
	 * Verifies value specified as length of array.
	 * @param length the length specified for arrays.
	 */
	private static void verifyLength(int length) {
		if (length < 0) {
			throw new SchemaException(Messages.ARRAY_SIZE_IS_NEGATIVE(length));
		}
	}
}
