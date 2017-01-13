package com.github.i49.hibiscus.validation;

import static com.github.i49.hibiscus.schema.SchemaComponents.*;
import static com.github.i49.hibiscus.validation.CustomAssertions.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.io.StringReader;
import java.util.List;

import javax.json.JsonObject;
import javax.json.JsonValue;

import org.junit.Test;

import com.github.i49.hibiscus.formats.Formats;
import com.github.i49.hibiscus.problems.Problem;
import com.github.i49.hibiscus.problems.UnknownPropertyProblem;
import com.github.i49.hibiscus.schema.Schema;

public class PatternPropertyTest {

	public static class RegexPatternPropertyTest {

		private static Schema createSchema() {
			return schema(
				object(
					pattern("1st|2nd|3rd|[4-8]th", string())	
				)
			);
		}
		
		@Test
		public void allMatch() {
			String json = "{ \"1st\": \"Mercury\", \"4th\": \"Mars\" }";

			JsonValidator validator = new BasicJsonValidator(createSchema());
			ValidationResult result = validator.validate(new StringReader(json));
			
			assertResultValid(result, json);
			assertThat(result.hasProblems(), is(false));

			JsonValue v = result.getValue();
			assertThat(v, is(instanceOf(JsonObject.class)));
			JsonObject o = (JsonObject)v;
			assertThat(o.getString("1st"), equalTo("Mercury"));
			assertThat(o.getString("4th"), equalTo("Mars"));
		}

		@Test
		public void someNotMatch() {
			String json = "{ \"2nd\": \"Venus\", \"9th\": \"Pluto\" }";

			JsonValidator validator = new BasicJsonValidator(createSchema());
			ValidationResult result = validator.validate(new StringReader(json));

			assertResultValid(result, json);
			
			List<Problem> problems = result.getProblems();
			assertThat(problems.size(), equalTo(1));
			UnknownPropertyProblem p = (UnknownPropertyProblem)problems.get(0);
			assertThat(p.getPropertyName(), equalTo("9th"));
			assertThat(p.getDescription(), is(notNullValue()));

			JsonValue v = result.getValue();
			assertThat(v, is(instanceOf(JsonObject.class)));
			JsonObject o = (JsonObject)v;
			assertThat(o.getString("2nd"), equalTo("Venus"));
			assertThat(o.getString("9th"), equalTo("Pluto"));
		}
	}

	public static class MixedPropertiesTest {

		private static Schema createSchema() {
			return schema(
				object(
					required("name", string()),
					required("center", string()),
					optional("comets", array(string())),
					pattern("1st|2nd|3rd|[4-8]th", string())	
				)
			);
		}
		
		@Test
		public void allMatch() {

			String json = "{"
					+ "\"name\": \"Solar System\","
					+ "\"center\": \"Sun\","
					+ "\"1st\": \"Mercury\","
					+ "\"4th\": \"Mars\","
					+ "\"comets\": [ \"Hale-Bopp\" ]"
					+ "}";
	
			JsonValidator validator = new BasicJsonValidator(createSchema());
			ValidationResult result = validator.validate(new StringReader(json));
			
			assertResultValid(result, json);
			assertThat(result.hasProblems(), is(false));
			
			JsonValue v = result.getValue();
			assertThat(v, is(instanceOf(JsonObject.class)));
			JsonObject o = (JsonObject)v;
			assertThat(o.getString("name"), equalTo("Solar System"));
			assertThat(o.getString("center"), equalTo("Sun"));
			assertThat(o.getString("1st"), equalTo("Mercury"));
			assertThat(o.getString("4th"), equalTo("Mars"));
			assertThat(o.getJsonArray("comets").getString(0), equalTo("Hale-Bopp"));
		}
		
		@Test
		public void someNotMatch() {
			
			String json = "{"
					+ "\"name\": \"Solar System\","
					+ "\"center\": \"Sun\","
					+ "\"2nd\": \"Venus\","
					+ "\"9th\": \"Pluto\","
					+ "\"comets\": [ \"Hale-Bopp\" ]"
					+ "}";
	
			JsonValidator validator = new BasicJsonValidator(createSchema());
			ValidationResult result = validator.validate(new StringReader(json));
			
			assertResultValid(result, json);
			
			List<Problem> problems = result.getProblems();
			assertThat(problems.size(), equalTo(1));
			UnknownPropertyProblem p = (UnknownPropertyProblem)problems.get(0);
			assertThat(p.getPropertyName(), equalTo("9th"));
			assertThat(p.getDescription(), is(notNullValue()));

			JsonValue v = result.getValue();
			assertThat(v, is(instanceOf(JsonObject.class)));
			JsonObject o = (JsonObject)v;
			assertThat(o.getString("name"), equalTo("Solar System"));
			assertThat(o.getString("center"), equalTo("Sun"));
			assertThat(o.getString("2nd"), equalTo("Venus"));
			assertThat(o.getJsonArray("comets").getString(0), equalTo("Hale-Bopp"));
		}
	}
	
