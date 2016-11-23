package com.github.i49.hibiscus.validation;

import static com.github.i49.hibiscus.schema.JsonTypes.*;
import static org.junit.Assert.*;

import java.io.StringReader;
import java.util.List;

import org.junit.Test;

import com.github.i49.hibiscus.common.TypeId;
import com.github.i49.hibiscus.problems.MissingPropertyProblem;
import com.github.i49.hibiscus.problems.Problem;
import com.github.i49.hibiscus.problems.TypeMismatchProblem;
import com.github.i49.hibiscus.problems.UnknownPropertyProblem;
import com.github.i49.hibiscus.schema.ComplexType;
import com.github.i49.hibiscus.schema.ObjectType;

public class ObjectValiadtionTest extends BaseValidationTest {

	private static ObjectType createSchema() {
		return object(
				required("a", string()),
				required("b", integer()),
				required("c", number()),
				required("d", bool()),
				required("e", nil()),
				required("f", object()),
				required("g", array(integer()))
			);
	}
	
	public static class ObjectTypeTest extends BaseValidationTest {
		
		@Test
		public void notObjectButArray() {
			String json = "[1, 2, 3]";
			JsonValidator validator = new BasicJsonValidator(createSchema());
			result = validator.validate(new StringReader(json));

			assertEquals(1, result.getProblems().size());
			assertTrue(result.getProblems().get(0) instanceof TypeMismatchProblem);
			TypeMismatchProblem p = (TypeMismatchProblem)result.getProblems().get(0);
			assertEquals(TypeId.ARRAY, p.getActualType());
			assertEquals(TypeId.OBJECT, p.getExpectedTypes().iterator().next());
			assertNotNull(p.getDescription());
		}
	}
	
	@Test
	public void emptyObject() {
		String json = "{}";
		ComplexType schema = object();
		JsonValidator validator = new BasicJsonValidator(schema);
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

		JsonValidator validator = new BasicJsonValidator(createSchema());
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

		JsonValidator validator = new BasicJsonValidator(createSchema());
		result = validator.validate(new StringReader(json));

		assertEquals(1, result.getProblems().size());
		
		Problem p = result.getProblems().get(0);
		assertTrue(p instanceof TypeMismatchProblem);
		assertTrue(((TypeMismatchProblem)p).getExpectedTypes().contains(TypeId.NUMBER));
		assertEquals(TypeId.STRING, ((TypeMismatchProblem)p).getActualType());
		assertNotNull(p.getDescription());
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

		JsonValidator validator = new BasicJsonValidator(createSchema());
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

		JsonValidator validator = new BasicJsonValidator(createSchema());
		result = validator.validate(new StringReader(json));

		List<Problem> problems = result.getProblems();
		assertEquals(1, problems.size());
		MissingPropertyProblem p = (MissingPropertyProblem)problems.get(0);
		assertEquals("d", p.getPropertyName());
		assertNotNull(p.getDescription());
	}
	
	public static class UnknownPropertyTest extends BaseValidationTest {
	
		private static final String json ="{"
					+ "\"a\": \"abc\","
					+ "\"b\": 123,"
					+ "\"c\": 123.45,"
					+ "\"d\": true,"
					+ "\"e\": null,"
					+ "\"f\": {},"
					+ "\"g\": [1, 2, 3],"
					+ "\"h\": 123"
					+ "}";
		
		@Test
		public void objectWithUnknownProperty() {
			
			JsonValidator validator = new BasicJsonValidator(createSchema());
			result = validator.validate(new StringReader(json));
	
			List<Problem> problems = result.getProblems();
			assertEquals(1, problems.size());
			UnknownPropertyProblem p = (UnknownPropertyProblem)problems.get(0);
			assertEquals("h", p.getPropertyName());
			assertNotNull(p.getDescription());
		}
		
		@Test
		public void objectWithMoreProperties() {
			
			JsonValidator validator = new BasicJsonValidator(createSchema().moreProperties());
			ValidationResult result = validator.validate(new StringReader(json));
	
			assertFalse(result.hasProblems());
		}
	}
}
