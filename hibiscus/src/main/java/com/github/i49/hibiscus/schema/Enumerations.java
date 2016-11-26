package com.github.i49.hibiscus.schema;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

import javax.json.JsonNumber;
import javax.json.JsonValue;

import com.github.i49.hibiscus.json.JsonValues;

/**
 * Class to help implement enumeration facets.
 */
final class Enumerations {

	/**
	 * Creates a set of values.
	 * @param values the values that belong to the set. 
	 * @return a set of values.
	 * @exception SchemaException if one of values is {@code null}.
	 * @param <T> type of {@code JsonValue}.
	 * @param <V> type of each value of valueSet.
	 */
	static <T extends JsonValue, V> Set<T> valueSet(Function<V, T> mapper, V[] values) {
		Set<T> valueSet = new HashSet<>();
		int index = 0;
		for (V value: values) {
			if (value == null) {
				throw new SchemaException(Messages.ONE_OF_VALUES_IS_NULL(index));
			}
			T mapped = mapper.apply(value);
			if (mapped == null) {
				throw new SchemaException(Messages.ONE_OF_VALUES_IS_NULL(index));
			}
			valueSet.add(mapped);
			index++;
		}
		return valueSet;
	}
	
	/**
	 * Creates a set of boolean values.
	 * @param values the values that belong to the set. 
	 * @return a set of boolean values.
	 */
	static Set<JsonValue> valueSet(boolean[] values) {
		Set<JsonValue> valueSet = new HashSet<>();
		for (boolean value: values) {
			valueSet.add(JsonValues.createBoolean(value));
		}
		return valueSet;
	}
	
	/**
	 * Creates a set of long values.
	 * @param values the values that belong to the set. 
	 * @return a set of long values.
	 */
	static Set<JsonNumber> valueSet(long[] values) {
		Set<JsonNumber> valueSet = new HashSet<>();
		for (long value: values) {
			valueSet.add(JsonValues.createNumber(value));
		}
		return valueSet;
	}
	
	private Enumerations() {
	}
}
