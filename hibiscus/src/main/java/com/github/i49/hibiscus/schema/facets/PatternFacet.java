package com.github.i49.hibiscus.schema.facets;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.json.JsonString;

import com.github.i49.hibiscus.problems.Problem;
import com.github.i49.hibiscus.problems.StringPatternProblem;

/**
 * Facet constraining a value space to those that matches a regular expression.  
 */
public class PatternFacet implements Facet<JsonString> {

	private final Pattern pattern;

	/**
	 * Constructs this facet.
	 * @param regex the string which is compatible with Java regular expression.
	 */
	public PatternFacet(String regex) {
		this.pattern = Pattern.compile(regex);
	}
	
	@Override
	public void apply(JsonString value, List<Problem> problems) {
		Matcher m = pattern.matcher(value.getString());
		if (!m.matches()) {
			problems.add(new StringPatternProblem(value));
		}
	}
}