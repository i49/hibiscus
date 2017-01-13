package com.github.i49.hibiscus.validation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.StringReader;

import javax.json.JsonString;

import org.junit.Test;

import static com.github.i49.hibiscus.schema.SchemaComponents.*;
import static com.github.i49.hibiscus.formats.Formats.*;

import com.github.i49.hibiscus.formats.Format;
import com.github.i49.hibiscus.problems.InvalidFormatProblem;
import com.github.i49.hibiscus.schema.Schema;

import static com.github.i49.hibiscus.validation.CustomAssertions.*;

/**
 * Tests to test format() method provided by string type.
 */
public class StringFormatTest {

	/**
	 * Tests for datetime() format.
	 */
	public static class DateTimeTest {

		private static Schema createSchema() {
			return schema(array(string().format(datetime())));
		}

		@Test
		public void dateTimeInUTC() {
			String json = "[\"1985-04-12T23:20:50.52Z\"]";
			JsonValidator validator = new BasicJsonValidator(createSchema());
			ValidationResult result = validator.validate(new StringReader(json));
	
			assertResultValid(result, json);
			assertFalse(result.hasProblems());
		}

		@Test
		public void dateTimeWithOffset() {
			String json = "[\"1996-12-19T16:39:57-08:00\"]";
			JsonValidator validator = new BasicJsonValidator(createSchema());
			ValidationResult result = validator.validate(new StringReader(json));
	
			assertResultValid(result, json);
			assertFalse(result.hasProblems());
		}
		
		@Test
		public void dateTimeWithLeapSecond() {
			String json = "[\"1990-12-31T15:59:60-08:00\"]";
			JsonValidator validator = new BasicJsonValidator(createSchema());
			ValidationResult result = validator.validate(new StringReader(json));
	
			assertResultValid(result, json);
			assertFalse(result.hasProblems());
		}

		@Test
		public void dateTimeInNetherlands() {
			String json = "[\"1937-01-01T12:00:27.87+00:20\"]";
			JsonValidator validator = new BasicJsonValidator(createSchema());
			ValidationResult result = validator.validate(new StringReader(json));
	
			assertResultValid(result, json);
			assertFalse(result.hasProblems());
		}

		@Test
		public void invalidDateTime() {
			String json = "[\"1985-04-12 23:20:50.52Z\"]";
			JsonValidator validator = new BasicJsonValidator(createSchema());
			ValidationResult result = validator.validate(new StringReader(json));
	
			assertResultValid(result, json);
			assertEquals(1, result.getProblems().size());
			assertTrue(result.getProblems().get(0) instanceof InvalidFormatProblem);
			InvalidFormatProblem<?> p = (InvalidFormatProblem<?>)result.getProblems().get(0);
			assertEquals("1985-04-12 23:20:50.52Z", ((JsonString)p.getCauseValue()).getString());
			Format<?> f = p.getExpectedFormats().iterator().next();
			assertEquals("datetime", f.getName());
			assertNotNull(p.getDescription());
		}
	}
	
	/**
	 * Tests for email() format.
	 */
	public static class EmailTest {
		
		private static Schema createSchema() {
			return schema(array(string().format(email())));
		}
		
		@Test
		public void validEmail() {
			String json = "[\"someone@example.org\"]";
			JsonValidator validator = new BasicJsonValidator(createSchema());
			ValidationResult result = validator.validate(new StringReader(json));
	
			assertResultValid(result, json);
			assertFalse(result.hasProblems());
		}

		@Test
		public void invalidEmail() {
			String json = "[\"John Smith\"]";
			JsonValidator validator = new BasicJsonValidator(createSchema());
			ValidationResult result = validator.validate(new StringReader(json));
	
			assertResultValid(result, json);
			assertEquals(1, result.getProblems().size());
			assertTrue(result.getProblems().get(0) instanceof InvalidFormatProblem);
			InvalidFormatProblem<?> p = (InvalidFormatProblem<?>)result.getProblems().get(0);
			assertEquals("John Smith", ((JsonString)p.getCauseValue()).getString());
			Format<?> f = p.getExpectedFormats().iterator().next();
			assertEquals("email", f.getName());
			assertNotNull(p.getDescription());
		}
	}

	/**
	 * Tests for hostname() format.
	 */
	public static class HostnameTest {
		
		private static Schema createSchema() {
			return schema(array(string().format(hostname())));
		}

