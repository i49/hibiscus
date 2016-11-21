package com.github.i49.hibiscus.schema;

import java.util.Set;

import com.github.i49.hibiscus.common.TypeId;

/**
 * Immutable set of JSON types.
 * Each type in this set must have unique type identifier.
 */
public interface TypeSet {

	/**
	 * Returns {@code TypeSet} instance containing no types.  
	 * @return empty set.
	 */
	static TypeSet empty() {
		return TypeSetImpl.EMPTY;
	}

	/**
	 * Creates a new {@code TypeSet} instance containing only one type specified.
	 * @param type the type of {@code JsonType}.
	 * @return new set containing the type specified.
	 */
	static TypeSet of(JsonType type) {
		return new TypeSetImpl(type);
	}

	/**
	 * Creates a new {@code TypeSet} instance containing types specified.
	 * @param types the types to be added to this set.
	 * @return new set containing all types specified.
	 */
	static TypeSet of(JsonType... types) {
		return new TypeSetImpl(types);
	}
	
	/**
	 * Creates a new {@code TypeSet} containing types specified.
	 * @param type the type to be added to this set.
	 * @param moreTypes the other types to be added to this set.
	 * @return new set containing all types specified.
	 */
	static TypeSet of(JsonType type, JsonType[] moreTypes) {
		return new TypeSetImpl(type, moreTypes);
	}
	
	/**
	 * Returns a type which exists in this set and has specified type identifier.
	 * @param typeId the identifier of the type.
	 * @return {@code JsonType} if found, or {@code null} if this set does not have such a type.
	 */
	JsonType getType(TypeId typeId);
	
	/**
	 * Returns all type identifiers of types contained in this set.
	 * @return set of type identifiers.
	 */
	Set<TypeId> getTypeIds();
}
