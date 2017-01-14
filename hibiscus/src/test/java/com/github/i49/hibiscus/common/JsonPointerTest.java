package com.github.i49.hibiscus.common;

import static org.junit.Assert.*;

import org.junit.Test;
import static org.hamcrest.CoreMatchers.*;

public class JsonPointerTest {

	@Test
	public void testToString() {
		
		assertThat(JsonPointer.builder().append("foo").build().toString(), equalTo("/foo"));
		assertThat(JsonPointer.builder().append("foo").append(0).build().toString(), equalTo("/foo/0"));
		assertThat(JsonPointer.builder().append("").build().toString(), equalTo("/"));
		assertThat(JsonPointer.builder().append("a/b").build().toString(), equalTo("/a~1b"));
		assertThat(JsonPointer.builder().append("c%d").build().toString(), equalTo("/c%d"));
		assertThat(JsonPointer.builder().append("e^f").build().toString(), equalTo("/e^f"));
		assertThat(JsonPointer.builder().append("g|h").build().toString(), equalTo("/g|h"));
		assertThat(JsonPointer.builder().append("i\\j").build().toString(), equalTo("/i\\j"));
		assertThat(JsonPointer.builder().append("k\"l").build().toString(), equalTo("/k\"l"));
		assertThat(JsonPointer.builder().append(" ").build().toString(), equalTo("/ "));
		assertThat(JsonPointer.builder().append("m~n").build().toString(), equalTo("/m~0n"));		
	}
	
	@Test
	public void testEmptyToStrig() {
		assertThat(JsonPointer.builder().build().toString(), equalTo(""));
	}
	
	@Test
	public void testToURI() {
		
		assertThat(JsonPointer.builder().append("foo").build().toURI().toString(), equalTo("#/foo"));
		assertThat(JsonPointer.builder().append("foo").append(0).build().toURI().toString(), equalTo("#/foo/0"));
		assertThat(JsonPointer.builder().append("").build().toURI().toString(), equalTo("#/"));
		assertThat(JsonPointer.builder().append("a/b").build().toURI().toString(), equalTo("#/a~1b"));
		assertThat(JsonPointer.builder().append("c%d").build().toURI().toString(), equalTo("#/c%25d"));
		assertThat(JsonPointer.builder().append("e^f").build().toURI().toString(), equalTo("#/e%5Ef"));
		assertThat(JsonPointer.builder().append("g|h").build().toURI().toString(), equalTo("#/g%7Ch"));
		assertThat(JsonPointer.builder().append("i\\j").build().toURI().toString(), equalTo("#/i%5Cj"));
		assertThat(JsonPointer.builder().append("k\"l").build().toURI().toString(), equalTo("#/k%22l"));
		assertThat(JsonPointer.builder().append(" ").build().toURI().toString(), equalTo("#/%20"));
		assertThat(JsonPointer.builder().append("m~n").build().toURI().toString(), equalTo("#/m~0n"));		
	}
	
	@Test
	public void testEmptyToURI() {
		assertThat(JsonPointer.builder().build().toURI().toString(), equalTo("#"));
	}
	
	@Test
	public void testWholeDocument() {
		assertThat(JsonPointer.builder().build(), is(JsonPointer.getPointerToDocumentRoot()));
	}	
}
