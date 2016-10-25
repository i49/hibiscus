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
import com.github.i49.hibiscus.problems.TypeMismatchProblem;
import com.github.i49.hibiscus.problems.UnknownPropertyProblem;

import static com.github.i49.hibiscus.SchemaComponents.*;

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
	
	@Test
	public void testTypeMismatch() throws Exception {

		JsonValidator v = createPersonValidator();
		ValidationResult result = null;
		try (Reader reader = openReader("person-type-mismatch.json")) {
			result = v.validate(reader);
		}
		
		assertTrue(result.hasProblems());
		
		List<Problem> problems = result.getProblems();
		assertEquals(3, problems.size());
		
		assertTrue(problems.get(0) instanceof TypeMismatchProblem);
		TypeMismatchProblem p0 = (TypeMismatchProblem)problems.get(0);
		assertEquals(TypeId.STRING, p0.getExpectedType());
		assertEquals(TypeId.INTEGER, p0.getActualType());

		assertTrue(problems.get(1) instanceof TypeMismatchProblem);
		TypeMismatchProblem p1 = (TypeMismatchProblem)problems.get(1);
		assertEquals(TypeId.INTEGER, p1.getExpectedType());
		assertEquals(TypeId.STRING, p1.getActualType());

		assertTrue(problems.get(2) instanceof TypeMismatchProblem);
		TypeMismatchProblem p2 = (TypeMismatchProblem)problems.get(2);
		assertEquals(TypeId.ARRAY, p2.getExpectedType());
		assertEquals(TypeId.OBJECT, p2.getActualType());
	}
	
	@Test
	public void testUnknownProperties() throws Exception {
		
		JsonValidator v = createPersonValidator();
		ValidationResult result = null;
		try (Reader reader = openReader("person-unknown-property.json")) {
			result = v.validate(reader);
		}
		
		assertTrue(result.hasProblems());
		
		List<Problem> problems = result.getProblems();
		assertEquals(1, problems.size());
		Problem p = problems.get(0);
		assertTrue(p instanceof UnknownPropertyProblem);
		assertEquals("birthplace", ((UnknownPropertyProblem)p).getPropertyName());
	}
	
	private JsonValidator createPersonValidator() {
		ObjectType rootType = object(
				required("firstName", string()),
				required("lastName", string()),
				optional("age", integer()),
				optional("hobbies", array(string()))
			);
		return new JsonValidator(rootType);
	}

	private static Reader openReader(String name) {
		InputStream stream = JsonValidatorTest.class.getResourceAsStream(name);
		return new InputStreamReader(stream, StandardCharsets.UTF_8);
	}
}
