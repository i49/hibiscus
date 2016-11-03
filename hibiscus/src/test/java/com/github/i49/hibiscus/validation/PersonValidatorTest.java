package com.github.i49.hibiscus.validation;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import javax.json.JsonObject;
import javax.json.JsonValue;

import org.junit.Before;
import org.junit.Test;

import static com.github.i49.hibiscus.schema.types.JsonTypes.*;
import static com.github.i49.hibiscus.validation.Resources.*;

import com.github.i49.hibiscus.schema.TypeId;
import com.github.i49.hibiscus.schema.problems.MissingPropertyProblem;
import com.github.i49.hibiscus.schema.problems.Problem;
import com.github.i49.hibiscus.schema.problems.TypeMismatchProblem;
import com.github.i49.hibiscus.schema.problems.UnknownPropertyProblem;
import com.github.i49.hibiscus.schema.types.ObjectType;

public class PersonValidatorTest {
	
	private JsonValidator validator;
	
	@Before
	public void setUp() {

		ObjectType schema = object(
				required("firstName", string()),
				required("lastName", string()),
				optional("age", integer()),
				optional("hobbies", array(string()))
			);

		validator = new JsonValidator(schema);
	}

	@Test
	public void testRead() throws IOException {

		ValidationResult result = null;
		try (Reader reader = newReader("person.json")) {
			result = validator.validate(reader);
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
	public void testMissingProperty() throws IOException {
		
		ValidationResult result = null;
		try (Reader reader = newReader("person-missing-property.json")) {
			result = validator.validate(reader);
		}
		
		assertTrue(result.hasProblems());
		
		List<Problem> problems = result.getProblems();
		assertEquals(1, problems.size());
		Problem p = problems.get(0);
		assertTrue(p instanceof MissingPropertyProblem);
		assertEquals("lastName", ((MissingPropertyProblem)p).getPropertyName());
	}
	
	@Test
	public void testTypeMismatch() throws IOException {

		ValidationResult result = null;
		try (Reader reader = newReader("person-type-mismatch.json")) {
			result = validator.validate(reader);
		}
		
		assertTrue(result.hasProblems());
		
		List<Problem> problems = result.getProblems();
		assertEquals(3, problems.size());
		
		assertTrue(problems.get(0) instanceof TypeMismatchProblem);
		TypeMismatchProblem p0 = (TypeMismatchProblem)problems.get(0);
		assertEquals(1, p0.getExpectedTypes().size());
		assertTrue(p0.getExpectedTypes().contains(TypeId.STRING));
		assertEquals(TypeId.INTEGER, p0.getInstanceType());

		assertTrue(problems.get(1) instanceof TypeMismatchProblem);
		TypeMismatchProblem p1 = (TypeMismatchProblem)problems.get(1);
		assertEquals(1, p1.getExpectedTypes().size());
		assertTrue(p1.getExpectedTypes().contains(TypeId.INTEGER));
		assertEquals(TypeId.STRING, p1.getInstanceType());

		assertTrue(problems.get(2) instanceof TypeMismatchProblem);
		TypeMismatchProblem p2 = (TypeMismatchProblem)problems.get(2);
		assertEquals(1, p2.getExpectedTypes().size());
		assertTrue(p2.getExpectedTypes().contains(TypeId.ARRAY));
		assertEquals(TypeId.OBJECT, p2.getInstanceType());
	}
	
	@Test
	public void testUnknownProperties() throws IOException {
		
		ValidationResult result = null;
		try (Reader reader = newReader("person-unknown-property.json")) {
			result = validator.validate(reader);
		}
		
		assertTrue(result.hasProblems());
		
		List<Problem> problems = result.getProblems();
		assertEquals(1, problems.size());
		Problem p = problems.get(0);
		assertTrue(p instanceof UnknownPropertyProblem);
		assertEquals("birthplace", ((UnknownPropertyProblem)p).getPropertyName());
	}
}
