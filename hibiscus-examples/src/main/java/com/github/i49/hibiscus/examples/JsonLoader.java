package com.github.i49.hibiscus.examples;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonStructure;
import javax.json.JsonValue;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;

import com.github.i49.hibiscus.problems.Problem;
import com.github.i49.hibiscus.validation.JsonValidator;
import com.github.i49.hibiscus.validation.ValidationResult;

/**
 * Utility class in order to show results of validation of JSON.
 */
class JsonLoader {
	
	private static final JsonWriterFactory writerFactory;
	
	static {
		Map<String, Object> config = new HashMap<>();
		config.put(JsonGenerator.PRETTY_PRINTING, true);
		writerFactory = Json.createWriterFactory(config);
	}

	/**
	 * Loads JSON content from specified resource.
	 * @param name name of resource to load.
	 * @param validator validator for this JSON content.
	 * @return JSON value loaded.
	 */
	public static JsonValue load(String name, JsonValidator validator) {
	
		System.out.println("Validating JSON file: \"" + name + "\"");

		InputStream stream = JsonLoader.class.getResourceAsStream(name);
		try (Reader reader = new InputStreamReader(stream, StandardCharsets.UTF_8)) {
			long startTime = System.currentTimeMillis();
			ValidationResult result = validator.validate(reader);
			long endTime = System.currentTimeMillis();
			long elapsed = endTime - startTime;
			printProblems(result);
			System.out.println("Time elapsed: " + elapsed + " ms");
			printValue(result);
			return result.getValue();
		} catch (IOException e) {
			System.err.println(e.toString());
			return null;
		}
	}
	
	private static void printProblems(ValidationResult result) {
		if (result.hasProblems()) {
			for (Problem p: result.getProblems()) {
				System.out.println(p.toString());
			}
		} else {
			System.out.println("No problem found.");
		}
	}
	
	private static void printValue(ValidationResult result) {
		System.out.println("JSON values loaded:");
		try (JsonWriter writer = writerFactory.createWriter(new StandardOutputStream())) {
			writer.write((JsonStructure)result.getValue());
		}
		System.out.println();
		System.out.println();
	}
	
	private JsonLoader() {
	}
	
	private static class StandardOutputStream extends FilterOutputStream {

		public StandardOutputStream() {
			super(System.out);
		}

		@Override
		public void close() throws IOException {
			super.flush();
			// Do not close.
		}
	}
}
