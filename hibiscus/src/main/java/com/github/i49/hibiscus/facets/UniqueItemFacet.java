package com.github.i49.hibiscus.facets;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.json.JsonArray;
import javax.json.JsonValue;

import com.github.i49.hibiscus.problems.ArrayDuplicateItemProblem;
import com.github.i49.hibiscus.problems.JsonValueProblem;

/**
 * <strong>unique</strong> facet to enforce each element in array to be unique. 
 * <p>
 * This facet is applicable to {@code array()} type only.
 * If any duplication of values is found in the array,
 * {@link ArrayDuplicateItemProblem} will be reported for each duplicated element excluding the first one.
 * This facet may report more than one problems.
 * </p>
 */
public class UniqueItemFacet implements Facet<JsonArray> {

	/**
	 * The Singleton instance of this facet.
	 */
	public static final UniqueItemFacet INSTANCE = new UniqueItemFacet(); 
	
	@Override
	public void apply(JsonArray value, List<JsonValueProblem> problems) {
		Set<JsonValue> items = new HashSet<>();
		int index = 0;
		for (JsonValue item: value) {
			if (items.contains(item)) {
				problems.add(new ArrayDuplicateItemProblem(index));
			} else {
				items.add(item);
			}
			index++;
		}
	}
}
