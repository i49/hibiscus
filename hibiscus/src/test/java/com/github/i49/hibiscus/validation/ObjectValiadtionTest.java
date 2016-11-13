package com.github.i49.hibiscus.validation;

import static com.github.i49.hibiscus.schema.JsonTypes.*;
import static org.junit.Assert.*;

import java.io.StringReader;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.github.i49.hibiscus.common.TypeId;
import com.github.i49.hibiscus.problems.MissingPropertyProblem;
import com.github.i49.hibiscus.problems.Problem;
import com.github.i49.hibiscus.problems.TypeMismatchProblem;
import com.github.i49.hibiscus.problems.UnknownPropertyProblem;
import com.github.i49.hibiscus.schema.JsonType;
import com.github.i49.hibiscus.schema.ObjectType;

public class ObjectValiadtionTest extends BaseValidationTest {

	private ObjectType schema;
	
	@Before
	@Override
	public void setUp() {
		super.setUp();
		schema = object(
				required("a", string()),
				required("b", integer()),
				required("c", number()),
				required("d", bool()),
				required("e", nullValue()),
				required("f", object()),
				required("g", array(integer()))
			);
	}

	@Test
	public void emptyObject() {
		String json = "{}";
		JsonType schema = object();
		JsonValidator validator = new JsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}
	
	@Test
	public void normalObject() {

		String json = "{"
				+ "\"a\": \"abc\","
				+ "\"b\": 123,"
				+ "\"c\": 123.45,"
				+ "\"d\": true,"
				+ "\"e\": null,"
				+ "\"f\": {},"
				+ "\"g\": [1, 2, 3]"
				+ "}";

		JsonValidator validator = new JsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}
	
	@Test
	public void objectOfWrongTypes() {
	
		String json = "{"
				+ "\"a\": \"abc\","
				+ "\"b\": 123,"
				+ "\"c\": \"123.45\","
				+ "\"d\": true,"
				+ "\"e\": null,"
				+ "\"f\": {},"
				+ "\"g\": [1, 2, 3]"
				+ "}";

		JsonValidator validator = new JsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertEquals(1, result.getProblems().size());
		
		Problem p = result.getProblems().get(0);
		assertTrue(p instanceof TypeMismatchProblem);
		assertTrue(((TypeMismatchProblem)p).getExpectedTypes().contains(TypeId.NUMBER));
		assertEquals(TypeId.STRING, ((TypeMismatchProblem)p).getActualType());
		assertNotNull(p.getMessage());
	}

	@Test
	public void objectOfMultipleWrongTypes() {
	
		String json = "{"
				+ "\"a\": 123,"
				+ "\"b\": true,"
				+ "\"c\": \"abc\","
				+ "\"d\": 123.45,"
				+ "\"e\": {},"
				+ "\"f\": [1, 2, 3],"
				+ "\"g\": null"
				+ "}";

		JsonValidator validator = new JsonValidator(schema);
		result = validator.validate(new StringReader(json));

		List<Problem> problems = result.getProblems();
		assertEquals(7, problems.size());
		
		TypeMismatchProblem p1 = (TypeMismatchProblem)problems.get(0);
		assertEquals(TypeId.INTEGER, p1.getActualType());
		TypeMismatchProblem p2 = (TypeMismatchProblem)problems.get(1);
		assertEquals(TypeId.BOOLEAN, p2.getActualType());
		TypeMismatchProblem p3 = (TypeMismatchProblem)problems.get(2);
		assertEquals(TypeId.STRING, p3.getActualType());
		TypeMismatchProblem p4 = (TypeMismatchProblem)problems.get(3);
		assertEquals(TypeId.NUMBER, p4.getActualType());
		TypeMismatchProblem p5 = (TypeMismatchProblem)problems.get(4);
		assertEquals(TypeId.OBJECT, p5.getActualType());
		TypeMismatchProblem p6 = (TypeMismatchProblem)problems.get(5);
		assertEquals(TypeId.ARRAY, p6.getActualType());
		TypeMismatchProblem p7 = (TypeMismatchProblem)problems.get(6);
		assertEquals(TypeId.NULL, p7.getActualType());
	}
	
	@Test
	public void objectWithMissingProperty() {

		String json = "{"
				+ "\"a\": \"abc\","
				+ "\"b\": 123,"
				+ "\"c\": 123.45,"
				+ "\"e\": null,"
				+ "\"f\": {},"
				+ "\"g\": [1, 2, 3]"
				+ "}";

		JsonValidator validator = new JsonValidator(schema);
		result = validator.validate(new StringReader(json));

		List<Problem> problems = result.getProblems();
		assertEquals(1, problems.size());
		MissingPropertyProblem p = (MissingPropertyProblem)problems.get(0);
		assertEquals("d", p.getPropertyName());
		assertNotNull(p.getMessage());
	}
	
	private static String jsonWithUnknownProperty() {
		return "{"
				+ "\"a\": \"abc\","
				+ "\"b\": 123,"
				+ "\"c\": 123.45,"
				+ "\"d\": true,"
				+ "\"e\": null,"
				+ "\"f\": {},"
				+ "\"g\": [1, 2, 3],"
				+ "\"h\": 123"
				+ "}";
	}
	
	@Test
	public void objectWithUnknownProperty() {
		
		String json = jsonWithUnknownProperty();
		JsonValidator validator = new JsonValidator(schema);
		result = validator.validate(new StringReader(json));

		List<Problem> problems = result.getProblems();
		assertEquals(1, problems.size());
		UnknownPropertyProblem p = (UnknownPropertyProblem)problems.get(0);
		assertEquals("h", p.getPropertyName());
		assertNotNull(p.getMessage());
	}
	
	@Test
	public void objectWithMoreProperties() {
		
		String json = jsonWithUnknownProperty();
		JsonValidator validator = new JsonValidator(schema.moreProperties());
		ValidationResult result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}
}
