package com.github.i49.hibiscus.schema.problems;

/**
 * Problems that string value does not match predefined pattern.
 */
public class StringPatternProblem extends Problem {

	private final String instanceValue;
	
	public StringPatternProblem(String instanceValue) {
		this.instanceValue = instanceValue;
	}
	
	public String getInstanceValue() {
		return instanceValue;
	}

	@Override
	public String getMessage() {
		StringBuilder b = new StringBuilder();
		b.append("String value does not match predefined pattern. value is: ");
		b.append(getInstanceValue());
		return b.toString();
	}

}
