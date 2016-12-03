package com.github.i49.hibiscus.validation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.StringReader;

import javax.json.JsonString;

import org.junit.Before;
import org.junit.Test;

import static com.github.i49.hibiscus.schema.SchemaComponents.*;
import static com.github.i49.hibiscus.formats.Formats.*;

import com.github.i49.hibiscus.formats.Format;
import com.github.i49.hibiscus.problems.InvalidFormatProblem;
import com.github.i49.hibiscus.schema.Schema;

public class StringFormatTest {

	public static class EmailTest extends BaseValidationTest {
		
		private Schema schema;
		
		@Before
		public void setUp() {
			super.setUp();
			schema = schema(array(string().format(email())));
		}
		
		@Test
		public void validEmail() {
			String json = "[\"someone@example.org\"]";
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
	
			assertFalse(result.hasProblems());
		}

		@Test
		public void invalidEmail() {
			String json = "[\"John Smith\"]";
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
	
			assertEquals(1, result.getProblems().size());
			assertTrue(result.getProblems().get(0) instanceof InvalidFormatProblem);
			InvalidFormatProblem<?> p = (InvalidFormatProblem<?>)result.getProblems().get(0);
			assertEquals("John Smith", ((JsonString)p.getActualValue()).getString());
			Format<?> f = p.getExpectedFormats().iterator().next();
			assertEquals("email", f.getName());
			assertNotNull(p.getDescription());
		}
	}

	public static class HostnameTest extends BaseValidationTest {
		
		private Schema schema;
		
		@Before
		public void setUp() {
			super.setUp();
			schema = schema(array(string().format(hostname())));
		}

		@Test
		public void validHostname() {
			String json = "[\"www.example.com\"]";
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
	
			assertFalse(result.hasProblems());
		}

		@Test
		public void invalidHostname() {
			String json = "[\"www._example_.com\"]";
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
	
			assertEquals(1, result.getProblems().size());
			assertTrue(result.getProblems().get(0) instanceof InvalidFormatProblem);
			InvalidFormatProblem<?> p = (InvalidFormatProblem<?>)result.getProblems().get(0);
			assertEquals("www._example_.com", ((JsonString)p.getActualValue()).getString());
			Format<?> f = p.getExpectedFormats().iterator().next();
			assertEquals("hostname", f.getName());
			assertNotNull(p.getDescription());
		}

		@Test
		public void ipaddress() {
			String json = "[\"192.168.80.1\"]";
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
	
			assertEquals(1, result.getProblems().size());
			assertTrue(result.getProblems().get(0) instanceof InvalidFormatProblem);
			InvalidFormatProblem<?> p = (InvalidFormatProblem<?>)result.getProblems().get(0);
			assertEquals("192.168.80.1", ((JsonString)p.getActualValue()).getString());
			Format<?> f = p.getExpectedFormats().iterator().next();
			assertEquals("hostname", f.getName());
			assertNotNull(p.getDescription());
		}

		@Test
		public void withPort() {
			String json = "[\"www.example.com:80\"]";
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
	
			assertEquals(1, result.getProblems().size());
			assertTrue(result.getProblems().get(0) instanceof InvalidFormatProblem);
			InvalidFormatProblem<?> p = (InvalidFormatProblem<?>)result.getProblems().get(0);
			assertEquals("www.example.com:80", ((JsonString)p.getActualValue()).getString());
			Format<?> f = p.getExpectedFormats().iterator().next();
			assertEquals("hostname", f.getName());
			assertNotNull(p.getDescription());
		}
	}

	public static class Ipv4Test extends BaseValidationTest {
		
		private Schema schema;
		
		@Before
		public void setUp() {
			super.setUp();
			schema = schema(array(string().format(ipv4())));
		}

		@Test
		public void inet4address() {
			String json = "[\"192.0.2.0\"]";
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
	
			assertFalse(result.hasProblems());
		}

		@Test
		public void inet6address() {
			String json = "[\"2001:db8::\"]";
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
	
			assertEquals(1, result.getProblems().size());
			assertTrue(result.getProblems().get(0) instanceof InvalidFormatProblem);
			InvalidFormatProblem<?> p = (InvalidFormatProblem<?>)result.getProblems().get(0);
			assertEquals("2001:db8::", ((JsonString)p.getActualValue()).getString());
			Format<?> f = p.getExpectedFormats().iterator().next();
			assertEquals("ipv4", f.getName());
			assertNotNull(p.getDescription());
		}

