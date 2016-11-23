package com.github.i49.hibiscus.validation;

import static com.github.i49.hibiscus.schema.JsonTypes.*;
import static org.junit.Assert.*;
import org.junit.Test;

import com.github.i49.hibiscus.common.TypeId;
import com.github.i49.hibiscus.problems.ArrayTooLongProblem;
import com.github.i49.hibiscus.problems.ArrayTooShortProblem;
import com.github.i49.hibiscus.problems.TypeMismatchProblem;
import com.github.i49.hibiscus.schema.ComplexType;

import java.io.StringReader;

public class ArrayValidationTest extends BaseValidationTest {

	public static class ArrayTypeTest  extends BaseValidationTest {

		@Test
		public void notArrayButObject() {
			String json = "{}";
			ComplexType schema = array();
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
			ComplexType schema = array();
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));

			assertFalse(result.hasProblems());
		}

		@Test
		public void booleans() {
			String json = "[true, false, true]";
			ComplexType schema = array(bool());
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
	
			assertFalse(result.hasProblems());
		}
	
		@Test
		public void integers() {
			String json = "[1, 2, 3, 4, 5]";
			ComplexType schema = array(integer());
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
	
			assertFalse(result.hasProblems());
		}
	
		@Test
		public void numbers() {
			String json = "[1.2, 3.4, 5.6]";
			ComplexType schema = array(number());
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
	
			assertFalse(result.hasProblems());
		}
	
		@Test
		public void nulls() {
			String json = "[null, null, null]";
			ComplexType schema = array(nil());
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
	
			assertFalse(result.hasProblems());
		}
	
		@Test
		public void strings() {
			String json = "[\"abc\", \"xyz\", \"123\"]";
			ComplexType schema = array(string());
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
	
			assertFalse(result.hasProblems());
		}
	
		@Test
		public void arrays() {
			String json = "[[1, 2, 3], [4, 5, 6]]";
			ComplexType schema = array(array(integer()));
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
	
			assertFalse(result.hasProblems());
		}
		
		@Test
		public void objects() {
			String json = "[{}, {}, {}]";
			ComplexType schema = array(object());
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
	
			assertFalse(result.hasProblems());
		}

		@Test
		public void mixed() {
			String json = "[123, \"abc\", 456, \"xyz\"]";
			ComplexType schema = array(integer(), string());
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
	
			assertFalse(result.hasProblems());
		}
	}
	
	public static class ArraySizeTest extends BaseValidationTest {
		
		@Test
		public void arrayOfMinItems() {
			String json = "[1, 2, 3]";
			ComplexType schema = array(integer()).minItems(3);
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
	
			assertFalse(result.hasProblems());
		}
	
		@Test
		public void arrayOfItemsMoreThanMin() {
			String json = "[1, 2, 3, 4]";
			ComplexType schema = array(integer()).minItems(3);
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
	
			assertFalse(result.hasProblems());
		}
	
		@Test
		public void arrayOfItemsLessThanMin() {
			String json = "[1, 2]";
			ComplexType schema = array(integer()).minItems(3);
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
	
			assertEquals(1, result.getProblems().size());
			assertTrue(result.getProblems().get(0) instanceof ArrayTooShortProblem);
			ArrayTooShortProblem p = (ArrayTooShortProblem)result.getProblems().get(0);
			assertEquals(2, p.getActualSize());
			assertEquals(3, p.getLimitSize());
			assertNotNull(p.getDescription());
		}
	
		@Test
		public void arrayOfMaxItems() {
			String json = "[1, 2, 3, 4]";
			ComplexType schema = array(integer()).maxItems(4);
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
	
			assertFalse(result.hasProblems());
		}
	
		@Test
		public void arrayOfItemsLessThanMax() {
			String json = "[1, 2, 3]";
			ComplexType schema = array(integer()).maxItems(4);
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
	
			assertFalse(result.hasProblems());
		}
		
		/*
		 * Problem that the array is too long than required.
		 */
		@Test
		public void arrayOfItemsMoreThanMax() {
			String json = "[1, 2, 3, 4, 5]";
			ComplexType schema = array(integer()).maxItems(4);
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
	
			assertEquals(1, result.getProblems().size());
			assertTrue(result.getProblems().get(0) instanceof ArrayTooLongProblem);
			ArrayTooLongProblem p = (ArrayTooLongProblem)result.getProblems().get(0);
			assertEquals(5, p.getActualSize());
			assertEquals(4, p.getLimitSize());
			assertNotNull(p.getDescription());
		}
		
		@Test
		public void arrayOfItemsBetweenMinAndMax() {
			String json = "[1, 2, 3, 4]";
			ComplexType schema = array(integer()).minItems(3).maxItems(4);
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
	
			assertFalse(result.hasProblems());
		}
	}
	
	public static class ArrayItemTypeTest extends BaseValidationTest {
		
		@Test
		public void typeMatchSingleType() {
			String json = "[\"a\", \"b\", \"c\"]";
			ComplexType schema = array(string());
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));

			assertFalse(result.hasProblems());
		}

		@Test
		public void typeUnmatchSingleType() {
			String json = "[\"a\", \"b\", \"c\"]";
			ComplexType schema = array(number());
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
			ComplexType schema = array(number(), string(), bool());
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));

			assertFalse(result.hasProblems());
		}

		@Test
		public void typeUnmatchAllTypes() {
			String json = "[\"a\", \"b\", \"c\"]";
			ComplexType schema = array(number(), bool(), nil());
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
}