		@Test
		public void validHostname() {
			String json = "[\"www.example.com\"]";
			JsonValidator validator = new BasicJsonValidator(createSchema());
			ValidationResult result = validator.validate(new StringReader(json));
	
			assertResultValid(result, json);
			assertFalse(result.hasProblems());
		}

		@Test
		public void invalidHostname() {
			String json = "[\"www._example_.com\"]";
			JsonValidator validator = new BasicJsonValidator(createSchema());
			ValidationResult result = validator.validate(new StringReader(json));
	
			assertResultValid(result, json);
			assertEquals(1, result.getProblems().size());
			assertTrue(result.getProblems().get(0) instanceof InvalidFormatProblem);
			InvalidFormatProblem<?> p = (InvalidFormatProblem<?>)result.getProblems().get(0);
			assertEquals("www._example_.com", ((JsonString)p.getCauseValue()).getString());
			Format<?> f = p.getExpectedFormats().iterator().next();
			assertEquals("hostname", f.getName());
			assertNotNull(p.getDescription());
		}

		@Test
		public void ipaddress() {
			String json = "[\"192.168.80.1\"]";
			JsonValidator validator = new BasicJsonValidator(createSchema());
			ValidationResult result = validator.validate(new StringReader(json));
	
			assertResultValid(result, json);
			assertEquals(1, result.getProblems().size());
			assertTrue(result.getProblems().get(0) instanceof InvalidFormatProblem);
			InvalidFormatProblem<?> p = (InvalidFormatProblem<?>)result.getProblems().get(0);
			assertEquals("192.168.80.1", ((JsonString)p.getCauseValue()).getString());
			Format<?> f = p.getExpectedFormats().iterator().next();
			assertEquals("hostname", f.getName());
			assertNotNull(p.getDescription());
		}

		@Test
		public void withPort() {
			String json = "[\"www.example.com:80\"]";
			JsonValidator validator = new BasicJsonValidator(createSchema());
			ValidationResult result = validator.validate(new StringReader(json));
	
			assertResultValid(result, json);
			assertEquals(1, result.getProblems().size());
			assertTrue(result.getProblems().get(0) instanceof InvalidFormatProblem);
			InvalidFormatProblem<?> p = (InvalidFormatProblem<?>)result.getProblems().get(0);
			assertEquals("www.example.com:80", ((JsonString)p.getCauseValue()).getString());
			Format<?> f = p.getExpectedFormats().iterator().next();
			assertEquals("hostname", f.getName());
			assertNotNull(p.getDescription());
		}
	}

	/**
	 * Tests for ipv4() format.
	 */
	public static class Ipv4Test {
		
		private static Schema createSchema() {
			return schema(array(string().format(ipv4())));
		}

		@Test
		public void inet4address() {
			String json = "[\"192.0.2.0\"]";
			JsonValidator validator = new BasicJsonValidator(createSchema());
			ValidationResult result = validator.validate(new StringReader(json));
	
			assertResultValid(result, json);
			assertFalse(result.hasProblems());
		}

		@Test
		public void inet6address() {
			String json = "[\"2001:db8::\"]";
			JsonValidator validator = new BasicJsonValidator(createSchema());
			ValidationResult result = validator.validate(new StringReader(json));
	
			assertResultValid(result, json);
			assertEquals(1, result.getProblems().size());
			assertTrue(result.getProblems().get(0) instanceof InvalidFormatProblem);
			InvalidFormatProblem<?> p = (InvalidFormatProblem<?>)result.getProblems().get(0);
			assertEquals("2001:db8::", ((JsonString)p.getCauseValue()).getString());
			Format<?> f = p.getExpectedFormats().iterator().next();
			assertEquals("ipv4", f.getName());
			assertNotNull(p.getDescription());
		}

		@Test
		public void hostname() {
			String json = "[\"www.example.com\"]";
			JsonValidator validator = new BasicJsonValidator(createSchema());
			ValidationResult result = validator.validate(new StringReader(json));
	
			assertResultValid(result, json);
			assertEquals(1, result.getProblems().size());
			assertTrue(result.getProblems().get(0) instanceof InvalidFormatProblem);
			InvalidFormatProblem<?> p = (InvalidFormatProblem<?>)result.getProblems().get(0);
			assertEquals("www.example.com", ((JsonString)p.getCauseValue()).getString());
			Format<?> f = p.getExpectedFormats().iterator().next();
			assertEquals("ipv4", f.getName());
			assertNotNull(p.getDescription());
		}
	}