		@Test
		public void hostname() {
			String json = "[\"www.example.com\"]";
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
	
			assertEquals(1, result.getProblems().size());
			assertTrue(result.getProblems().get(0) instanceof InvalidFormatProblem);
			InvalidFormatProblem<?> p = (InvalidFormatProblem<?>)result.getProblems().get(0);
			assertEquals("www.example.com", ((JsonString)p.getActualValue()).getString());
			Format<?> f = p.getExpectedFormats().iterator().next();
			assertEquals("ipv4", f.getName());
			assertNotNull(p.getDescription());
		}
	}

	public static class Ipv6Test extends BaseValidationTest {
		
		private Schema schema;
		
		@Before
		public void setUp() {
			super.setUp();
			schema = schema(array(string().format(ipv6())));
		}

		@Test
		public void inet6address() {
			String json = "[\"2001:db8::\"]";
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
	
			assertFalse(result.hasProblems());
		}

		@Test
		public void inet4address() {
			String json = "[\"192.0.2.0\"]";
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
	
			assertEquals(1, result.getProblems().size());
			assertTrue(result.getProblems().get(0) instanceof InvalidFormatProblem);
			InvalidFormatProblem<?> p = (InvalidFormatProblem<?>)result.getProblems().get(0);
			assertEquals("192.0.2.0", ((JsonString)p.getActualValue()).getString());
			Format<?> f = p.getExpectedFormats().iterator().next();
			assertEquals("ipv6", f.getName());
			assertNotNull(p.getDescription());
		}

		@Test
		public void hostname() {
			String json = "[\"www.example.com\"]";
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
	
			assertEquals(1, result.getProblems().size());
			assertTrue(result.getProblems().get(0) instanceof InvalidFormatProblem);
			InvalidFormatProblem<?> p = (InvalidFormatProblem<?>)result.getProblems().get(0);
			assertEquals("www.example.com", ((JsonString)p.getActualValue()).getString());
			Format<?> f = p.getExpectedFormats().iterator().next();
			assertEquals("ipv6", f.getName());
			assertNotNull(p.getDescription());
		}
	}
	
	public static class AnyURITest extends BaseValidationTest {

		private Schema schema;
		
		@Before
		public void setUp() {
			super.setUp();
			schema = schema(array(string().format(anyURI())));
		}

		@Test
		public void url() {
			String json = "[\"http://www.ietf.org/rfc/rfc2396.txt\"]";
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
	
			assertFalse(result.hasProblems());
		}

		@Test
		public void urn() {
			String json = "[\"urn:oasis:names:specification:docbook:dtd:xml:4.1.2\"]";
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
	
			assertFalse(result.hasProblems());
		}

		@Test
		public void relative() {
			String json = "[\"../path/to/index.html\"]";
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
	
			assertFalse(result.hasProblems());
		}

		@Test
		public void includingSpace() {
			String json = "[\"http://example.com/ index.html\"]";
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
	
			assertEquals(1, result.getProblems().size());
			assertTrue(result.getProblems().get(0) instanceof InvalidFormatProblem);
			InvalidFormatProblem<?> p = (InvalidFormatProblem<?>)result.getProblems().get(0);
			assertEquals("http://example.com/ index.html", ((JsonString)p.getActualValue()).getString());
			Format<?> f = p.getExpectedFormats().iterator().next();
			assertEquals("anyURI", f.getName());
			assertNotNull(p.getDescription());
		}
	}	

	public static class AbsoluteURITest extends BaseValidationTest {

		private Schema schema;
		
		@Before
		public void setUp() {
			super.setUp();
			schema = schema(array(string().format(absoluteURI())));
		}

		@Test
		public void url() {
			String json = "[\"http://www.ietf.org/rfc/rfc2396.txt\"]";
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
	
			assertFalse(result.hasProblems());
		}

		@Test
		public void urn() {
			String json = "[\"urn:oasis:names:specification:docbook:dtd:xml:4.1.2\"]";
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
	
			assertFalse(result.hasProblems());
		}

		@Test
		public void relativeURI() {
			String json = "[\"../path/to/index.html\"]";
			JsonValidator validator = new BasicJsonValidator(schema);
			result = validator.validate(new StringReader(json));
	
			assertEquals(1, result.getProblems().size());
			assertTrue(result.getProblems().get(0) instanceof InvalidFormatProblem);
			InvalidFormatProblem<?> p = (InvalidFormatProblem<?>)result.getProblems().get(0);
			assertEquals("../path/to/index.html", ((JsonString)p.getActualValue()).getString());
			Format<?> f = p.getExpectedFormats().iterator().next();
			assertEquals("absoluteURI", f.getName());
			assertNotNull(p.getDescription());
		}
	}	
}
