package com.github.i49.hibiscus.schema.internal;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

import com.github.i49.hibiscus.common.TypeId;
import com.github.i49.hibiscus.schema.JsonType;
import com.github.i49.hibiscus.schema.SchemaException;
import com.github.i49.hibiscus.schema.TypeSet;

/**
 * The implementation class of {@link TypeSet}.
 */
public class TypeSetImpl implements TypeSet {

	/** Empty set of this type. */
	public static final TypeSet EMPTY = new TypeSetImpl();

	private final Map<TypeId, JsonType> map = new EnumMap<>(TypeId.class);

	/**
	 * Constructs this object.
	 */
	public TypeSetImpl() {
	}
	
	public TypeSetImpl(JsonType type) {
		addType(type);
	}

	public TypeSetImpl(JsonType... types) {
		for (JsonType type: types) {
			addType(type);
		}
	}

	public TypeSetImpl(JsonType type, JsonType[] moreTypes) {
		addType(type);
		for (JsonType other: moreTypes) {
			addType(other);
		}
	}
	
	@Override
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
	
	@Override
	public Set<TypeId> getTypeIds() {
		return map.keySet();
	}

	/**
	 * Adds another type to this set.
	 * @param type the type to be added to this set.
	 */
	private void addType(JsonType type) {
		if (type == null) {
			throw new SchemaException(Messages.ONE_OF_TYPES_IS_NULL(map.size()));
		}
		TypeId typeId = type.getTypeId();
		if (map.containsKey(typeId)) {
			throw new SchemaException(Messages.ONE_OF_TYPES_IS_DUPLICATED(map.size(), typeId));
		} else {
			map.put(typeId, type);
		}
	}
}
