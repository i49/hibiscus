package com.github.i49.hibiscus.validation;

import static com.github.i49.hibiscus.schema.JsonTypes.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.github.i49.hibiscus.common.TypeId;
import com.github.i49.hibiscus.problems.ArrayDuplicateItemProblem;
import com.github.i49.hibiscus.problems.ArrayLengthProblem;
import com.github.i49.hibiscus.problems.ArrayTooLongProblem;
import com.github.i49.hibiscus.problems.ArrayTooShortProblem;
import com.github.i49.hibiscus.problems.TypeMismatchProblem;
import com.github.i49.hibiscus.schema.Schema;

import java.io.StringReader;

import javax.json.JsonNumber;
import javax.json.JsonString;

public class ArrayValidationTest extends BaseValidationTest {

	public static class ArrayTypeTest  extends BaseValidationTest {

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
			result = validator.validate(new StringReader(json));
			
			assertFalse(result.hasProblems());
		}

		@Test
		public void notArrayButObject() {
			String json = "{}";
			Schema schema = schema(array());
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));

			assertEquals(1, result.getProblems().size());
			assertTrue(result.getProblems().get(0) instanceof TypeMismatchProblem);
			TypeMismatchProblem p = (TypeMismatchProblem)result.getProblems().get(0);
			assertEquals(TypeId.OBJECT, p.getActualType());
			assertEquals(TypeId.ARRAY, p.getExpectedTypes().iterator().next());
			assertNotNull(p.getDescription());
		}
	}
	
	public static class ArrayItemTest extends BaseValidationTest { 
	
		@Test
		public void empty() {
			String json = "[]";
			Schema schema = schema(array());
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));

			assertFalse(result.hasProblems());
		}

		@Test
		public void booleans() {
			String json = "[true, false, true]";
			Schema schema = schema(array(bool()));
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
	
			assertFalse(result.hasProblems());
		}
	
		@Test
		public void integers() {
			String json = "[1, 2, 3, 4, 5]";
			Schema schema = schema(array(integer()));
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
	
			assertFalse(result.hasProblems());
		}
	
		@Test
		public void numbers() {
			String json = "[1.2, 3.4, 5.6]";
			Schema schema = schema(array(number()));
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
	
			assertFalse(result.hasProblems());
		}
	
		@Test
		public void nulls() {
			String json = "[null, null, null]";
			Schema schema = schema(array(nil()));
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
	
			assertFalse(result.hasProblems());
		}
	
		@Test
		public void strings() {
			String json = "[\"abc\", \"xyz\", \"123\"]";
			Schema schema = schema(array(string()));
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
	
			assertFalse(result.hasProblems());
		}
	
		@Test
		public void arrays() {
			String json = "[[1, 2, 3], [4, 5, 6]]";
			Schema schema = schema(array(array(integer())));
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
	
			assertFalse(result.hasProblems());
		}
		
		@Test
		public void objects() {
			String json = "[{}, {}, {}]";
			Schema schema = schema(array(object()));
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
	
			assertFalse(result.hasProblems());
		}

		@Test
		public void mixed() {
			String json = "[123, \"abc\", 456, \"xyz\"]";
			Schema schema = schema(array(integer(), string()));
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
	
			assertFalse(result.hasProblems());
		}
	}
	
	/**
	 * Tests of minLength() method.
	 */
	public static class MinLengthTest extends BaseValidationTest {
		
		private Schema schema;
		
		@Before
		public void setUp() {
			super.setUp();
			schema = schema(array(integer()).minLength(3));
		}
		
		@Test
		public void minLength() {
			String json = "[1, 2, 3]";
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
	
			assertFalse(result.hasProblems());
		}
	
		@Test
		public void moreThanMinLength() {
			String json = "[1, 2, 3, 4]";
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
	
			assertFalse(result.hasProblems());
		}
	
		@Test
		public void lessThanMinLength() {
			String json = "[1, 2]";
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
	
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
	public static class MaxLengthTest extends BaseValidationTest {
	
		private Schema schema;
		
		@Before
		public void setUp() {
			super.setUp();
			schema = schema(array(integer()).maxLength(4));
		}

		@Test
		public void maxLength() {
			String json = "[1, 2, 3, 4]";
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
	
			assertFalse(result.hasProblems());
		}
	
		@Test
		public void lessThanMaxLength() {
			String json = "[1, 2, 3]";
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
	
			assertFalse(result.hasProblems());
		}
		
		@Test
		public void moreThanMaxLength() {
			String json = "[1, 2, 3, 4, 5]";
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
	
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
	public static class MinAndMaxLengthTest extends BaseValidationTest {

		private Schema schema;
		
		@Before
		public void setUp() {
			super.setUp();
			schema = schema(array(integer()).minLength(3).maxLength(5));
		}

		@Test
		public void lessThanMinLength() {
			String json = "[1, 2]";
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
	
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
			result = validator.validate(new StringReader(json));
	
			assertFalse(result.hasProblems());
		}

		@Test
		public void betweenMinAndMax() {
			String json = "[1, 2, 3, 4]";
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
	
			assertFalse(result.hasProblems());
		}

		@Test
		public void maxLength() {
			String json = "[1, 2, 3, 4, 5]";
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
	
			assertFalse(result.hasProblems());
		}

		@Test
		public void moreThanMaxLength() {
			String json = "[1, 2, 3, 4, 5, 6]";
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
	
			assertEquals(1, result.getProblems().size());
			assertTrue(result.getProblems().get(0) instanceof ArrayTooLongProblem);
			ArrayTooLongProblem p = (ArrayTooLongProblem)result.getProblems().get(0);
			assertEquals(6, p.getActualLength());
			assertEquals(5, p.getLimitLength());
			assertNotNull(p.getDescription());
		}
	}
	
	public static class ArrayLengthTest extends BaseValidationTest {
		
		private Schema schema;
		
		@Before
		public void setUp() {
			super.setUp();
			schema = schema(array(integer()).length(3));
		}
		
		@Test
		public void lessThanExpected() {
			String json = "[1, 2]";
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
	
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
			result = validator.validate(new StringReader(json));
	
			assertFalse(result.hasProblems());
		}

		@Test
		public void moreThanExpected() {
			String json = "[1, 2, 3, 4]";
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
	
			assertEquals(1, result.getProblems().size());
			assertTrue(result.getProblems().get(0) instanceof ArrayLengthProblem);
			ArrayLengthProblem p = (ArrayLengthProblem)result.getProblems().get(0);
			assertEquals(4, p.getActualLength());
			assertEquals(3, p.getExpectedLength());
			assertNotNull(p.getDescription());
		}
	}

	
	public static class ArrayItemTypeTest extends BaseValidationTest {
		
		@Test
		public void typeMatchSingleType() {
			String json = "[\"a\", \"b\", \"c\"]";
			Schema schema = schema(array(string()));
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));

			assertFalse(result.hasProblems());
		}

		@Test
		public void typeUnmatchSingleType() {
			String json = "[\"a\", \"b\", \"c\"]";
			Schema schema = schema(array(number()));
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));

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
			result = validator.validate(new StringReader(json));

			assertFalse(result.hasProblems());
		}

		@Test
		public void typeUnmatchAllTypes() {
			String json = "[\"a\", \"b\", \"c\"]";
			Schema schema = schema(array(number(), bool(), nil()));
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));

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
	
	public static class ArrayUniqueItemTest extends BaseValidationTest {
	
		@Test
		public void unique() {
			String json = "[\"club\", \"diamond\", \"heart\", \"spade\"]";
			Schema schema = schema(array(string()).unique());
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));

			assertFalse(result.hasProblems());
		}

		@Test
		public void duplication() {
			String json = "[\"club\", \"diamond\", \"club\", \"spade\"]";
			Schema schema = schema(array(string()).unique());
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));

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
			result = validator.validate(new StringReader(json));

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
}
