package com.github.i49.hibiscus.validation;

import static com.github.i49.hibiscus.schema.SchemaComponents.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.StringReader;
import java.math.BigDecimal;
import java.util.List;

import javax.json.JsonArray;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonString;
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

			assertResultValid(result, json);
			assertThat(result.hasProblems(), is(false));
		}

		@Test
		public void emptyObject() {
			String json = "{}";
			Schema schema = schema(object());
			JsonValidator validator = new BasicJsonValidator(schema);
			ValidationResult result = validator.validate(new StringReader(json));

			assertResultValid(result, json);
			assertThat(result.hasProblems(), is(false));
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
			
			assertResultValid(result, json);
			assertThat(result.hasProblems(), is(false));
		}
	}
	
	public static class TypeMismatchTest {
	
		@Test
		public void notObjectButArray() {
			String json = "[1, 2, 3]";
			JsonValidator validator = new BasicJsonValidator(createSchema());
			ValidationResult result = validator.validate(new StringReader(json));

			assertResultValid(result, json);
			assertThat(result.getProblems().size(), equalTo(1));
			assertThat(result.getProblems().get(0), instanceOf(TypeMismatchProblem.class));
			TypeMismatchProblem p = (TypeMismatchProblem)result.getProblems().get(0);
			assertThat(p.getPointer().toString(), equalTo(""));
			assertThat(p.getCauseValue().getValueType(), is(JsonValue.ValueType.ARRAY));
			assertThat(p.getActualType(), is(TypeId.ARRAY));
			assertThat(p.getExpectedTypes().iterator().next(), is(TypeId.OBJECT));
			assertThat(p.getDescription(), is(notNullValue()));
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
	
			assertResultValid(result, json);
			assertThat(result.getProblems().size(), equalTo(1));
			
			assertThat(result.getProblems().get(0),  instanceOf(TypeMismatchProblem.class));
			TypeMismatchProblem p = (TypeMismatchProblem)result.getProblems().get(0);
			assertThat(p.getPointer().toString(), equalTo("/c"));
			assertThat(p.getCauseValue().getValueType(), is(JsonValue.ValueType.STRING));
			assertThat(((JsonString)p.getCauseValue()).getString(), equalTo("123.45"));
			assertThat(p.getActualType(), is(TypeId.STRING));
			assertThat(p.getExpectedTypes(), hasItem(TypeId.NUMBER));
			assertThat(p.getDescription(), is(notNullValue()));
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
	
			assertResultValid(result, json);
			List<Problem> problems = result.getProblems();
			assertThat(problems.size(), equalTo(7));
			
			TypeMismatchProblem p0 = (TypeMismatchProblem)problems.get(0);
			assertThat(p0.getActualType(), is(TypeId.INTEGER));
			assertThat(p0.getPointer().toString(), equalTo("/a"));
			assertThat(((JsonNumber)p0.getCauseValue()).intValue(), equalTo(123));

			TypeMismatchProblem p1 = (TypeMismatchProblem)problems.get(1);
			assertThat(p1.getPointer().toString(), equalTo("/b"));
			assertThat(p1.getActualType(), is(TypeId.BOOLEAN));
			assertThat(p1.getCauseValue(), is(JsonValue.TRUE));

			TypeMismatchProblem p2 = (TypeMismatchProblem)problems.get(2);
			assertThat(p2.getActualType(), is(TypeId.STRING));
			assertThat(p2.getPointer().toString(), equalTo("/c"));
			assertThat(((JsonString)p2.getCauseValue()).getString(), equalTo("abc"));

			TypeMismatchProblem p3 = (TypeMismatchProblem)problems.get(3);
			assertThat(p3.getPointer().toString(), equalTo("/d"));
			assertThat(p3.getActualType(), is(TypeId.NUMBER));
			assertThat(((JsonNumber)p3.getCauseValue()).bigDecimalValue(), equalTo(new BigDecimal("123.45")));

			TypeMismatchProblem p4 = (TypeMismatchProblem)problems.get(4);
			assertThat(p4.getPointer().toString(), equalTo("/e"));
			assertThat(p4.getActualType(), is(TypeId.OBJECT));
			assertThat(p4.getCauseValue().getValueType(), is(JsonValue.ValueType.OBJECT));

			TypeMismatchProblem p5 = (TypeMismatchProblem)problems.get(5);
			assertThat(p5.getPointer().toString(), equalTo("/f"));
			assertThat(p5.getActualType(), is(TypeId.ARRAY));
			assertThat(p5.getCauseValue().getValueType(), is(JsonValue.ValueType.ARRAY));

			TypeMismatchProblem p6 = (TypeMismatchProblem)problems.get(6);
			assertThat(p6.getPointer().toString(), equalTo("/g"));
			assertThat(p6.getActualType(), is(TypeId.NULL));
			assertThat(p6.getCauseValue(), is(JsonValue.NULL));
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
	
			assertResultValid(result, json);
			List<Problem> problems = result.getProblems();
			assertThat(problems.size(), equalTo(1));
			assertThat(problems.get(0), instanceOf(MissingPropertyProblem.class));
			MissingPropertyProblem p = (MissingPropertyProblem)problems.get(0);
			assertThat(p.getPointer().toString(), equalTo(""));
			assertThat(p.getPropertyName(), equalTo("d"));
			assertThat(p.getCauseValue().getValueType(), is(JsonValue.ValueType.OBJECT));
			assertThat(p.getDescription(), is(notNullValue()));
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
	
			assertResultValid(result, json);
			List<Problem> problems = result.getProblems();
			assertThat(problems.size(), equalTo(1));
			assertThat(problems.get(0), instanceOf(UnknownPropertyProblem.class));
			UnknownPropertyProblem p = (UnknownPropertyProblem)problems.get(0);
			assertThat(p.getPointer().toString(), equalTo(""));
			assertThat(p.getCauseValue().getValueType(), is(JsonValue.ValueType.OBJECT));
			assertThat(p.getPropertyName(), equalTo("h"));
			assertThat(p.getDescription(), is(notNullValue()));
		}
		
		@Test
		public void objectWithMoreProperties() {
			
			Schema schema = schema(createObjectType().moreProperties());

			JsonValidator validator = new BasicJsonValidator(schema);
			ValidationResult result = validator.validate(new StringReader(json));
	
			assertResultValid(result, json);
			assertThat(result.hasProblems(), is(false));
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
	
			assertResultValid(result, json);
			assertThat(result.hasProblems(), is(false));
		}
		
		@Test
		public void failure() {
			String json = "{"
					+ "\"rate\": 3"
					+ "}";

			JsonValidator validator = new BasicJsonValidator(schema);
			ValidationResult result = validator.validate(new StringReader(json));
	
			assertResultValid(result, json);
			assertThat(result.getProblems().size(), equalTo(1));
			assertThat(result.getProblems().get(0), instanceOf(AssertionFailureProblem.class));
			AssertionFailureProblem<?> p = (AssertionFailureProblem<?>)result.getProblems().get(0);
			assertThat(p.getPointer().toString(), equalTo(""));
			assertThat(p.getCauseValue().getValueType(), is(JsonValue.ValueType.OBJECT));
			assertThat(((JsonObject)p.getCauseValue()).size(), equalTo(1));
			assertThat(p.getDescription(), equalTo("Any comments please."));
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

			assertResultValid(result, json);
			assertThat(result.getProblems().size(), equalTo(6));
			
			List<Problem> problems = result.getProblems();
			
			Problem p0 = problems.get(0); 
			assertThat(p0, is(instanceOf(StringLengthProblem.class)));
			assertThat(p0.getPointer().toString(), equalTo("/a"));
			assertThat(p0.getCauseValue().getValueType(), is(JsonValue.ValueType.STRING));
			assertThat(((StringLengthProblem)p0).getCauseValue().getString(), equalTo("abcd"));

			Problem p1 = problems.get(1); 
			assertThat(p1, is(instanceOf(InclusiveLowerBoundProblem.class)));
			assertThat(p1.getPointer().toString(), equalTo("/b"));
			assertThat(p1.getCauseValue().getValueType(), is(JsonValue.ValueType.NUMBER));
			assertThat(((InclusiveLowerBoundProblem)p1).getCauseValue().intValue(), equalTo(-1));

			Problem p2 = problems.get(2); 
			assertThat(p2, is(instanceOf(ExclusiveUpperBoundProblem.class)));
			assertThat(p2.getPointer().toString(), equalTo("/c"));
			assertThat(p2.getCauseValue().getValueType(), is(JsonValue.ValueType.NUMBER));
			assertThat(((ExclusiveUpperBoundProblem)p2).getCauseValue().bigDecimalValue(), equalTo(new BigDecimal("20.0")));

			Problem p3 = problems.get(3); 
			assertThat(p3, is(instanceOf(NoSuchEnumeratorProblem.class)));
			assertThat(p3.getPointer().toString(), equalTo("/d"));
			assertThat(p3.getCauseValue().getValueType(), is(JsonValue.ValueType.TRUE));
			assertThat(((NoSuchEnumeratorProblem)p3).getCauseValue(), equalTo(JsonValue.TRUE));

			Problem p4 = problems.get(4); 
			assertThat(p4, is(instanceOf(ArrayLengthProblem.class)));
			assertThat(p4.getPointer().toString(), equalTo("/e"));
			assertThat(p4.getCauseValue().getValueType(), is(JsonValue.ValueType.ARRAY));
			JsonValue v4 = ((ArrayLengthProblem)p4).getCauseValue();
			assertThat(v4, instanceOf(JsonArray.class));
			assertThat(((JsonArray)v4).size(), equalTo(2));
			assertThat(((JsonArray)v4).getInt(0), equalTo(1));
			assertThat(((JsonArray)v4).getInt(1), equalTo(2));

			Problem p5 = problems.get(5); 
			assertThat(p5, is(instanceOf(MissingPropertyProblem.class)));
			assertThat(p5.getPointer().toString(), equalTo("/f"));
			assertThat(p5.getCauseValue().getValueType(), is(JsonValue.ValueType.OBJECT));
			JsonValue v5 = ((MissingPropertyProblem)p5).getCauseValue();
			assertThat(v5, instanceOf(JsonObject.class));
			assertThat(((JsonObject)v5).getInt("age"), equalTo(42));
		}
	}
}
