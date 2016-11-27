package com.github.i49.hibiscus.validation;

import static com.github.i49.hibiscus.schema.JsonTypes.*;
import static org.junit.Assert.*;

import java.io.StringReader;
import java.util.Set;

import javax.json.JsonString;
import javax.json.JsonValue;

import org.junit.Before;
import org.junit.Test;

import com.github.i49.hibiscus.common.TypeId;
import com.github.i49.hibiscus.problems.AssertionFailureProblem;
import com.github.i49.hibiscus.problems.StringLengthProblem;
import com.github.i49.hibiscus.problems.StringPatternProblem;
import com.github.i49.hibiscus.problems.StringTooLongProblem;
import com.github.i49.hibiscus.problems.StringTooShortProblem;
import com.github.i49.hibiscus.problems.TypeMismatchProblem;
import com.github.i49.hibiscus.problems.UnknownValueProblem;
import com.github.i49.hibiscus.schema.Schema;

public class StringValidationTest extends BaseValidationTest {

	@Test
	public void emptyString() {
		String json = "[\"\"]";
		Schema schema = schema(array(string()));
		JsonValidator validator = new BasicJsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}

	@Test
	public void normalString() {
		String json = "[\"abc\"]";
		Schema schema = schema(array(string()));
		JsonValidator validator = new BasicJsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertFalse(result.hasProblems());
	}

	@Test
	public void notStringButInteger() {
		String json = "[123]";
		Schema schema = schema(array(string()));
		JsonValidator validator = new BasicJsonValidator(schema);
		result = validator.validate(new StringReader(json));

		assertEquals(1, result.getProblems().size());
		assertTrue(result.getProblems().get(0) instanceof TypeMismatchProblem);
		TypeMismatchProblem p = (TypeMismatchProblem)result.getProblems().get(0);
		assertEquals(TypeId.INTEGER, p.getActualType());
		assertNotNull(p.getDescription());
	}

	public static class EnumerationTest extends BaseValidationTest {
	
		@Test
		public void noneOfNone() {
			String json = "[\"Spring\"]";
			Schema schema = schema(array(string().enumeration()));
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
			
			assertEquals(1, result.getProblems().size());
			assertTrue(result.getProblems().get(0) instanceof UnknownValueProblem);
			UnknownValueProblem p = (UnknownValueProblem)result.getProblems().get(0);
			assertEquals("\"Spring\"", p.getActualValue().toString());
			Set<JsonValue> expected = p.getExpectedValues();
			assertEquals(0, expected.size());
			assertNotNull(p.getDescription());
		}

		@Test
		public void oneOfOne() {
			String json = "[\"Spring\"]";
			Schema schema = schema(array(string().enumeration("Spring")));
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
			
			assertFalse(result.hasProblems());
		}

		@Test
		public void noneOfOne() {
			String json = "[\"Spring\"]";
			Schema schema = schema(array(string().enumeration("Summer")));
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
			
			assertEquals(1, result.getProblems().size());
			assertTrue(result.getProblems().get(0) instanceof UnknownValueProblem);
			UnknownValueProblem p = (UnknownValueProblem)result.getProblems().get(0);
			assertEquals("\"Spring\"", p.getActualValue().toString());
			Set<JsonValue> expected = p.getExpectedValues();
			assertEquals(1, expected.size());
			assertTrue(((JsonString)expected.iterator().next()).getString().equals("Summer"));
			assertNotNull(p.getDescription());
		}

		@Test
		public void oneOfMany() {
			String json = "[\"Spring\"]";
			Schema schema = schema(array(string().enumeration("Spring", "Summer", "Autumn", "Winter")));
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
			
			assertFalse(result.hasProblems());
		}
		
		@Test
		public void noneOfMany() {
			String json = "[\"Q2\"]";
			Schema schema = schema(array(string().enumeration("Spring", "Summer", "Autumn", "Winter")));
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
			
			assertEquals(1, result.getProblems().size());
			assertTrue(result.getProblems().get(0) instanceof UnknownValueProblem);
			UnknownValueProblem p = (UnknownValueProblem)result.getProblems().get(0);
			assertEquals("\"Q2\"", p.getActualValue().toString());
			Set<JsonValue> expected = p.getExpectedValues();
			assertEquals(4, expected.size());
			assertNotNull(p.getDescription());
		}
	}
	
	public static class MinLengthTest extends BaseValidationTest {

		private Schema schema;
		
		@Before
		public void setUp() {
			super.setUp();
			schema = schema(array(string().minLength(3)));
		}

		@Test
		public void moreThanMinLength() {
			String json = "[\"abcd\"]";
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
	
			assertFalse(result.hasProblems());
		}

		@Test
		public void minLength() {
			String json = "[\"abc\"]";
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
	
			assertFalse(result.hasProblems());
		}
	
		@Test
		public void lessThanMinLength() {
			
			String json = "[\"ab\"]";
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
	
			assertEquals(1, result.getProblems().size());
			assertTrue(result.getProblems().get(0) instanceof StringTooShortProblem);
			StringTooShortProblem p = (StringTooShortProblem)result.getProblems().get(0);
			assertEquals(2, p.getActualLength());
			assertEquals(3, p.getLimitLength());
			assertNotNull(p.getDescription());
		}
	}
	
	public static class MaxLengthTest extends BaseValidationTest {

		private Schema schema;
		
		@Before
		public void setUp() {
			super.setUp();
			schema = schema(array(string().maxLength(3)));
		}

		@Test
		public void lessThanMaxLength() {
			
			String json = "[\"ab\"]";
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
	
			assertFalse(result.hasProblems());
		}

		@Test
		public void maxLength() {
			
			String json = "[\"abc\"]";
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
	
			assertFalse(result.hasProblems());
		}
	
