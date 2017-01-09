package com.github.i49.hibiscus.validation;

import static com.github.i49.hibiscus.schema.SchemaComponents.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.github.i49.hibiscus.common.TypeId;
import com.github.i49.hibiscus.problems.ArrayDuplicateItemProblem;
import com.github.i49.hibiscus.problems.ArrayLengthProblem;
import com.github.i49.hibiscus.problems.ArrayTooLongProblem;
import com.github.i49.hibiscus.problems.ArrayTooShortProblem;
import com.github.i49.hibiscus.problems.ExclusiveUpperBoundProblem;
import com.github.i49.hibiscus.problems.InclusiveLowerBoundProblem;
import com.github.i49.hibiscus.problems.MissingPropertyProblem;
import com.github.i49.hibiscus.problems.NoSuchEnumeratorProblem;
import com.github.i49.hibiscus.problems.Problem;
import com.github.i49.hibiscus.problems.StringLengthProblem;
import com.github.i49.hibiscus.problems.TypeMismatchProblem;
import com.github.i49.hibiscus.schema.Schema;

import java.io.StringReader;
import java.math.BigDecimal;
import java.util.List;

import javax.json.JsonArray;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonString;
import javax.json.JsonValue;

import static org.hamcrest.CoreMatchers.*;
import static com.github.i49.hibiscus.validation.CustomAssertions.*;

public class ArrayValidationTest {

	/**
	 * Tests of various kinds of values.
	 */
	public static class ArrayValueTest {

		@Test
		public void objectOrArray() {

			Schema schema = schema(
				object(
					required("foo", string()),
					optional("bar", integer())
				),
				array(string())
			);
			
			String json = "[\"abc\", \"xyz\"]";

			JsonValidator validator = new BasicJsonValidator(schema);
			ValidationResult result = validator.validate(new StringReader(json));
			
			assertValid(result);
			assertFalse(result.hasProblems());
		}
	}

	public static class TypeMismatchTest {
			
		@Test
		public void notArrayButObject() {
			String json = "{}";
			Schema schema = schema(array());
			JsonValidator validator = new BasicJsonValidator(schema);
			ValidationResult result = validator.validate(new StringReader(json));

			assertValid(result);
			assertEquals(1, result.getProblems().size());
			assertTrue(result.getProblems().get(0) instanceof TypeMismatchProblem);
			TypeMismatchProblem p = (TypeMismatchProblem)result.getProblems().get(0);
			assertEquals(TypeId.OBJECT, p.getActualType());
			assertEquals(TypeId.ARRAY, p.getExpectedTypes().iterator().next());
			assertNotNull(p.getDescription());
		}
	}
	
	public static class ArrayItemTest { 
	
		@Test
		public void empty() {
			String json = "[]";
			Schema schema = schema(array());
			JsonValidator validator = new BasicJsonValidator(schema);
			ValidationResult result = validator.validate(new StringReader(json));

			assertValid(result);
			assertFalse(result.hasProblems());
		}

		@Test
		public void booleans() {
			String json = "[true, false, true]";
			Schema schema = schema(array(bool()));
			JsonValidator validator = new BasicJsonValidator(schema);
			ValidationResult result = validator.validate(new StringReader(json));
	
			assertValid(result);
			assertFalse(result.hasProblems());
		}
	
		@Test
		public void integers() {
			String json = "[1, 2, 3, 4, 5]";
			Schema schema = schema(array(integer()));
			JsonValidator validator = new BasicJsonValidator(schema);
			ValidationResult result = validator.validate(new StringReader(json));
	
			assertValid(result);
			assertFalse(result.hasProblems());
		}
	
		@Test
		public void numbers() {
			String json = "[1.2, 3.4, 5.6]";
			Schema schema = schema(array(number()));
			JsonValidator validator = new BasicJsonValidator(schema);
			ValidationResult result = validator.validate(new StringReader(json));
	
			assertValid(result);
			assertFalse(result.hasProblems());
		}
	
		@Test
		public void nulls() {
			String json = "[null, null, null]";
			Schema schema = schema(array(nil()));
			JsonValidator validator = new BasicJsonValidator(schema);
			ValidationResult result = validator.validate(new StringReader(json));
	
			assertValid(result);
			assertFalse(result.hasProblems());
		}
	
		@Test
		public void strings() {
			String json = "[\"abc\", \"xyz\", \"123\"]";
			Schema schema = schema(array(string()));
			JsonValidator validator = new BasicJsonValidator(schema);
			ValidationResult result = validator.validate(new StringReader(json));
	
			assertValid(result);
			assertFalse(result.hasProblems());
		}
	
