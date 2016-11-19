package com.github.i49.hibiscus.schema.facets;

import java.util.List;

import javax.json.JsonString;

import com.github.i49.hibiscus.problems.Problem;
import com.github.i49.hibiscus.problems.StringTooLongProblem;

/**
 * Facet constraining a value space to values with at most the specific number of characters. 
 */
public class MaxLengthFacet implements Facet<JsonString> {

	private final int maxLength;
	
	/**
	 * Constructs this facet.
	 * @param maxLength the maximum length of the string.
	 */
	public MaxLengthFacet(int maxLength) {
		this.maxLength = maxLength;
	}
	
	@Override
	public void apply(JsonString value, List<Problem> problems) {
		int length = value.getString().length();
		if (length > maxLength) {
			problems.add(new StringTooLongProblem(value, length, maxLength));
		}
	}
}