package com.github.i49.hibiscus.schema;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

import com.github.i49.hibiscus.common.TypeId;

/**
 * Immutable set of JSON types.
 * Each type in this set must have unique type identifier.
 */
public final class TypeSet {

	/** Empty set of this type. */
	private static final TypeSet EMPTY = new TypeSet();
	
	private final Map<TypeId, JsonType> map;
	
	/**
	 * Returns {@code TypeSet} instance containing no types.  
	 * @return empty set.
	 */
	public static TypeSet empty() {
		return EMPTY;
	}

	/**
	 * Creates new {@code TypeSet} instance containing only one type specified.
	 * @param type the type of {@code JsonType}.
	 * @return new set containing the type specified.
	 */
	public static TypeSet of(JsonType type) {
		return new TypeSet().addType(type);
	}

	/**
	 * Creates new {@code TypeSet} instance containing types specified.
	 * @param types the type to be added to this set.
	 * @return new set containing all types specified.
	 */
	public static TypeSet of(JsonType... types) {
		TypeSet map = new TypeSet();
		for (JsonType type: types) {
			map.addType(type);
		}
		return map;
	}
	
	/**
	 * Creates new {@code TypeSet} containing types specified.
	 * @param type the type to be added to this set.
	 * @param moreTypes the other types to be added to this set.
	 * @return new set containing all types specified.
	 */
	public static TypeSet of(JsonType type, JsonType... moreTypes) {
		TypeSet map = new TypeSet();
		map.addType(type);
		for (JsonType other: moreTypes) {
			map.addType(other);
		}
		return map;
	}
	
	/**
	 * Constructs this object.
	 */
	private TypeSet() {
		this.map = new EnumMap<>(TypeId.class);
	}

	/**
	 * Returns a type which exists in this set and has specified type identifier.
	 * @param typeId the identifier of the type.
	 * @return {@code JsonType} if found, or {@code null} if this set does not have such a type.
	 */
	public JsonType getType(TypeId typeId) {
		if (typeId == null) {
			return null;
		}
		JsonType type = map.get(typeId);
		if (type != null) {
			return type;
		}
		if (typeId == TypeId.INTEGER) {
			type = map.get(TypeId.NUMBER);
			if (type != null) {
				return type;
			}
		}
		return null;
	}
	
	/**
	 * Returns all type identifiers of types contained in this set.
	 * @return set of type identifiers.
	 */
	public Set<TypeId> getTypeIds() {
		return map.keySet();
	}

	/**
	 * Adds another type to this set.
	 * @param type the type to be added.
	 * @return this set.
	 */
	private TypeSet addType(JsonType type) {
		if (type == null) {
			throw new SchemaException(Messages.ONE_OF_TYPES_IS_NULL(map.size()));
		}
		TypeId typeId = type.getTypeId();
		if (map.containsKey(typeId)) {
			throw new SchemaException(Messages.ONE_OF_TYPES_IS_DUPLICATED(map.size(), typeId));
		} else {
			map.put(typeId, type);
		}
		return this;
	}
}
