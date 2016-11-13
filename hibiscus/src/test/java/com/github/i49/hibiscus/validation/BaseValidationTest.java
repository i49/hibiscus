package com.github.i49.hibiscus.validation;

import org.junit.After;
import org.junit.Before;

import com.github.i49.hibiscus.problems.Problem;

public abstract class BaseValidationTest {

	protected ValidationResult result;
	
	@Before
	public void setUp() {
		result = null;
	}
	
	@After
	public void tearDown() {
		if (result != null && result.hasProblems()) {
			for (Problem problem: result.getProblems()) {
				System.out.println(problem);
			}
		}
	}
}
