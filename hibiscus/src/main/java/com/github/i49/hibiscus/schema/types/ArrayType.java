package com.github.i49.hibiscus.schema.types;

import java.util.List;

import javax.json.JsonArray;
import javax.json.JsonValue;

import com.github.i49.hibiscus.schema.IntRange;
import com.github.i49.hibiscus.schema.TypeId;
import com.github.i49.hibiscus.schema.problems.ArrayTooLongProblem;
import com.github.i49.hibiscus.schema.problems.ArrayTooShortProblem;
import com.github.i49.hibiscus.schema.problems.Problem;

/**
 * JSON array which can have zero or more values as elements.
 */
public class ArrayType extends ComplexType {

	private final TypeSet typeSet;
	private int minItems = -1;
	private int maxItems = -1;

	/**
	 * Creates new instance of array type.
	 * @param itemTypes types allowed for elements of the array. 
	 * @return instance of array type.
	 */
	public static ArrayType of(JsonType[] itemTypes) {
		return new ArrayType(TypeSet.of(itemTypes));
	}
	
	/**
	 * Constructs this array type.
	 * @param typeSet set of types allowed for elements of this array.
	 */
	protected ArrayType(TypeSet typeSet) {
		this.typeSet = typeSet;
	}
	
	@Override
	public TypeId getTypeId() {
		return TypeId.ARRAY;
	}
	
	@Override
	public void validateInstance(JsonValue value, List<Problem> problems) {
		JsonArray array = (JsonArray)value;
		int size = array.size();
		if (minItems != -1 && size < minItems) {
			problems.add(new ArrayTooShortProblem(size, IntRange.of(minItems, maxItems)));
		}
		if (maxItems != -1 && size > maxItems) {
			problems.add(new ArrayTooLongProblem(size, IntRange.of(minItems, maxItems)));
		}
	}

	/**
	 * Returns types allowed for elements of this array.
	 * @return set of types.
	 */
	public TypeSet getItemTypes() {
		return typeSet;
	}

	/**
	 * Specifies minimum number of elements in this array. 
	 * @param size minimum number of elements.
	 * @return this array.
	 */
	public ArrayType minItems(int size) {
		this.minItems = size;
		return this;
	}

	/**
	 * Specifies maximum number of elements in this array. 
	 * @param size maximum number of elements.
	 * @return this array.
	 */
	public ArrayType maxItems(int size) {
		this.maxItems = size;
		return this;
	}
}
