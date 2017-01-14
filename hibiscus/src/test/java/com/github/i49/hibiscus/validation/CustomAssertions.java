package com.github.i49.hibiscus.validation;

import com.github.i49.hibiscus.problems.Problem;

import static org.junit.Assert.*;

import java.io.StringReader;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;

import static org.hamcrest.CoreMatchers.*;

public class CustomAssertions {
	
	private static final Logger log = Logger.getLogger(CustomAssertions.class.getName());

	public static void assertResultValid(ValidationResult result) {
		assertProblemsValid(result.getProblems());
		assertNotNull(result.getValue());
 	}
	
	public static void assertResultValid(ValidationResult result, String json) {
		assertProblemsValid(result.getProblems());
		assertValueValid(result.getValue(), json);
	}
	
	private static void assertValueValid(JsonValue actual, String json) {
		JsonReader reader = Json.createReader(new StringReader(json));
		JsonValue expected = reader.read();
		compareJsonValue(actual, expected);
	}
	
	private static void assertProblemsValid(List<Problem> problems) {
		for (Problem p: problems) {
			assertProblemValid(p);
		}
	}
	
	private static void assertProblemValid(Problem problem) {
		assertNotNull(problem.getMessage(Locale.ENGLISH));
		assertNotNull(problem.getDescription());
		assertNotNull(problem.getLocation());
		
		JsonValue value = problem.getCauseValue();
		assertNotNull(value);
		String className = value.getClass().getName();
		assertThat(className, not(startsWith("com.github.i49.hibiscus.")));
		
		assertNotNull(problem.getPointer());

		log.fine(problem.getMessage(Locale.ENGLISH));
	}
	
	private static void compareJsonValue(JsonValue actual, JsonValue expected) {
		assertNotNull(actual);
		assertNotNull(expected);
		assertThat(actual.getValueType(), is(expected.getValueType()));
		JsonValue.ValueType type = actual.getValueType();
		if (type == JsonValue.ValueType.OBJECT) {
			JsonObject actualObject = (JsonObject)actual;
			JsonObject expectedObject = (JsonObject)expected;
			assertThat(actualObject.size(), equalTo(expectedObject.size()));
			for (String key: actualObject.keySet()) {
				 compareJsonValue(actualObject.get(key), expectedObject.get(key));
			}
		} else if (type == JsonValue.ValueType.ARRAY) {
			JsonArray actualArray = (JsonArray)actual;
			JsonArray expectedArray = (JsonArray)expected;
			assertThat(actualArray.size(), equalTo(expectedArray.size()));
			for (int i = 0; i < actualArray.size(); i++) {
				compareJsonValue(actualArray.get(i), expectedArray.get(i));
			}
		} else {
			assertTrue(actual.equals(expected));
		}
	}
}