	/**
	 * Tests for ipv6() format.
	 */
	public static class Ipv6Test {
		
		private static Schema createSchema() {
			return schema(array(string().format(ipv6())));
		}

		@Test
		public void inet6address() {
			String json = "[\"2001:db8::\"]";
			JsonValidator validator = new BasicJsonValidator(createSchema());
			ValidationResult result = validator.validate(new StringReader(json));

			assertResultValid(result, json);
			assertFalse(result.hasProblems());
		}
		
		@Test
		public void unspecifiedAddress() {
			String json = "[\"::\"]";
			JsonValidator validator = new BasicJsonValidator(createSchema());
			ValidationResult result = validator.validate(new StringReader(json));

			assertResultValid(result, json);
			assertFalse(result.hasProblems());
		}
		
		@Test
		public void loopbackAddress() {
			String json = "[\"::1\"]";
			JsonValidator validator = new BasicJsonValidator(createSchema());
			ValidationResult result = validator.validate(new StringReader(json));

			assertResultValid(result, json);
			assertFalse(result.hasProblems());
		}
		
		@Test
		public void ipv4CompatibleAddress() {
			String json = "[\"::192.9.5.5\"]";
			JsonValidator validator = new BasicJsonValidator(createSchema());
			ValidationResult result = validator.validate(new StringReader(json));

			assertResultValid(result, json);
			assertFalse(result.hasProblems());
		}

		@Test
		public void ipv4MappedAddress() {
			String json = "[\"::FFFF:129.144.52.38\"]";
			JsonValidator validator = new BasicJsonValidator(createSchema());
			ValidationResult result = validator.validate(new StringReader(json));

			assertResultValid(result, json);
			assertFalse(result.hasProblems());
		}
	
		@Test
		public void inet4address() {
			String json = "[\"192.0.2.0\"]";
			JsonValidator validator = new BasicJsonValidator(createSchema());
			ValidationResult result = validator.validate(new StringReader(json));
	
			assertResultValid(result, json);
			assertEquals(1, result.getProblems().size());
			assertTrue(result.getProblems().get(0) instanceof InvalidFormatProblem);
			InvalidFormatProblem<?> p = (InvalidFormatProblem<?>)result.getProblems().get(0);
			assertEquals("192.0.2.0", ((JsonString)p.getCauseValue()).getString());
			Format<?> f = p.getExpectedFormats().iterator().next();
			assertEquals("ipv6", f.getName());
			assertNotNull(p.getDescription());
		}

		@Test
		public void hostname() {
			String json = "[\"www.example.com\"]";
			JsonValidator validator = new BasicJsonValidator(createSchema());
			ValidationResult result = validator.validate(new StringReader(json));
	
			assertResultValid(result, json);
			assertEquals(1, result.getProblems().size());
			assertTrue(result.getProblems().get(0) instanceof InvalidFormatProblem);
			InvalidFormatProblem<?> p = (InvalidFormatProblem<?>)result.getProblems().get(0);
			assertEquals("www.example.com", ((JsonString)p.getCauseValue()).getString());
			Format<?> f = p.getExpectedFormats().iterator().next();
			assertEquals("ipv6", f.getName());
			assertNotNull(p.getDescription());
		}
	}
	
	/**
	 * Tests for host() format.
	 */
	public static class HostTest {
		
		private static Schema createSchema() {
			return schema(array(string().format(host())));
		}
		
		@Test
		public void hostname() {
			String json = "[\"www.example.org\"]";
			JsonValidator validator = new BasicJsonValidator(createSchema());
			ValidationResult result = validator.validate(new StringReader(json));

			assertResultValid(result, json);
			assertFalse(result.hasProblems());
		}
		
		@Test
		public void hostnameAndPort() {
			String json = "[\"www.example.org:80\"]";
			JsonValidator validator = new BasicJsonValidator(createSchema());
			ValidationResult result = validator.validate(new StringReader(json));

			assertResultValid(result, json);
			assertEquals(1, result.getProblems().size());
		}

		@Test
		public void inet4Address() {
			String json = "[\"192.0.2.0\"]";
			JsonValidator validator = new BasicJsonValidator(createSchema());
			ValidationResult result = validator.validate(new StringReader(json));

			assertResultValid(result, json);
			assertFalse(result.hasProblems());
		}

