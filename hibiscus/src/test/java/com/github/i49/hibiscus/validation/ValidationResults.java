package com.github.i49.hibiscus.validation;

import com.github.i49.hibiscus.schema.problems.Problem;

class ValidationResults {
	
	static void printProblems(ValidationResult result) {
		printLine("[Problem(s) Found]");
		if (result.hasProblems()) {
			for (Problem problem: result.getProblems()) {
				printLine(problem.toString());
			}
		} else {
			printLine("No problem found.");
		}
	}

	private static void printLine(String line) {
		System.out.println(line);
	}
	
	private ValidationResults() {
	}
}
