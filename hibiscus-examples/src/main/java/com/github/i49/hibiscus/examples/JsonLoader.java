package com.github.i49.hibiscus.examples;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

import javax.json.JsonValue;

import com.github.i49.hibiscus.JsonValidator;
import com.github.i49.hibiscus.ValidationResult;
import com.github.i49.hibiscus.problems.Problem;

public abstract class JsonLoader {

	/**
	 * Loads JSON from specified resource.
	 * @param name name of resource to load
	 * @param validator validator for this JSON
	 * @return JSON value loaded
	 */
	public static JsonValue load(String name, JsonValidator validator) {
	
		InputStream stream = JsonLoader.class.getResourceAsStream(name);
		try (Reader reader = new InputStreamReader(stream, StandardCharsets.UTF_8)) {
			ValidationResult result = validator.validate(reader);
			if (result.hasProblems()) {
				for (Problem p: result.getProblems()) {
					System.out.println(p.getMessage());
				}
			} else {
				System.out.println("No problem found in " + name);
			}
			return result.getValue();
		} catch (IOException e) {
			System.err.println(e);
			return null;
		}
	}
}
