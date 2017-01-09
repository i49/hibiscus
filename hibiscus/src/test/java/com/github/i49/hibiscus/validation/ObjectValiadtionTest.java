package com.github.i49.hibiscus.validation;

import static com.github.i49.hibiscus.schema.SchemaComponents.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.io.StringReader;
import java.math.BigDecimal;
import java.util.List;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;

import org.junit.Before;
import org.junit.Test;

import com.github.i49.hibiscus.common.TypeId;
import com.github.i49.hibiscus.problems.ArrayLengthProblem;
import com.github.i49.hibiscus.problems.AssertionFailureProblem;
import com.github.i49.hibiscus.problems.ExclusiveUpperBoundProblem;
import com.github.i49.hibiscus.problems.InclusiveLowerBoundProblem;
import com.github.i49.hibiscus.problems.MissingPropertyProblem;
import com.github.i49.hibiscus.problems.NoSuchEnumeratorProblem;
import com.github.i49.hibiscus.problems.Problem;
import com.github.i49.hibiscus.problems.StringLengthProblem;
import com.github.i49.hibiscus.problems.TypeMismatchProblem;
import com.github.i49.hibiscus.problems.UnknownPropertyProblem;
import com.github.i49.hibiscus.schema.ObjectType;
import com.github.i49.hibiscus.schema.Schema;

import static com.github.i49.hibiscus.validation.CustomAssertions.*;

public class ObjectValiadtionTest {

	private static ObjectType createObjectType() {
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

	private static Schema createSchema() {
		return schema(createObjectType());
	}
	
	/**
	 * Tests of various kinds of values.
	 */
	public static class ObjectValueTest {

		@Test
		public void basicObject() {

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
			ValidationResult result = validator.validate(new StringReader(json));

			assertValid(result);
			assertFalse(result.hasProblems());
		}

		@Test
		public void emptyObject() {
			String json = "{}";
			Schema schema = schema(object());
			JsonValidator validator = new BasicJsonValidator(schema);
			ValidationResult result = validator.validate(new StringReader(json));

			assertValid(result);
			assertFalse(result.hasProblems());
		}
	
		@Test
		public void arrayOrObject() {
			
			Schema schema = schema(
					array(string()),
					object(
						required("foo", string()),
						optional("bar", integer())
					)
				);
			
			String json = "{ \"foo\": \"abc\", \"bar\": 123 }";

			JsonValidator validator = new BasicJsonValidator(schema);
			ValidationResult result = validator.validate(new StringReader(json));
			
			assertValid(result);
			assertFalse(result.hasProblems());
		}
	}
	
	public static class TypeMismatchTest {
	
		@Test
		public void notObjectButArray() {
			String json = "[1, 2, 3]";
			JsonValidator validator = new BasicJsonValidator(createSchema());
			ValidationResult result = validator.validate(new StringReader(json));

			assertValid(result);
			assertEquals(1, result.getProblems().size());
			assertTrue(result.getProblems().get(0) instanceof TypeMismatchProblem);
			TypeMismatchProblem p = (TypeMismatchProblem)result.getProblems().get(0);
			assertEquals(TypeId.ARRAY, p.getActualType());
			assertEquals(TypeId.OBJECT, p.getExpectedTypes().iterator().next());
			assertNotNull(p.getDescription());
		}
	}

	public static class PropertyTypeTest {

		@Test
		public void wrongType() {
		
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
			ValidationResult result = validator.validate(new StringReader(json));
	
			assertValid(result);
			assertEquals(1, result.getProblems().size());
			
			Problem p = result.getProblems().get(0);
			assertTrue(p instanceof TypeMismatchProblem);
			assertTrue(((TypeMismatchProblem)p).getExpectedTypes().contains(TypeId.NUMBER));
			assertEquals(TypeId.STRING, ((TypeMismatchProblem)p).getActualType());
			assertNotNull(p.getDescription());
		}

		@Test
		public void multipleWrongTypes() {
		
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
			ValidationResult result = validator.validate(new StringReader(json));
	
			assertValid(result);
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
	}
	
	public static class MissingPropertyTest {
		
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
			ValidationResult result = validator.validate(new StringReader(json));
	
			assertValid(result);
			List<Problem> problems = result.getProblems();
			assertEquals(1, problems.size());
			MissingPropertyProblem p = (MissingPropertyProblem)problems.get(0);
			assertEquals("d", p.getPropertyName());
			assertNotNull(p.getDescription());
		}
	}
	
	public static class UnknownPropertyTest {
	
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
			ValidationResult result = validator.validate(new StringReader(json));
	
			assertValid(result);
			List<Problem> problems = result.getProblems();
			assertEquals(1, problems.size());
			UnknownPropertyProblem p = (UnknownPropertyProblem)problems.get(0);
			assertEquals("h", p.getPropertyName());
			assertNotNull(p.getDescription());
		}
		
		@Test
		public void objectWithMoreProperties() {
			
			Schema schema = schema(createObjectType().moreProperties());

			JsonValidator validator = new BasicJsonValidator(schema);
			ValidationResult result = validator.validate(new StringReader(json));
	
			assertValid(result);
			assertFalse(result.hasProblems());
		}
	}

