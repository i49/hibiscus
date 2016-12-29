package com.github.i49.hibiscus.problems;

import java.util.Locale;

import javax.json.JsonArray;
import javax.json.JsonValue;

/**
 * Problem that an element in an array has the same value as other element in that array.

 * <p>This object will be instantiated each per duplication found in an array. 
 * If more than two elements were found to have the coincident value, 
 * each occurrence but the first one produces a new problem to be reported.</p>
 *  
 * <p>This problem can be caused by {@code array()} type only.</p>
 */
public class ArrayDuplicateItemProblem extends JsonValueProblem<JsonArray> {

	private final int itemIndex;
	
	/**
	 * Constructs this problem.
	 * @param value the array which has the duplicate element.
	 * @param itemIndex zero-based index that indicates where the duplicated element was found.
	 */
	public ArrayDuplicateItemProblem(JsonArray value, int itemIndex) {
		super(value);
		this.itemIndex = itemIndex;
	}
	
	/**
	 * Returns the zero-based index of the duplicate element.
	 * @return the zero-based index of the duplicate element. 
	 */
	public int getDuplicateIndex() {
		return itemIndex;
	}
	
	/**
	 * Returns the value of the duplicate element in the array.
	 * @return the value of the duplicate element.
	 */
	public JsonValue getDuplicateItem() {
		return getActualValue().get(this.itemIndex);
	}

	@Override
	protected String buildDescription(Locale locale) {
		return Messages.DUPLICATE_ITEM_PROBLEM(locale, getDuplicateIndex(), getDuplicateItem());
	}
}
