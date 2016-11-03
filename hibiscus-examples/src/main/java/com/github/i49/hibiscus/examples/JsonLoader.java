package com.github.i49.hibiscus.examples;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

import javax.json.JsonValue;

import com.github.i49.hibiscus.schema.problems.Problem;
import com.github.i49.hibiscus.validation.JsonValidator;
import com.github.i49.hibiscus.validation.ValidationResult;

public abstract class JsonLoader {

	/**
	 * Loads JSON from specified resource.
	 * @param name name of resource to load
	 * @param validator validator for this JSON
	 * @return JSON value loaded
	 */
	public static JsonValue load(String name, JsonValidator validator) {
	
		System.out.println("Validating JSON file: \"" + name + "\"");

		InputStream stream = JsonLoader.class.getResourceAsStream(name);
		try (Reader reader = new InputStreamReader(stream, StandardCharsets.UTF_8)) {
			long startTime = System.currentTimeMillis();
			ValidationResult result = validator.validate(reader);
			long endTime = System.currentTimeMillis();
			if (result.hasProblems()) {
				for (Problem p: result.getProblems()) {
					System.out.println(p);
				}
			} else {
				System.out.println("No problem found.");
			}
			System.out.println("time: " + (endTime - startTime) + " [ms]");
			System.out.println();
			return result.getValue();
		} catch (IOException e) {
			System.err.println(e);
			return null;
		}
	}
}
