package com.github.i49.hibiscus.validation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.StringReader;
import java.util.Locale;

import javax.json.JsonString;

import org.junit.Test;

import static com.github.i49.hibiscus.schema.SchemaComponents.*;

import com.github.i49.hibiscus.formats.Format;
import com.github.i49.hibiscus.problems.InvalidFormatProblem;
import com.github.i49.hibiscus.schema.Schema;

public class StringFormatTest {

	public static class FormatTest extends BaseValidationTest {
		
		@Test
		public void validEmail() {
			Schema schema = schema(array(string().format(email())));
			String json = "[\"someone@example.org\"]";
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
	
			assertFalse(result.hasProblems());
		}

		@Test
		public void invalidEmail() {
			Schema schema = schema(array(string().format(email())));
			String json = "[\"John Smith\"]";
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
	
			assertEquals(1, result.getProblems().size());
			assertTrue(result.getProblems().get(0) instanceof InvalidFormatProblem);
			InvalidFormatProblem<?> p = (InvalidFormatProblem<?>)result.getProblems().get(0);
			assertEquals("John Smith", ((JsonString)p.getActualValue()).getString());
			Format<?> f = p.getExpectedFormats().iterator().next();
			assertEquals("email", f.getName());
			assertEquals("email", f.getLocalizedString(Locale.US));
			assertNotNull(p.getDescription());
		}
	}
}
