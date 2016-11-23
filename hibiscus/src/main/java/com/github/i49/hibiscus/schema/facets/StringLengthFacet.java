package com.github.i49.hibiscus.schema.facets;

import java.util.List;

import javax.json.JsonString;

import com.github.i49.hibiscus.problems.Problem;
import com.github.i49.hibiscus.problems.StringLengthProblem;

public class StringLengthFacet implements Facet<JsonString> {

	private final int expectedLength;
	
	public StringLengthFacet(int expectedLength) {
		this.expectedLength = expectedLength;
	}

	@Override
	public void apply(JsonString value, List<Problem> problems) {
		int length = value.getString().length();
		if (length != expectedLength) {
			problems.add(new StringLengthProblem(value, length, expectedLength));
		}
	}
}