		@Test
		public void arrays() {
			String json = "[[1, 2, 3], [4, 5, 6]]";
			Schema schema = schema(array(array(integer())));
			JsonValidator validator = new BasicJsonValidator(schema);
			ValidationResult result = validator.validate(new StringReader(json));
	
			assertValid(result);
			assertFalse(result.hasProblems());
		}
		
		@Test
		public void objects() {
			String json = "[{}, {}, {}]";
			Schema schema = schema(array(object()));
			JsonValidator validator = new BasicJsonValidator(schema);
			ValidationResult result = validator.validate(new StringReader(json));
	
			assertValid(result);
			assertFalse(result.hasProblems());
		}

		@Test
		public void mixed() {
			String json = "[123, \"abc\", 456, \"xyz\"]";
			Schema schema = schema(array(integer(), string()));
			JsonValidator validator = new BasicJsonValidator(schema);
			ValidationResult result = validator.validate(new StringReader(json));
	
			assertValid(result);
			assertFalse(result.hasProblems());
		}
	}
	
	/**
	 * Tests of minLength() method.
	 */
	public static class MinLengthTest {
		
		private Schema schema;
		
		@Before
		public void setUp() {
			schema = schema(array(integer()).minLength(3));
		}
		
		@Test
		public void minLength() {
			String json = "[1, 2, 3]";
			JsonValidator validator = new BasicJsonValidator(schema);
			ValidationResult result = validator.validate(new StringReader(json));
	
			assertValid(result);
			assertFalse(result.hasProblems());
		}
	
		@Test
		public void moreThanMinLength() {
			String json = "[1, 2, 3, 4]";
			JsonValidator validator = new BasicJsonValidator(schema);
			ValidationResult result = validator.validate(new StringReader(json));
	
			assertValid(result);
			assertFalse(result.hasProblems());
		}
	
		@Test
		public void lessThanMinLength() {
			String json = "[1, 2]";
			JsonValidator validator = new BasicJsonValidator(schema);
			ValidationResult result = validator.validate(new StringReader(json));
	
			assertValid(result);
			assertEquals(1, result.getProblems().size());
			assertTrue(result.getProblems().get(0) instanceof ArrayTooShortProblem);
			ArrayTooShortProblem p = (ArrayTooShortProblem)result.getProblems().get(0);
			assertEquals(2, p.getActualLength());
			assertEquals(3, p.getLimitLength());
			assertNotNull(p.getDescription());
		}
	}
	
	/**
	 * Tests of maxLength() method.
	 */
	public static class MaxLengthTest {
	
		private Schema schema;
		
		@Before
		public void setUp() {
			schema = schema(array(integer()).maxLength(4));
		}

		@Test
		public void maxLength() {
			String json = "[1, 2, 3, 4]";
			JsonValidator validator = new BasicJsonValidator(schema);
			ValidationResult result = validator.validate(new StringReader(json));
	
			assertValid(result);
			assertFalse(result.hasProblems());
		}
	
		@Test
		public void lessThanMaxLength() {
			String json = "[1, 2, 3]";
			JsonValidator validator = new BasicJsonValidator(schema);
			ValidationResult result = validator.validate(new StringReader(json));
	
			assertValid(result);
			assertFalse(result.hasProblems());
		}
		
		@Test
		public void moreThanMaxLength() {
			String json = "[1, 2, 3, 4, 5]";
			JsonValidator validator = new BasicJsonValidator(schema);
			ValidationResult result = validator.validate(new StringReader(json));
	
			assertValid(result);
			assertEquals(1, result.getProblems().size());
			assertTrue(result.getProblems().get(0) instanceof ArrayTooLongProblem);
			ArrayTooLongProblem p = (ArrayTooLongProblem)result.getProblems().get(0);
			assertEquals(5, p.getActualLength());
			assertEquals(4, p.getLimitLength());
			assertNotNull(p.getDescription());
		}
	}

	/**
	 * Tests applying both minLength() and maxLength() methods.
	 */
	public static class MinAndMaxLengthTest {

		private Schema schema;
		
		@Before
		public void setUp() {
			schema = schema(array(integer()).minLength(3).maxLength(5));
		}

