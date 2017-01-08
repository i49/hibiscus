package com.github.i49.hibiscus.validation;

import com.github.i49.hibiscus.problems.JsonValueProblem;
import com.github.i49.hibiscus.problems.Problem;

import static org.junit.Assert.*;

import java.util.Locale;
import java.util.logging.Logger;

public class CustomAssertions {
	
	private static final Logger log = Logger.getLogger(CustomAssertions.class.getName());

	public static void assertValid(ValidationResult result) {
		assertNotNull(result.getValue());
		for (Problem p: result.getProblems()) {
			assertValid(p);
		}
 	}
	
	public static void assertValid(Problem problem) {
		assertNotNull(problem.getMessage(Locale.ENGLISH));
		assertNotNull(problem.getDescription());
		assertNotNull(problem.getLocation());
		
		if (problem instanceof JsonValueProblem) {
			JsonValueProblem valueProblem = (JsonValueProblem)problem;
			assertNotNull(valueProblem.getActualValue());
		}

		log.fine(problem.getMessage(Locale.ENGLISH));
	}
}
