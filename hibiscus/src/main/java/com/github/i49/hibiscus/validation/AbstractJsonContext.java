package com.github.i49.hibiscus.validation;

import java.util.concurrent.Future;

import javax.json.JsonValue;

import com.github.i49.hibiscus.common.JsonPointer;

/**
 * A skeletal implementation of {@link JsonContext} interface.
 */
abstract class AbstractJsonContext implements JsonContext {

	private JsonContext parent;
	
	/**
	 * Returns the parent context of this context. 
	 * @return the parent context.
	 */
	@Override
	public JsonContext getParent() {
		return parent;
	}
	
	/**
	 * Assigns the parent context of this context.
	 * @param parent the parent context.
	 */
	@Override
	public void setParent(JsonContext parent) {
		this.parent = parent;
	}

	@Override
	public Future<JsonValue> getSelfFuture() {
		if (this.parent == null) {
			return null;
		}
		return this.parent.getCurrentValueFuture();
	}
	
	
	@Override
	public JsonPointer getCurrentPointer() {
		JsonPointer.Builder builder = JsonPointer.builder();
		buildCurrentPointer(builder);
		return builder.build();
	}
	
	@Override
	public JsonPointer getSelfPointer() {
		if (this.parent == null) {
			return null;
		}
		return this.parent.getCurrentPointer();
	}
	
	@Override
	public void buildCurrentPointer(JsonPointer.Builder builder) {
		if (this.parent != null) {
			this.parent.buildCurrentPointer(builder);
		}
	}
}
