package com.github.i49.schema.types;

import java.util.List;

import javax.json.JsonArray;
import javax.json.JsonValue;

import com.github.i49.schema.TypeId;
import com.github.i49.schema.problems.ArraySizeProblem;
import com.github.i49.schema.problems.Problem;

/**
 * JSON array which can have zero or more values as elements.
 */
public class ArrayType extends ContainerType {

	private final TypeSet typeSet;
	private int minItems = 0;
	private int maxItems = Integer.MAX_VALUE;

	/**
	 * Creates new instance of array type.
	 * @param itemTypes types allowed for elements of the array. 
	 * @return instance of array type.
	 */
	public static ArrayType of(ValueType[] itemTypes) {
		return new ArrayType(TypeSet.of(itemTypes));
	}
	
	/**
	 * Constructs this array type.
	 * @param typeset set of types allowed for elements of this array.
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
		if (size < minItems) {
			problems.add(new ArraySizeProblem(minItems, size));
		}
		if (size > maxItems) {
			problems.add(new ArraySizeProblem(maxItems, size));
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
	 * @param size minimum number of elements
	 * @return this array.
	 */
	public ArrayType minItems(int size) {
		this.minItems = size;
		return this;
	}

	/**
	 * Specifies maximum number of elements in this array. 
	 * @param size maximum number of elements
	 * @return this array.
	 */
	public ArrayType maxItems(int size) {
		this.maxItems = size;
		return this;
	}
}
