package com.github.i49.hibiscus;

public enum TypeKind {

	ARRAY,
	BOOLEAN,
	INTEGER,
	NUMBER,
	NULL,
	OBJECT,
	STRING;
	
	public boolean isCompatible(TypeKind other) {
		if (this == INTEGER && other == NUMBER) {
			return true;
		}
		return this == other;
	}
}
