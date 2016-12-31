package com.github.i49.hibiscus.schema;

import java.util.Set;

import com.github.i49.hibiscus.common.TypeId;

/**
 * An immutable set of {@link JsonType}s, each of which has an unique {@link TypeId}.
 */
public interface TypeSet {
	
	/**
	 * Returns the empty set of this type.
	 * @return the empty set of this type.
	 */
	static TypeSet empty() {
		return TypeSetImpl.EMPTY;
	}

	/**
	 * Creates a new instance containing only one type specified.
	 * @param type the {@link JsonType} to be added to this set.
	 * @return a new set containing the type specified.
	 */
	static TypeSet of(JsonType type) {
		return new TypeSetImpl(type);
	}

	/**
	 * Creates a new instance containing types specified.
	 * @param types the {@link JsonType} to be added to this set.
	 * @return a new set containing all types specified.
	 */
	static TypeSet of(JsonType... types) {
		return new TypeSetImpl(types);
	}
	
	/**
	 * Creates a new instance containing one or more types specified.
	 * @param type the {@link JsonType} to be added to this set.
	 * @param moreTypes the other {@link JsonType}s to be added to this set.
	 * @return a new set containing all types specified.
	 */
	static TypeSet of(JsonType type, JsonType[] moreTypes) {
		return new TypeSetImpl(type, moreTypes);
	}
	
	/**
	 * Returns a type which exists in this set and has the type identifier specified.
	 * @param typeId the identifier of the type.
	 * @return {@link JsonType} if found, or {@code null} if this set does not have such a type.
	 */
	JsonType getType(TypeId typeId);
	
	/**
	 * Returns all type identifiers of the types contained in this set.
	 * @return a set of type identifiers.
	 */
	Set<TypeId> getTypeIds();
}
