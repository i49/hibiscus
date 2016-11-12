package com.github.i49.hibiscus.schema;

public interface BaseRange {

	boolean hasMinimum();
	
	boolean hasExlusiveMinimum();
	
	boolean hasMaximum();

	boolean hasExclusiveMaximum();
}
