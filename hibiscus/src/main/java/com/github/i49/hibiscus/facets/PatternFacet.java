package com.github.i49.hibiscus.facets;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.json.JsonString;

import com.github.i49.hibiscus.problems.Problem;
import com.github.i49.hibiscus.problems.StringPatternProblem;

/**
 * <strong>pattern</strong> facet to restrict the value space to the values that match a specified regular expression.
 * <p>
 * This facet is applicable to {@code string()} type only.
 * If the value of the type does not match the specified regular expression,
 * {@link StringPatternProblem} will be reported by this facet. 
 * </p>
 * <p>
 * Important note is that the pattern specified for this facet must be compatible with Java regular expression
 * and not with JavaScript alternative.
 * </p>
 */
public class PatternFacet implements Facet<JsonString> {

	private final Pattern pattern;

	/**
	 * Constructs this facet.
	 * 
	 * @param regex the regular expression which strictly conforms to Java specification
	 *              accurately described in {@link Pattern} class.
	 * @exception PatternSyntaxException If the expression's syntax is invalid.
	 * 
	 * @see Pattern
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