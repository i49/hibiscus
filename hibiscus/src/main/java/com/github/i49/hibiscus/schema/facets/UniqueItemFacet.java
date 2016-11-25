package com.github.i49.hibiscus.schema.facets;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.json.JsonArray;
import javax.json.JsonValue;

import com.github.i49.hibiscus.problems.ArrayDuplicateItemProblem;
import com.github.i49.hibiscus.problems.Problem;

public class UniqueItemFacet implements Facet<JsonArray> {

	public static final UniqueItemFacet INSTANCE = new UniqueItemFacet(); 
	
	@Override
	public void apply(JsonArray value, List<Problem> problems) {
		Set<JsonValue> items = new HashSet<>();
		int index = 0;
		for (JsonValue item: value) {
			if (items.contains(item)) {
				problems.add(new ArrayDuplicateItemProblem(value, index));
			} else {
				items.add(item);
			}
			index++;
		}
	}
}