		@Test
		public void inet4AddressAndPort() {
			String json = "[\"192.0.2.0:80\"]";
			JsonValidator validator = new BasicJsonValidator(createSchema());
			ValidationResult result = validator.validate(new StringReader(json));

			assertResultValid(result, json);
			assertEquals(1, result.getProblems().size());
		}
		
		@Test
		public void inet6Address() {
			String json = "[\"[FEDC:BA98:7654:3210:FEDC:BA98:7654:3210]\"]";
			JsonValidator validator = new BasicJsonValidator(createSchema());
			ValidationResult result = validator.validate(new StringReader(json));

			assertResultValid(result, json);
			assertFalse(result.hasProblems());
		}
		
		@Test
		public void shortInet6Address() {
			String json = "[\"[2010:836B:4179::836B:4179]\"]";
			JsonValidator validator = new BasicJsonValidator(createSchema());
			ValidationResult result = validator.validate(new StringReader(json));

			assertResultValid(result, json);
			assertFalse(result.hasProblems());
		}

		@Test
		public void inet6AddressAndPort() {
			String json = "[\"[FEDC:BA98:7654:3210:FEDC:BA98:7654:3210]:80\"]";
			JsonValidator validator = new BasicJsonValidator(createSchema());
			ValidationResult result = validator.validate(new StringReader(json));

			assertResultValid(result, json);
			assertEquals(1, result.getProblems().size());
		}

		@Test
		public void unmatchedSquareBrackets() {
			String json = "[\"[FEDC:BA98:7654:3210:FEDC:BA98:7654:3210]]:80\"]";
			JsonValidator validator = new BasicJsonValidator(createSchema());
			ValidationResult result = validator.validate(new StringReader(json));

			assertResultValid(result, json);
			assertEquals(1, result.getProblems().size());
		}
	}
	
	/**
	 * Tests for hostport() format.
	 */
	public static class HostportTest {
		
		private static Schema createSchema() {
			return schema(array(string().format(hostport())));
		}
		
		@Test
		public void hostname() {
			String json = "[\"www.example.org\"]";
			JsonValidator validator = new BasicJsonValidator(createSchema());
			ValidationResult result = validator.validate(new StringReader(json));

			assertResultValid(result, json);
			assertFalse(result.hasProblems());
		}
		
		@Test
		public void hostnameAndPort() {
			String json = "[\"www.example.org:80\"]";
			JsonValidator validator = new BasicJsonValidator(createSchema());
			ValidationResult result = validator.validate(new StringReader(json));

			assertResultValid(result, json);
			assertFalse(result.hasProblems());
		}

		@Test
		public void inet4Address() {
			String json = "[\"192.0.2.0\"]";
			JsonValidator validator = new BasicJsonValidator(createSchema());
			ValidationResult result = validator.validate(new StringReader(json));

			assertResultValid(result, json);
			assertFalse(result.hasProblems());
		}

		@Test
		public void inet4AddressAndPort() {
			String json = "[\"192.0.2.0:80\"]";
			JsonValidator validator = new BasicJsonValidator(createSchema());
			ValidationResult result = validator.validate(new StringReader(json));

			assertResultValid(result, json);
			assertFalse(result.hasProblems());
		}
		
		@Test
		public void inet6Address() {
			String json = "[\"[FEDC:BA98:7654:3210:FEDC:BA98:7654:3210]\"]";
			JsonValidator validator = new BasicJsonValidator(createSchema());
			ValidationResult result = validator.validate(new StringReader(json));

			assertResultValid(result, json);
			assertFalse(result.hasProblems());
		}
		
		@Test
		public void shortInet6Address() {
			String json = "[\"[2010:836B:4179::836B:4179]\"]";
			JsonValidator validator = new BasicJsonValidator(createSchema());
			ValidationResult result = validator.validate(new StringReader(json));

			assertResultValid(result, json);
			assertFalse(result.hasProblems());
		}

		@Test
		public void inet6AddressAndPort() {
			String json = "[\"[FEDC:BA98:7654:3210:FEDC:BA98:7654:3210]:80\"]";
			JsonValidator validator = new BasicJsonValidator(createSchema());
			ValidationResult result = validator.validate(new StringReader(json));

			assertResultValid(result, json);
			assertFalse(result.hasProblems());
		}