		@Test
		public void moreThantMaxLength() {
			
			String json = "[\"abcd\"]";
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
	
			assertEquals(1, result.getProblems().size());
			assertTrue(result.getProblems().get(0) instanceof StringTooLongProblem);
			StringTooLongProblem p = (StringTooLongProblem)result.getProblems().get(0);
			assertEquals(4, p.getActualLength());
			assertEquals(3, p.getLimitLength());
			assertNotNull(p.getDescription());
		}
	}

	public static class MinAndMaxLengthTest extends BaseValidationTest {
	
		private Schema schema;
		
		@Before
		public void setUp() {
			super.setUp();
			schema = schema(array(string().minLength(3).maxLength(5)));
		}

		@Test
		public void lessThanMinLength() {
			String json = "[\"ab\"]";
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
	
			assertEquals(1, result.getProblems().size());
			assertTrue(result.getProblems().get(0) instanceof StringTooShortProblem);
			StringTooShortProblem p = (StringTooShortProblem)result.getProblems().get(0);
			assertEquals(2, p.getActualLength());
			assertEquals(3, p.getLimitLength());
			assertNotNull(p.getDescription());
		}

		@Test
		public void minLength() {
			String json = "[\"abc\"]";
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
	
			assertFalse(result.hasProblems());
		}
		
		@Test
		public void betweenMinAndMaxLength() {
			String json = "[\"abcd\"]";
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
	
			assertFalse(result.hasProblems());
		}

		@Test
		public void maxLength() {
			String json = "[\"abcde\"]";
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
	
			assertFalse(result.hasProblems());
		}

		@Test
		public void moreThanMaxLength() {
			String json = "[\"abcdef\"]";
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
	
			assertEquals(1, result.getProblems().size());
			assertTrue(result.getProblems().get(0) instanceof StringTooLongProblem);
			StringTooLongProblem p = (StringTooLongProblem)result.getProblems().get(0);
			assertEquals(6, p.getActualLength());
			assertEquals(5, p.getLimitLength());
			assertNotNull(p.getDescription());
		}
	}
	
	public static class LenghTest extends BaseValidationTest {
		
		private Schema schema;
		
		@Before
		public void setUp() {
			super.setUp();
			schema = schema(array(string().length(3)));
		}

		@Test
		public void same() {
			String json = "[\"abc\"]";
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
	
			assertFalse(result.hasProblems());
		}

		@Test
		public void lessThanExpected() {
			String json = "[\"ab\"]";
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
	
			assertEquals(1, result.getProblems().size());
			assertTrue(result.getProblems().get(0) instanceof StringLengthProblem);
			StringLengthProblem p = (StringLengthProblem)result.getProblems().get(0);
			assertEquals(2, p.getActualLength());
			assertEquals(3, p.getExpectedLength());
			assertNotNull(p.getDescription());
		}

		@Test
		public void moreThanExpected() {
			String json = "[\"abcd\"]";
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
	
			assertEquals(1, result.getProblems().size());
			assertTrue(result.getProblems().get(0) instanceof StringLengthProblem);
			StringLengthProblem p = (StringLengthProblem)result.getProblems().get(0);
			assertEquals(4, p.getActualLength());
			assertEquals(3, p.getExpectedLength());
			assertNotNull(p.getDescription());
		}
	}

	public static class ZeroLenghTest extends BaseValidationTest {
		
		private Schema schema;
		
		@Before
		public void setUp() {
			super.setUp();
			schema = schema(array(string().length(0)));
		}

		@Test
		public void same() {
			String json = "[\"\"]";
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
	
			assertFalse(result.hasProblems());
		}

		@Test
		public void notSame() {
			String json = "[\"a\"]";
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
	
			assertEquals(1, result.getProblems().size());
			assertTrue(result.getProblems().get(0) instanceof StringLengthProblem);
			StringLengthProblem p = (StringLengthProblem)result.getProblems().get(0);
			assertEquals(1, p.getActualLength());
			assertEquals(0, p.getExpectedLength());
			assertNotNull(p.getDescription());
		}
	}
	
	public static class PatternTest extends BaseValidationTest {
		
		@Test
		public void valid() {
			String json = "[\"123-45-6789\"]";
			Schema schema = schema(array(string().pattern("\\d{3}-?\\d{2}-?\\d{4}")));
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
	
			assertFalse(result.hasProblems());
		}
	
		@Test
		public void invalid() {
			String json = "[\"9876-54-321\"]";
			Schema schema = schema(array(string().pattern("\\d{3}-?\\d{2}-?\\d{4}")));
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
	
			assertEquals(1, result.getProblems().size());
			assertTrue(result.getProblems().get(0) instanceof StringPatternProblem);
			StringPatternProblem p = (StringPatternProblem)result.getProblems().get(0);
			assertEquals("9876-54-321", p.getActualValue().getString());
			assertNotNull(p.getDescription());
		}
	}

	public static class AssertionTest extends BaseValidationTest {
	
		private Schema schema;
		
		@Before
		public void setUp() {
			super.setUp();
			schema = schema(array(string().assertion(
					v->((v.getString().length() % 2) == 0), 
					(v, l)->"Length must be a even number."
					)));
		}
		
		@Test
		public void success() {
			String json = "[\"abcd\"]";
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
	
			assertFalse(result.hasProblems());
		}
	
		@Test
		public void failure() {
			String json = "[\"abc\"]";
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
	
			assertEquals(1, result.getProblems().size());
			assertTrue(result.getProblems().get(0) instanceof AssertionFailureProblem);
			AssertionFailureProblem<?> p = (AssertionFailureProblem<?>)result.getProblems().get(0);
			assertEquals("abc", ((JsonString)p.getActualValue()).getString());
			assertEquals("Length must be a even number.", p.getDescription());
		}
	}
}