	public static class PredicatePatternPropertyTest {
		
		private static Schema createSchema() {
			return schema(
				object(
					pattern(PredicatePatternPropertyTest::palindrome, object())	
				)
			);
		}
		
		public static boolean palindrome(String s) {
			final int length = s.length();
			for (int i = 0; i < length / 2; i++) {
				if (s.charAt(i) != s.charAt(length - i - 1)) {
					return false;
				}
			}
			return true;
		}

		@Test
		public void allMatch() {
			String json = "{ \"noon\": {}, \"racecar\": {}, \"kayak\": {} }";

			JsonValidator validator = new BasicJsonValidator(createSchema());
			ValidationResult result = validator.validate(new StringReader(json));
			
			assertResultValid(result, json);
			assertThat(result.hasProblems(), is(false));

			JsonValue v = result.getValue();
			assertThat(v, is(instanceOf(JsonObject.class)));
			JsonObject o = (JsonObject)v;
			assertThat(o.getJsonObject("noon"), is(notNullValue()));
			assertThat(o.getJsonObject("racecar"), is(notNullValue()));
			assertThat(o.getJsonObject("kayak"), is(notNullValue()));
		}

		@Test
		public void someNotMatch() {
			String json = "{ \"radar\": {}, \"word\": {}, \"level\": {} }";

			JsonValidator validator = new BasicJsonValidator(createSchema());
			ValidationResult result = validator.validate(new StringReader(json));
			
			assertResultValid(result, json);
			List<Problem> problems = result.getProblems();
			assertThat(problems.size(), equalTo(1));
			assertThat(problems.get(0), is(instanceOf(UnknownPropertyProblem.class)));
			UnknownPropertyProblem p0 = (UnknownPropertyProblem)problems.get(0);
			assertThat(p0.getPropertyName(), equalTo("word"));
			assertThat(p0.getDescription(), is(notNullValue()));

			JsonValue v = result.getValue();
			assertThat(v, is(instanceOf(JsonObject.class)));
			JsonObject o = (JsonObject)v;
			assertThat(o.getJsonObject("radar"), is(notNullValue()));
			assertThat(o.getJsonObject("word"), is(notNullValue()));
			assertThat(o.getJsonObject("level"), is(notNullValue()));
		}
	}
	
	public static class FormatPatternPropertyTest {

		private static Schema createSchema() {
			return schema(
				object(
					pattern(Formats.absoluteURI(), object())
				)
			);
		}
		
		@Test
		public void allMatch() {
			String json = "{"
					+ "\"http://example.com/index.html\": {},"
					+ "\"http://example.org/index.html\": {},"
					+ "\"http://example.net/index.html\": {}"
					+ "}";

			JsonValidator validator = new BasicJsonValidator(createSchema());
			ValidationResult result = validator.validate(new StringReader(json));
			
			assertResultValid(result, json);
			assertThat(result.hasProblems(), is(false));

			JsonValue v = result.getValue();
			assertThat(v, is(instanceOf(JsonObject.class)));
			JsonObject o = (JsonObject)v;
			assertThat(o.getJsonObject("http://example.com/index.html"), is(notNullValue()));
			assertThat(o.getJsonObject("http://example.org/index.html"), is(notNullValue()));
			assertThat(o.getJsonObject("http://example.net/index.html"), is(notNullValue()));
		}

		@Test
		public void someNotMatch() {
			String json = "{"
					+ "\"http://example.com/index.html\": {},"
					+ "\"../relative/path/to/index.html\": {},"
					+ "\"http://example.net/index.html\": {}"
					+ "}";

			JsonValidator validator = new BasicJsonValidator(createSchema());
			ValidationResult result = validator.validate(new StringReader(json));
			
			assertResultValid(result, json);

			List<Problem> problems = result.getProblems();
			assertThat(problems.size(), equalTo(1));
			assertThat(problems.get(0), is(instanceOf(UnknownPropertyProblem.class)));
			UnknownPropertyProblem p0 = (UnknownPropertyProblem)problems.get(0);
			assertThat(p0.getPropertyName(), equalTo("../relative/path/to/index.html"));
			assertThat(p0.getDescription(), is(notNullValue()));

			JsonValue v = result.getValue();
			assertThat(v, is(instanceOf(JsonObject.class)));
			JsonObject o = (JsonObject)v;
			assertThat(o.getJsonObject("http://example.com/index.html"), is(notNullValue()));
			assertThat(o.getJsonObject("../relative/path/to/index.html"), is(notNullValue()));
			assertThat(o.getJsonObject("http://example.net/index.html"), is(notNullValue()));
		}
	}
}