		@Test
		public void lessThanMinLength() {
			String json = "[1, 2]";
			JsonValidator validator = new BasicJsonValidator(schema);
			ValidationResult result = validator.validate(new StringReader(json));
	
			assertValid(result);
			assertEquals(1, result.getProblems().size());
			assertTrue(result.getProblems().get(0) instanceof ArrayTooShortProblem);
			ArrayTooShortProblem p = (ArrayTooShortProblem)result.getProblems().get(0);
			assertEquals(2, p.getActualLength());
			assertEquals(3, p.getLimitLength());
			assertNotNull(p.getDescription());
		}

		@Test
		public void minLength() {
			String json = "[1, 2, 3]";
			JsonValidator validator = new BasicJsonValidator(schema);
			ValidationResult result = validator.validate(new StringReader(json));
	
			assertValid(result);
			assertFalse(result.hasProblems());
		}

		@Test
		public void betweenMinAndMax() {
			String json = "[1, 2, 3, 4]";
			JsonValidator validator = new BasicJsonValidator(schema);
			ValidationResult result = validator.validate(new StringReader(json));
	
			assertValid(result);
			assertFalse(result.hasProblems());
		}

		@Test
		public void maxLength() {
			String json = "[1, 2, 3, 4, 5]";
			JsonValidator validator = new BasicJsonValidator(schema);
			ValidationResult result = validator.validate(new StringReader(json));
	
			assertValid(result);
			assertFalse(result.hasProblems());
		}

		@Test
		public void moreThanMaxLength() {
			String json = "[1, 2, 3, 4, 5, 6]";
			JsonValidator validator = new BasicJsonValidator(schema);
			ValidationResult result = validator.validate(new StringReader(json));
	
			assertValid(result);
			assertEquals(1, result.getProblems().size());
			assertTrue(result.getProblems().get(0) instanceof ArrayTooLongProblem);
			ArrayTooLongProblem p = (ArrayTooLongProblem)result.getProblems().get(0);
			assertEquals(6, p.getActualLength());
			assertEquals(5, p.getLimitLength());
			assertNotNull(p.getDescription());
		}
	}
	
	public static class ArrayLengthTest {
		
		private Schema schema;
		
		@Before
		public void setUp() {
			schema = schema(array(integer()).length(3));
		}
		
		@Test
		public void lessThanExpected() {
			String json = "[1, 2]";
			JsonValidator validator = new BasicJsonValidator(schema);
			ValidationResult result = validator.validate(new StringReader(json));
	
			assertValid(result);
			assertEquals(1, result.getProblems().size());
			assertTrue(result.getProblems().get(0) instanceof ArrayLengthProblem);
			ArrayLengthProblem p = (ArrayLengthProblem)result.getProblems().get(0);
			assertEquals(2, p.getActualLength());
			assertEquals(3, p.getExpectedLength());
			assertNotNull(p.getDescription());
		}

		@Test
		public void equalToExpected() {
			String json = "[1, 2, 3]";
			JsonValidator validator = new BasicJsonValidator(schema);
			ValidationResult result = validator.validate(new StringReader(json));
	
			assertValid(result);
			assertFalse(result.hasProblems());
		}

		@Test
		public void moreThanExpected() {
			String json = "[1, 2, 3, 4]";
			JsonValidator validator = new BasicJsonValidator(schema);
			ValidationResult result = validator.validate(new StringReader(json));
	
			assertValid(result);
			assertEquals(1, result.getProblems().size());
			assertTrue(result.getProblems().get(0) instanceof ArrayLengthProblem);
			ArrayLengthProblem p = (ArrayLengthProblem)result.getProblems().get(0);
			assertEquals(4, p.getActualLength());
			assertEquals(3, p.getExpectedLength());
			assertNotNull(p.getDescription());
		}
	}

	
	public static class ArrayItemTypeTest {
		
		@Test
		public void typeMatchSingleType() {
			String json = "[\"a\", \"b\", \"c\"]";
			Schema schema = schema(array(string()));
			JsonValidator validator = new BasicJsonValidator(schema);
			ValidationResult result = validator.validate(new StringReader(json));

			assertValid(result);
			assertFalse(result.hasProblems());
		}

		@Test
		public void typeUnmatchSingleType() {
			String json = "[\"a\", \"b\", \"c\"]";
			Schema schema = schema(array(number()));
			JsonValidator validator = new BasicJsonValidator(schema);
			ValidationResult result = validator.validate(new StringReader(json));

			assertValid(result);
			assertEquals(3, result.getProblems().size());
			assertTrue(result.getProblems().get(0) instanceof TypeMismatchProblem);
			TypeMismatchProblem p = (TypeMismatchProblem)result.getProblems().get(0);
			assertEquals(TypeId.STRING, p.getActualType());
			assertEquals(1, p.getExpectedTypes().size());
			assertTrue(p.getExpectedTypes().contains(TypeId.NUMBER));
			assertNotNull(p.getDescription());
		}
		