	public static class AssertionTest {
		
		private Schema schema;
		
		@Before
		public void setUp() {
			schema = schema(
				object(
					required("rate", integer().minInclusive(1).maxInclusive(5)),
					optional("comment", string())
				).assertion(
					(JsonObject value)->{
						if (value.getInt("rate") < 5) {
							return value.containsKey("comment");
						} else {
							return true;
						}
					},
					(value, locale)->"Any comments please."
				)
			);
		}
		
		@Test
		public void success() {

			String json = "{"
					+ "\"rate\": 3,"
					+ "\"comment\": \"so-so\""
					+ "}";

			JsonValidator validator = new BasicJsonValidator(schema);
			ValidationResult result = validator.validate(new StringReader(json));
	
			assertValid(result);
			assertFalse(result.hasProblems());
		}
		
		@Test
		public void failure() {
			String json = "{"
					+ "\"rate\": 3"
					+ "}";

			JsonValidator validator = new BasicJsonValidator(schema);
			ValidationResult result = validator.validate(new StringReader(json));
	
			assertValid(result);
			assertEquals(1, result.getProblems().size());
			assertTrue(result.getProblems().get(0) instanceof AssertionFailureProblem);
			AssertionFailureProblem<?> p = (AssertionFailureProblem<?>)result.getProblems().get(0);
			assertEquals(1, ((JsonObject)p.getActualValue()).size());
			assertEquals("Any comments please.", p.getDescription());
		}
	}
	
	public static class ObjectPropertyProblemTest {
		
		@Test
		public void testProblemValues() {

			Schema schema = schema(object(
				required("a", string().length(3)),
				required("b", integer().minInclusive(1)),
				required("c", number().maxExclusive(new BigDecimal("10.0"))),
				required("d", bool().enumeration(false)),
				required("e", array(integer()).length(3)),
				required("f", object(required("name", string())).moreProperties())
			));

			String json = "{"
					+ "\"a\": \"abcd\","
					+ "\"b\": -1,"
					+ "\"c\": 20.0,"
					+ "\"d\": true,"
					+ "\"e\": [1, 2],"
					+ "\"f\": {\"age\": 42}"
					+ "}";

			JsonValidator validator = new BasicJsonValidator(schema);
			ValidationResult result = validator.validate(new StringReader(json));

			assertValid(result);
			assertEquals(6, result.getProblems().size());
			
			List<Problem> problems = result.getProblems();
			
			Problem p0 = problems.get(0); 
			assertThat(p0, is(instanceOf(StringLengthProblem.class)));
			assertThat(((StringLengthProblem)p0).getActualValue().getString(), equalTo("abcd"));

			Problem p1 = problems.get(1); 
			assertThat(p1, is(instanceOf(InclusiveLowerBoundProblem.class)));
			assertThat(((InclusiveLowerBoundProblem)p1).getActualValue().intValue(), equalTo(-1));

			Problem p2 = problems.get(2); 
			assertThat(p2, is(instanceOf(ExclusiveUpperBoundProblem.class)));
			assertThat(((ExclusiveUpperBoundProblem)p2).getActualValue().bigDecimalValue(), equalTo(new BigDecimal("20.0")));

			Problem p3 = problems.get(3); 
			assertThat(p3, is(instanceOf(NoSuchEnumeratorProblem.class)));
			assertThat(((NoSuchEnumeratorProblem)p3).getActualValue(), equalTo(JsonValue.TRUE));

			Problem p4 = problems.get(4); 
			assertThat(p4, is(instanceOf(ArrayLengthProblem.class)));
			JsonValue v4 = ((ArrayLengthProblem)p4).getActualValue();
			assertThat(v4, instanceOf(JsonArray.class));
			assertThat(((JsonArray)v4).size(), equalTo(2));
			assertThat(((JsonArray)v4).getInt(0), equalTo(1));
			assertThat(((JsonArray)v4).getInt(1), equalTo(2));

			Problem p5 = problems.get(5); 
			assertThat(p5, is(instanceOf(MissingPropertyProblem.class)));
			JsonValue v5 = ((MissingPropertyProblem)p5).getActualValue();
			assertThat(v5, instanceOf(JsonObject.class));
			assertThat(((JsonObject)v5).getInt("age"), equalTo(42));
		}
	}
}
