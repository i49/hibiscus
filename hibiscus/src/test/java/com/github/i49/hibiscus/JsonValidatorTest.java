package com.github.i49.hibiscus;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.json.JsonObject;
import javax.json.JsonValue;

import org.junit.Test;

import com.github.i49.hibiscus.problems.MissingPropertyProblem;
import com.github.i49.hibiscus.problems.Problem;

import static com.github.i49.hibiscus.SchemaObjects.*;

public class JsonValidatorTest {

	@Test
	public void testRead() throws Exception {

		JsonValidator v = createPersonValidator();
		ValidationResult result = null;
		try (Reader reader = openReader("person.json")) {
			result = v.validate(reader);
		}
		
		assertFalse(result.hasProblems());

		JsonValue root = result.getValue();
		assertTrue(root instanceof JsonObject);
		JsonObject object = (JsonObject)root;
		assertEquals("Jason", object.getString("firstName"));
		assertEquals("Bourne", object.getString("lastName"));
		assertEquals(46, object.getInt("age"));
	}
	
	@Test
	public void testMissingProperty() throws Exception {
		
		JsonValidator v = createPersonValidator();
		ValidationResult result = null;
		try (Reader reader = openReader("person-missing-property.json")) {
			result = v.validate(reader);
		}
		
		assertTrue(result.hasProblems());
		
		List<Problem> problems = result.getProblems();
		assertEquals(1, problems.size());
		Problem p = problems.get(0);
		assertTrue(p instanceof MissingPropertyProblem);
		assertEquals("lastName", ((MissingPropertyProblem)p).getPropertyName());
	}
	
	private JsonValidator createPersonValidator() {
		ObjectType rootType = object()
				.properties(
					required("firstName", string()),
					required("lastName", string()),
					optional("age", number()),
					optional("hobbies", array(string()))
				);
		return new JsonValidator(rootType);
	}

	private static Reader openReader(String name) {
		InputStream stream = JsonValidatorTest.class.getResourceAsStream(name);
		return new InputStreamReader(stream, StandardCharsets.UTF_8);
	}
}
