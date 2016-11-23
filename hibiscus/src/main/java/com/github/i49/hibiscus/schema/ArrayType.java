package com.github.i49.hibiscus.schema;

import java.util.List;
import java.util.OptionalInt;

import javax.json.JsonArray;
import javax.json.JsonValue;

import com.github.i49.hibiscus.common.TypeId;
import com.github.i49.hibiscus.problems.ArraySizeProblem;
import com.github.i49.hibiscus.problems.ArrayTooLongProblem;
import com.github.i49.hibiscus.problems.ArrayTooShortProblem;
import com.github.i49.hibiscus.problems.Problem;

/**
 * JSON array which can have zero or more values as elements.
 * 
 * <h3>Overview of array type</h3>
 * <p>Array type can impose following constraints on values in JSON document.</p>
 * <ul>
 * <li>minSize</li>
 * <li>maxSize</li>
 * <li>size</li>
 * </ul>
 * 
 * <h3>Array type constraints</h3>
 * 
 * <h4>minSize</h4>
 * <p>{@link #minSize} constrains the minimum number of elements in the array.</p>
 * <blockquote><pre>array(number()).minSize(3);</pre></blockquote>
 *
 * <h4>maxSize</h4>
 * <p>{@link #maxSize} constrains the maximum number of elements in the array.</p>
 * <blockquote><pre>array(number()).maxSize(10);</pre></blockquote>
 *
 * <h4>size</h4>
 * <p>{@link #size} constrains the number of elements in the array.
 * For instance, three-dimensional vector can be defined as follows.</p>
 * <blockquote><pre>array(number()).size(3);</pre></blockquote>
 */
public class ArrayType extends AbstractJsonType implements ComplexType {

	private TypeSet typeSet = TypeSet.empty();
	private OptionalInt minSize = OptionalInt.empty();
	private OptionalInt maxSize = OptionalInt.empty();
	private OptionalInt size = OptionalInt.empty();
	
	/**
	 * Constructs this type.
	 */
	public ArrayType() {
	}
	
	@Override
	public TypeId getTypeId() {
		return TypeId.ARRAY;
	}
	
	@Override
	public void validateInstance(JsonValue value, List<Problem> problems) {
		JsonArray array = (JsonArray)value;
		int size = array.size();
		this.size.ifPresent(expected->{
			if (size != expected) {
				problems.add(new ArraySizeProblem(size, expected));
			}
		});
		this.minSize.ifPresent(limit->{
			if (size < limit) {
				problems.add(new ArrayTooShortProblem(size, limit));
			}
		});
		this.maxSize.ifPresent(limit->{
			if (size > limit) {
				problems.add(new ArrayTooLongProblem(size, limit));
			}
		});
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
	 * Specifies the minimum number of elements in this array. 
	 * @param size the minimum number of elements.
	 * @return this array.
	 * @exception SchemaException if size specified is negative.
	 */
	public ArrayType minSize(int size) {
		checkSize(size);
		this.minSize = OptionalInt.of(size);
		return this;
	}

	/**
	 * Specifies the maximum number of elements in this array. 
	 * @param size the maximum number of elements.
	 * @return this array.
	 * @exception SchemaException if size specified is negative.
	 */
	public ArrayType maxSize(int size) {
		checkSize(size);
		this.maxSize = OptionalInt.of(size);
		return this;
	}
	
	/**
	 * Specifies the number of elements in this array. 
	 * @param size the number of elements.
	 * @return this array.
	 * @exception SchemaException if size specified is negative.
	 */
	public ArrayType size(int size) {
		checkSize(size);
		this.size = OptionalInt.of(size);
		return this;
	}
	
	/**
	 * Checks array size.
	 * @param size the size of array.
	 */
	private static void checkSize(int size) {
		if (size < 0) {
			throw new SchemaException(Messages.ARRAY_SIZE_IS_NEGATIVE(size));
		}
	}
}
