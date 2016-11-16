package com.github.i49.hibiscus.schema;

import java.util.List;
import java.util.OptionalInt;

import javax.json.JsonArray;
import javax.json.JsonValue;

import com.github.i49.hibiscus.common.TypeId;
import com.github.i49.hibiscus.problems.ArrayTooLongProblem;
import com.github.i49.hibiscus.problems.ArrayTooShortProblem;
import com.github.i49.hibiscus.problems.Problem;

/**
 * JSON array which can have zero or more values as elements.
 */
public class ArrayType extends ComplexType {

	private TypeSet typeSet = TypeSet.empty();
	private OptionalInt minItems = OptionalInt.empty();
	private OptionalInt maxItems = OptionalInt.empty();
	
	/**
	 * Constructs this array type.
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
		this.minItems.ifPresent(limit->{
			if (size < limit) {
				problems.add(new ArrayTooShortProblem(size, limit));
			}
		});
		this.maxItems.ifPresent(limit->{
			if (size > limit) {
				problems.add(new ArrayTooLongProblem(size, limit));
			}
		});
	}
	
	/**
	 * Specifies allowed types for elements of this array. 
	 * @param types the types allowed.
	 * @return this array.
	 */
	public ArrayType items(JsonType[] types) {
		this.typeSet = TypeSet.of(types);
		return this;
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
		this.minItems = OptionalInt.of(size);
		return this;
	}

	/**
	 * Specifies maximum number of elements in this array. 
	 * @param size maximum number of elements.
	 * @return this array.
	 */
	public ArrayType maxItems(int size) {
		this.maxItems = OptionalInt.of(size);
		return this;
	}
}
