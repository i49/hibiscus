package com.github.i49.hibiscus.validation;

import com.github.i49.hibiscus.common.JsonPointer;

/**
 * A skeletal implementation of {@link JsonContext} interface.
 */
abstract class AbstractJsonContext implements JsonContext {

	private JsonContext parent;
	
	@Override
	public JsonContext getParent() {
		return parent;
	}
	
	@Override
	public void setParent(JsonContext parent) {
		this.parent = parent;
	}

	@Override
	public JsonPointer getBasePointer() {
		if (this.parent == null) {
			return null;
		}
		return this.parent.getCurrentPointer();
	}

	@Override
	public JsonPointer getCurrentPointer() {
		JsonPointer.Builder builder = JsonPointer.builder();
		buildCurrentPointer(builder);
		return builder.build();
	}
	
	@Override
	public void buildCurrentPointer(JsonPointer.Builder builder) {
		if (this.parent != null) {
			this.parent.buildCurrentPointer(builder);
		}
	}
}
