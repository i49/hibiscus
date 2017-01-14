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
			
			assertResultValid(result, json);
			assertThat(result.hasProblems(), is(false));
		}
	}

	public static class TypeMismatchTest {
			
		@Test
		public void notArrayButObject() {
			String json = "{}";
			Schema schema = schema(array());
			JsonValidator validator = new BasicJsonValidator(schema);
			ValidationResult result = validator.validate(new StringReader(json));

			assertResultValid(result, json);
			assertThat(result.getProblems().size(), equalTo(1));
			assertThat(result.getProblems().get(0), instanceOf(TypeMismatchProblem.class));
			TypeMismatchProblem p = (TypeMismatchProblem)result.getProblems().get(0);
			assertThat(p.getPointer().toString(), equalTo(""));
			assertThat(p.getActualType(), is(TypeId.OBJECT));
			assertThat(p.getExpectedTypes().iterator().next(), is(TypeId.ARRAY));
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

			assertResultValid(result, json);
			assertThat(result.hasProblems(), is(false));
		}

		@Test
		public void booleans() {
			String json = "[true, false, true]";
			Schema schema = schema(array(bool()));
			JsonValidator validator = new BasicJsonValidator(schema);
			ValidationResult result = validator.validate(new StringReader(json));
	
			assertResultValid(result, json);
			assertThat(result.hasProblems(), is(false));
		}
	
		@Test
		public void integers() {
			String json = "[1, 2, 3, 4, 5]";
			Schema schema = schema(array(integer()));
			JsonValidator validator = new BasicJsonValidator(schema);
			ValidationResult result = validator.validate(new StringReader(json));
	
			assertResultValid(result, json);
			assertThat(result.hasProblems(), is(false));
		}
	
		@Test
		public void numbers() {
			String json = "[1.2, 3.4, 5.6]";
			Schema schema = schema(array(number()));
			JsonValidator validator = new BasicJsonValidator(schema);
			ValidationResult result = validator.validate(new StringReader(json));
	
			assertResultValid(result, json);
			assertThat(result.hasProblems(), is(false));
		}
	
		@Test
		public void nulls() {
			String json = "[null, null, null]";
			Schema schema = schema(array(nil()));
			JsonValidator validator = new BasicJsonValidator(schema);
			ValidationResult result = validator.validate(new StringReader(json));
	
			assertResultValid(result, json);
			assertThat(result.hasProblems(), is(false));
		}
	
		@Test
		public void strings() {
			String json = "[\"abc\", \"xyz\", \"123\"]";
			Schema schema = schema(array(string()));
			JsonValidator validator = new BasicJsonValidator(schema);
			ValidationResult result = validator.validate(new StringReader(json));
	
			assertResultValid(result, json);
			assertThat(result.hasProblems(), is(false));
		}
	
		@Test
		public void arrays() {
			String json = "[[1, 2, 3], [4, 5, 6]]";
			Schema schema = schema(array(array(integer())));
			JsonValidator validator = new BasicJsonValidator(schema);
			ValidationResult result = validator.validate(new StringReader(json));
	
			assertResultValid(result, json);
			assertThat(result.hasProblems(), is(false));
		}
		
		@Test
		public void objects() {
			String json = "[{}, {}, {}]";
			Schema schema = schema(array(object()));
			JsonValidator validator = new BasicJsonValidator(schema);
			ValidationResult result = validator.validate(new StringReader(json));
	
			assertResultValid(result, json);
			assertThat(result.hasProblems(), is(false));
		}

		@Test
		public void mixed() {
			String json = "[123, \"abc\", 456, \"xyz\"]";
			Schema schema = schema(array(integer(), string()));
			JsonValidator validator = new BasicJsonValidator(schema);
			ValidationResult result = validator.validate(new StringReader(json));
	
			assertResultValid(result, json);
			assertThat(result.hasProblems(), is(false));
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
	
			assertResultValid(result, json);
			assertThat(result.hasProblems(), is(false));
		}
	
		@Test
		public void moreThanMinLength() {
			String json = "[1, 2, 3, 4]";
			JsonValidator validator = new BasicJsonValidator(schema);
			ValidationResult result = validator.validate(new StringReader(json));
	
			assertResultValid(result, json);
			assertThat(result.hasProblems(), is(false));
		}
	
		@Test
		public void lessThanMinLength() {
			String json = "[1, 2]";
			JsonValidator validator = new BasicJsonValidator(schema);
			ValidationResult result = validator.validate(new StringReader(json));
	
			assertResultValid(result, json);
			assertThat(result.getProblems().size(), equalTo(1));
			assertThat(result.getProblems().get(0), instanceOf(ArrayTooShortProblem.class));
			ArrayTooShortProblem p = (ArrayTooShortProblem)result.getProblems().get(0);
			assertThat(p.getPointer().toString(), equalTo(""));
			assertThat(p.getCauseValue().getValueType(), is(JsonValue.ValueType.ARRAY));
			assertThat(p.getActualLength(), equalTo(2));
			assertThat(p.getLimitLength(), equalTo(3));
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
	
			assertResultValid(result, json);
			assertThat(result.hasProblems(), is(false));
		}
	
		@Test
		public void lessThanMaxLength() {
			String json = "[1, 2, 3]";
			JsonValidator validator = new BasicJsonValidator(schema);
			ValidationResult result = validator.validate(new StringReader(json));
	
			assertResultValid(result, json);
			assertThat(result.hasProblems(), is(false));
		}
		
		@Test
		public void moreThanMaxLength() {
			String json = "[1, 2, 3, 4, 5]";
			JsonValidator validator = new BasicJsonValidator(schema);
			ValidationResult result = validator.validate(new StringReader(json));
	
			assertResultValid(result, json);
			assertThat(result.getProblems().size(), equalTo(1));
			assertThat(result.getProblems().get(0), instanceOf(ArrayTooLongProblem.class));
			ArrayTooLongProblem p = (ArrayTooLongProblem)result.getProblems().get(0);
			assertThat(p.getPointer().toString(), equalTo(""));
			assertThat(p.getCauseValue().getValueType(), is(JsonValue.ValueType.ARRAY));
			assertThat(p.getActualLength(), equalTo(5));
			assertThat(p.getLimitLength(), equalTo(4));
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
	
			assertResultValid(result, json);
			assertThat(result.getProblems().size(), equalTo(1));
			assertThat(result.getProblems().get(0), instanceOf(ArrayTooShortProblem.class));
			ArrayTooShortProblem p = (ArrayTooShortProblem)result.getProblems().get(0);
			assertThat(p.getPointer().toString(), equalTo(""));
			assertThat(p.getActualLength(), equalTo(2));
			assertThat(p.getLimitLength(), equalTo(3));
			assertNotNull(p.getDescription());
		}

		@Test
		public void minLength() {
			String json = "[1, 2, 3]";
			JsonValidator validator = new BasicJsonValidator(schema);
			ValidationResult result = validator.validate(new StringReader(json));
	
			assertResultValid(result, json);
			assertThat(result.hasProblems(), is(false));
		}

		@Test
		public void betweenMinAndMax() {
			String json = "[1, 2, 3, 4]";
			JsonValidator validator = new BasicJsonValidator(schema);
			ValidationResult result = validator.validate(new StringReader(json));
	
			assertResultValid(result, json);
			assertThat(result.hasProblems(), is(false));
		}

		@Test
		public void maxLength() {
			String json = "[1, 2, 3, 4, 5]";
			JsonValidator validator = new BasicJsonValidator(schema);
			ValidationResult result = validator.validate(new StringReader(json));
	
			assertResultValid(result, json);
			assertThat(result.hasProblems(), is(false));
		}

		@Test
		public void moreThanMaxLength() {
			String json = "[1, 2, 3, 4, 5, 6]";
			JsonValidator validator = new BasicJsonValidator(schema);
			ValidationResult result = validator.validate(new StringReader(json));
	
			assertResultValid(result, json);
			assertThat(result.getProblems().size(), equalTo(1));
			assertThat(result.getProblems().get(0), instanceOf(ArrayTooLongProblem.class));
			ArrayTooLongProblem p = (ArrayTooLongProblem)result.getProblems().get(0);
			assertThat(p.getPointer().toString(), equalTo(""));
			assertThat(p.getActualLength(), equalTo(6));
			assertThat(p.getLimitLength(), equalTo(5));
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
	
			assertResultValid(result, json);
			assertThat(result.getProblems().size(), equalTo(1));
			assertThat(result.getProblems().get(0), instanceOf(ArrayLengthProblem.class));
			ArrayLengthProblem p = (ArrayLengthProblem)result.getProblems().get(0);
			assertThat(p.getPointer().toString(), equalTo(""));
			assertThat(p.getActualLength(), equalTo(2));
			assertThat(p.getExpectedLength(), equalTo(3));
			assertNotNull(p.getDescription());
		}

		@Test
		public void equalToExpected() {
			String json = "[1, 2, 3]";
			JsonValidator validator = new BasicJsonValidator(schema);
			ValidationResult result = validator.validate(new StringReader(json));
	
			assertResultValid(result, json);
			assertThat(result.hasProblems(), is(false));
		}

		@Test
		public void moreThanExpected() {
			String json = "[1, 2, 3, 4]";
			JsonValidator validator = new BasicJsonValidator(schema);
			ValidationResult result = validator.validate(new StringReader(json));
	
			assertResultValid(result, json);
			assertThat(result.getProblems().size(), equalTo(1));
			assertThat(result.getProblems().get(0), instanceOf(ArrayLengthProblem.class));
			ArrayLengthProblem p = (ArrayLengthProblem)result.getProblems().get(0);
			assertThat(p.getPointer().toString(), equalTo(""));
			assertThat(p.getActualLength(), equalTo(4));
			assertThat(p.getExpectedLength(), equalTo(3));
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

			assertResultValid(result, json);
			assertThat(result.hasProblems(), is(false));
		}

		@Test
		public void typeUnmatchSingleType() {
			String json = "[\"a\", \"b\", \"c\"]";
			Schema schema = schema(array(number()));
			JsonValidator validator = new BasicJsonValidator(schema);
			ValidationResult result = validator.validate(new StringReader(json));

			assertResultValid(result, json);
			assertThat(result.getProblems().size(), equalTo(3));

			assertThat(result.getProblems().get(0), instanceOf(TypeMismatchProblem.class));
			TypeMismatchProblem p0 = (TypeMismatchProblem)result.getProblems().get(0);
			assertThat(p0.getPointer().toString(), equalTo("/0"));
			assertThat(p0.getActualType(), is(TypeId.STRING));
			assertThat(p0.getExpectedTypes().size(), equalTo(1));
			assertThat(p0.getExpectedTypes(), hasItem(TypeId.NUMBER));
			assertNotNull(p0.getDescription());

			TypeMismatchProblem p1 = (TypeMismatchProblem)result.getProblems().get(1);
			assertThat(p1.getPointer().toString(), equalTo("/1"));
			assertThat(p1.getActualType(), is(TypeId.STRING));
			assertThat(p1.getExpectedTypes().size(), equalTo(1));
			assertThat(p1.getExpectedTypes(), hasItem(TypeId.NUMBER));
			assertNotNull(p1.getDescription());

			TypeMismatchProblem p2 = (TypeMismatchProblem)result.getProblems().get(2);
			assertThat(p2.getPointer().toString(), equalTo("/2"));
			assertThat(p2.getActualType(), is(TypeId.STRING));
			assertThat(p2.getExpectedTypes().size(), equalTo(1));
			assertThat(p2.getExpectedTypes(), hasItem(TypeId.NUMBER));
			assertNotNull(p2.getDescription());
		}
		
		@Test
		public void typeMatchOneOfTypes() {
			String json = "[\"a\", \"b\", \"c\"]";
			Schema schema = schema(array(number(), string(), bool()));
			JsonValidator validator = new BasicJsonValidator(schema);
			ValidationResult result = validator.validate(new StringReader(json));

			assertResultValid(result, json);
			assertThat(result.hasProblems(), is(false));
		}

		@Test
		public void typeUnmatchAllTypes() {
			String json = "[\"a\", \"b\", \"c\"]";
			Schema schema = schema(array(number(), bool(), nil()));
			JsonValidator validator = new BasicJsonValidator(schema);
			ValidationResult result = validator.validate(new StringReader(json));

			assertResultValid(result, json);
			assertThat(result.getProblems().size(), equalTo(3));

			assertThat(result.getProblems().get(0), instanceOf(TypeMismatchProblem.class));
			TypeMismatchProblem p = (TypeMismatchProblem)result.getProblems().get(0);
			assertThat(p.getPointer().toString(), equalTo("/0"));
			assertThat(p.getActualType(), is(TypeId.STRING));
			assertThat(p.getExpectedTypes().size(), equalTo(3));
			assertThat(p.getExpectedTypes(), hasItem(TypeId.NUMBER));
			assertThat(p.getExpectedTypes(), hasItem(TypeId.BOOLEAN));
			assertThat(p.getExpectedTypes(), hasItem(TypeId.NULL));
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

			assertResultValid(result, json);
			assertThat(result.hasProblems(), is(false));
		}

		@Test
		public void duplication() {
			String json = "[\"club\", \"diamond\", \"club\", \"spade\"]";
			Schema schema = schema(array(string()).unique());
			JsonValidator validator = new BasicJsonValidator(schema);
			ValidationResult result = validator.validate(new StringReader(json));

			assertResultValid(result, json);
			assertThat(result.getProblems().size(), equalTo(1));
			assertThat(result.getProblems().get(0), instanceOf(ArrayDuplicateItemProblem.class));
			ArrayDuplicateItemProblem p = (ArrayDuplicateItemProblem)result.getProblems().get(0);
			assertThat(p.getPointer().toString(), equalTo(""));
			assertThat(p.getDuplicateIndex(), equalTo(2));
			assertThat(((JsonString)p.getDuplicateItem()).getString(), equalTo("club"));
			assertNotNull(p.getDescription());
		}

		@Test
		public void manyDuplications() {
			String json = "[123, 456, 456, 123]";
			Schema schema = schema(array(integer()).unique());
			JsonValidator validator = new BasicJsonValidator(schema);
			ValidationResult result = validator.validate(new StringReader(json));

			assertResultValid(result, json);
			assertThat(result.getProblems().size(), equalTo(2));

			assertThat(result.getProblems().get(0), instanceOf(ArrayDuplicateItemProblem.class));
			ArrayDuplicateItemProblem p0 = (ArrayDuplicateItemProblem)result.getProblems().get(0);
			assertThat(p0.getPointer().toString(), equalTo(""));
			assertThat(p0.getDuplicateIndex(), equalTo(2));
			assertThat(((JsonNumber)p0.getDuplicateItem()).intValue(), equalTo(456));
			assertNotNull(p0.getDescription());

			assertThat(result.getProblems().get(1), instanceOf(ArrayDuplicateItemProblem.class));
			ArrayDuplicateItemProblem p1 = (ArrayDuplicateItemProblem)result.getProblems().get(1);
			assertThat(p1.getPointer().toString(), equalTo(""));
			assertThat(p1.getDuplicateIndex(), equalTo(3));
			assertThat(((JsonNumber)p1.getDuplicateItem()).intValue(), equalTo(123));
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

			assertResultValid(result, json);
			assertThat(result.getProblems().size(), equalTo(6));
			
			List<Problem> problems = result.getProblems();
			
			Problem p0 = problems.get(0); 
			assertThat(p0, is(instanceOf(StringLengthProblem.class)));
			assertThat(p0.getPointer().toString(), equalTo("/0"));
			assertThat(p0.getCauseValue().getValueType(), is(JsonValue.ValueType.STRING));
			assertThat(((StringLengthProblem)p0).getCauseValue().getString(), equalTo("abcd"));

			Problem p1 = problems.get(1); 
			assertThat(p1, is(instanceOf(InclusiveLowerBoundProblem.class)));
			assertThat(p1.getPointer().toString(), equalTo("/1"));
			assertThat(p1.getCauseValue().getValueType(), is(JsonValue.ValueType.NUMBER));
			assertThat(((InclusiveLowerBoundProblem)p1).getCauseValue().intValue(), equalTo(-1));

			Problem p2 = problems.get(2); 
			assertThat(p2, is(instanceOf(ExclusiveUpperBoundProblem.class)));
			assertThat(p2.getPointer().toString(), equalTo("/2"));
			assertThat(p2.getCauseValue().getValueType(), is(JsonValue.ValueType.NUMBER));
			assertThat(((ExclusiveUpperBoundProblem)p2).getCauseValue().bigDecimalValue(), equalTo(new BigDecimal("20.0")));

			Problem p3 = problems.get(3); 
			assertThat(p3, is(instanceOf(NoSuchEnumeratorProblem.class)));
			assertThat(p3.getPointer().toString(), equalTo("/3"));
			assertThat(p3.getCauseValue().getValueType(), is(JsonValue.ValueType.TRUE));
			assertThat(((NoSuchEnumeratorProblem)p3).getCauseValue(), equalTo(JsonValue.TRUE));

			Problem p4 = problems.get(4); 
			assertThat(p4, is(instanceOf(ArrayLengthProblem.class)));
			assertThat(p4.getPointer().toString(), equalTo("/4"));
			assertThat(p4.getCauseValue().getValueType(), is(JsonValue.ValueType.ARRAY));
			JsonValue v4 = ((ArrayLengthProblem)p4).getCauseValue();
			assertThat(v4, instanceOf(JsonArray.class));
			assertThat(((JsonArray)v4).size(), equalTo(2));
			assertThat(((JsonArray)v4).getInt(0), equalTo(1));
			assertThat(((JsonArray)v4).getInt(1), equalTo(2));

			Problem p5 = problems.get(5); 
			assertThat(p5, is(instanceOf(MissingPropertyProblem.class)));
			assertThat(p5.getPointer().toString(), equalTo("/5"));
			assertThat(p5.getCauseValue().getValueType(), is(JsonValue.ValueType.OBJECT));
			JsonValue v5 = ((MissingPropertyProblem)p5).getCauseValue();
			assertThat(v5, instanceOf(JsonObject.class));
			assertThat(((JsonObject)v5).getInt("age"), equalTo(42));
		}
	}
}