		@Test
		public void typeMatchOneOfTypes() {
			String json = "[\"a\", \"b\", \"c\"]";
			Schema schema = schema(array(number(), string(), bool()));
			JsonValidator validator = new BasicJsonValidator(schema);
			ValidationResult result = validator.validate(new StringReader(json));

			assertValid(result);
			assertFalse(result.hasProblems());
		}

		@Test
		public void typeUnmatchAllTypes() {
			String json = "[\"a\", \"b\", \"c\"]";
			Schema schema = schema(array(number(), bool(), nil()));
			JsonValidator validator = new BasicJsonValidator(schema);
			ValidationResult result = validator.validate(new StringReader(json));

			assertValid(result);
			assertEquals(3, result.getProblems().size());
			assertTrue(result.getProblems().get(0) instanceof TypeMismatchProblem);
			TypeMismatchProblem p = (TypeMismatchProblem)result.getProblems().get(0);
			assertEquals(TypeId.STRING, p.getActualType());
			assertEquals(3, p.getExpectedTypes().size());
			assertTrue(p.getExpectedTypes().contains(TypeId.NUMBER));
			assertTrue(p.getExpectedTypes().contains(TypeId.BOOLEAN));
			assertTrue(p.getExpectedTypes().contains(TypeId.NULL));
			assertNotNull(p.getDescription());
		}
	}
	
	public static class ArrayUniqueItemTest {
	
		@Test
		public void unique() {
			String json = "[\"club\", \"diamond\", \"heart\", \"spade\"]";
			Schema schema = schema(array(string()).unique());
			JsonValidator validator = new BasicJsonValidator(schema);
			ValidationResult result = validator.validate(new StringReader(json));

			assertValid(result);
			assertFalse(result.hasProblems());
		}

		@Test
		public void duplication() {
			String json = "[\"club\", \"diamond\", \"club\", \"spade\"]";
			Schema schema = schema(array(string()).unique());
			JsonValidator validator = new BasicJsonValidator(schema);
			ValidationResult result = validator.validate(new StringReader(json));

			assertValid(result);
			assertEquals(1, result.getProblems().size());
			assertTrue(result.getProblems().get(0) instanceof ArrayDuplicateItemProblem);
			ArrayDuplicateItemProblem p = (ArrayDuplicateItemProblem)result.getProblems().get(0);
			assertEquals(2, p.getDuplicateIndex());
			assertEquals("club", ((JsonString)p.getDuplicateItem()).getString());
			assertNotNull(p.getDescription());
		}

		@Test
		public void manyDuplications() {
			String json = "[123, 456, 456, 123]";
			Schema schema = schema(array(integer()).unique());
			JsonValidator validator = new BasicJsonValidator(schema);
			ValidationResult result = validator.validate(new StringReader(json));

			assertValid(result);
			assertEquals(2, result.getProblems().size());

			assertTrue(result.getProblems().get(0) instanceof ArrayDuplicateItemProblem);
			ArrayDuplicateItemProblem p0 = (ArrayDuplicateItemProblem)result.getProblems().get(0);
			assertEquals(2, p0.getDuplicateIndex());
			assertEquals(456, ((JsonNumber)p0.getDuplicateItem()).intValue());
			assertNotNull(p0.getDescription());

			assertTrue(result.getProblems().get(1) instanceof ArrayDuplicateItemProblem);
			ArrayDuplicateItemProblem p1 = (ArrayDuplicateItemProblem)result.getProblems().get(1);
			assertEquals(3, p1.getDuplicateIndex());
			assertEquals(123, ((JsonNumber)p1.getDuplicateItem()).intValue());
			assertNotNull(p1.getDescription());
		}
	}
	
	public static class ArrayItemProblemTest {
	
		@Test
		public void testProblemValues() {
			
			Schema schema = schema(array(
				string().length(3),
				integer().minInclusive(1),
				number().maxExclusive(new BigDecimal("10.0")),
				bool().enumeration(false),
				array(integer()).length(3),
				object(required("name", string())).moreProperties()
			));

			String json = "[\"abcd\", -1, 20.0, true, [1, 2], {\"age\": 42}]";

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
