package com.github.i49.hibiscus.examples;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

import com.github.i49.hibiscus.JsonValidator;
import com.github.i49.hibiscus.ValidationResult;
import com.github.i49.hibiscus.problems.Problem;

public abstract class Example {

	public static void run(String name, JsonValidator validator) {
	
		InputStream stream = Example.class.getResourceAsStream(name);
		try (Reader reader = new InputStreamReader(stream, StandardCharsets.UTF_8)) {
			ValidationResult result = validator.validate(reader);
			if (result.hasProblems()) {
				for (Problem p: result.getProblems()) {
					System.out.println(p.getMessage());
				}
			} else {
				System.out.println("No problem found in " + name);
			}
		} catch (IOException e) {
			System.err.println(e);
		}
	}
}