		@Test
		public void unmatchedSquareBrackets() {
			String json = "[\"[FEDC:BA98:7654:3210:FEDC:BA98:7654:3210]]:80\"]";
			JsonValidator validator = new BasicJsonValidator(createSchema());
			ValidationResult result = validator.validate(new StringReader(json));

			assertResultValid(result, json);
			assertEquals(1, result.getProblems().size());
		}

		@Test
		public void notANumberPort() {
			String json = "[\"www.example.org:abc\"]";
			JsonValidator validator = new BasicJsonValidator(createSchema());
			ValidationResult result = validator.validate(new StringReader(json));

			assertResultValid(result, json);
			assertEquals(1, result.getProblems().size());
		}
	}

	/**
	 * Tests for anyURI() format.
	 */
	public static class AnyURITest {

		private static Schema createSchema() {
			return schema(array(string().format(anyURI())));
		}

		@Test
		public void url() {
			String json = "[\"http://www.ietf.org/rfc/rfc2396.txt\"]";
			JsonValidator validator = new BasicJsonValidator(createSchema());
			ValidationResult result = validator.validate(new StringReader(json));

			assertResultValid(result, json);
			assertFalse(result.hasProblems());
		}

		@Test
		public void urn() {
			String json = "[\"urn:oasis:names:specification:docbook:dtd:xml:4.1.2\"]";
			JsonValidator validator = new BasicJsonValidator(createSchema());
			ValidationResult result = validator.validate(new StringReader(json));
	
			assertResultValid(result, json);
			assertFalse(result.hasProblems());
		}

		@Test
		public void relative() {
			String json = "[\"../path/to/index.html\"]";
			JsonValidator validator = new BasicJsonValidator(createSchema());
			ValidationResult result = validator.validate(new StringReader(json));
	
			assertResultValid(result, json);
			assertFalse(result.hasProblems());
		}

		@Test
		public void includingSpace() {
			String json = "[\"http://example.com/ index.html\"]";
			JsonValidator validator = new BasicJsonValidator(createSchema());
			ValidationResult result = validator.validate(new StringReader(json));
	
			assertResultValid(result, json);
			assertEquals(1, result.getProblems().size());
			assertTrue(result.getProblems().get(0) instanceof InvalidFormatProblem);
			InvalidFormatProblem<?> p = (InvalidFormatProblem<?>)result.getProblems().get(0);
			assertEquals("http://example.com/ index.html", ((JsonString)p.getCauseValue()).getString());
			Format<?> f = p.getExpectedFormats().iterator().next();
			assertEquals("anyURI", f.getName());
			assertNotNull(p.getDescription());
		}
	}	

	/**
	 * Tests for absoluteURI() format.
	 */
	public static class AbsoluteURITest {

		private static Schema createSchema() {
			return schema(array(string().format(absoluteURI())));
		}

		@Test
		public void url() {
			String json = "[\"http://www.ietf.org/rfc/rfc2396.txt\"]";
			JsonValidator validator = new BasicJsonValidator(createSchema());
			ValidationResult result = validator.validate(new StringReader(json));
	
			assertResultValid(result, json);
			assertFalse(result.hasProblems());
		}

		@Test
		public void urn() {
			String json = "[\"urn:oasis:names:specification:docbook:dtd:xml:4.1.2\"]";
			JsonValidator validator = new BasicJsonValidator(createSchema());
			ValidationResult result = validator.validate(new StringReader(json));
	
			assertResultValid(result, json);
			assertFalse(result.hasProblems());
		}

		@Test
		public void relativeURI() {
			String json = "[\"../path/to/index.html\"]";
			JsonValidator validator = new BasicJsonValidator(createSchema());
			ValidationResult result = validator.validate(new StringReader(json));
	
			assertResultValid(result, json);
			assertEquals(1, result.getProblems().size());
			assertTrue(result.getProblems().get(0) instanceof InvalidFormatProblem);
			InvalidFormatProblem<?> p = (InvalidFormatProblem<?>)result.getProblems().get(0);
			assertEquals("../path/to/index.html", ((JsonString)p.getCauseValue()).getString());
			Format<?> f = p.getExpectedFormats().iterator().next();
			assertEquals("absoluteURI", f.getName());
			assertNotNull(p.getDescription());
		}
	}	
}
