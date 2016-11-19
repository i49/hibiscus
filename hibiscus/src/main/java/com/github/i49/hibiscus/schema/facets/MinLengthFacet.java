package com.github.i49.hibiscus.schema.facets;

import java.util.List;

import javax.json.JsonString;

import com.github.i49.hibiscus.problems.Problem;
import com.github.i49.hibiscus.problems.StringTooShortProblem;

/**
 * Facet constraining a value space to values with at least the specific number of characters. 
 */
public class MinLengthFacet implements Facet<JsonString> {

	private final int minLength;
	
	/**
	 * Constructs this facet.
	 * @param minLength the minimum length of the string.
	 */
	public MinLengthFacet(int minLength) {
		this.minLength = minLength;
	}
	
	@Override
	public void apply(JsonString value, List<Problem> problems) {
		int length = value.getString().length();
		if (length < minLength) {
			problems.add(new StringTooShortProblem(value, length, minLength));
		}
	}
}