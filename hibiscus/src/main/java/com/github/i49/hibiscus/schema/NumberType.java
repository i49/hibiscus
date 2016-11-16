package com.github.i49.hibiscus.schema;

import java.math.BigDecimal;
import java.util.List;

import javax.json.JsonNumber;
import javax.json.JsonValue;

import com.github.i49.hibiscus.common.Bound;
import com.github.i49.hibiscus.common.TypeId;
import com.github.i49.hibiscus.problems.LessThanMinimumProblem;
import com.github.i49.hibiscus.problems.MoreThanMaximumProblem;
import com.github.i49.hibiscus.problems.NotLessThanMaximumProblem;
import com.github.i49.hibiscus.problems.NotMoreThanMinimumProblem;
import com.github.i49.hibiscus.problems.Problem;

/**
 * JSON number including integer.
 */
public class NumberType extends SimpleType {

	private BigDecimal minimum;
	private boolean exclusiveMinimum;
	private BigDecimal maximum;
	private boolean exclusiveMaximum;
	
	@Override
	public TypeId getTypeId() {
		return TypeId.NUMBER;
	}
	
	@Override
	public void validateInstance(JsonValue value, List<Problem> problems) {
		super.validateInstance(value, problems);
		JsonNumber number = (JsonNumber)value;
		validateAgainstRange(number, problems);
	}

	/**
	 * Specifies minimum value of this type.
	 * @param value minimum value of this type.
	 * @return this type.
	 */
	public NumberType min(long value) {
		return min(BigDecimal.valueOf(value));
	}

	/**
	 * Specifies minimum value of this type.
	 * @param value minimum value of this type.
	 * @return this type.
	 */
	public NumberType min(BigDecimal value) {
		this.minimum = value;
		return this;
	}

	/**
	 * Specifies whether the minimum value is exclusive or not.
	 * @param exclusive true if exclusive.
	 * @return this type.
	 */
	public NumberType exclusiveMin(boolean exclusive) {
		this.exclusiveMinimum = exclusive;
		return this;
	}

	/**
	 * Specifies maximum value of this type.
	 * @param value maximum value of this type.
	 * @return this type.
	 */
	public NumberType max(long value) {
		return max(BigDecimal.valueOf(value));
	}

	/**
	 * Specifies maximum value of this type.
	 * @param value maximum value of this type.
	 * @return this type.
	 */
	public NumberType max(BigDecimal value) {
		this.maximum = value;
		return this;
	}
	
	/**
	 * Specifies whether the maximum value is exclusive or not.
	 * @param exclusive true if exclusive.
	 * @return this type.
	 */
	public NumberType exclusiveMax(boolean exclusive) {
		this.exclusiveMaximum = exclusive;
		return this;
	}
	
	private void validateAgainstRange(JsonNumber value, List<Problem> problems) {
		BigDecimal decimal = value.bigDecimalValue();
		if (this.minimum != null) {
			int result = decimal.compareTo(this.minimum);
			if (this.exclusiveMinimum) {
				if (result <= 0) {
					problems.add(new NotMoreThanMinimumProblem(value, Bound.of(this.minimum, true)));
				}
			} else {
				if (result < 0) {
					problems.add(new LessThanMinimumProblem(value, Bound.of(this.minimum, false)));
				}
			}
		}
		if (this.maximum != null) {
			int result = decimal.compareTo(this.maximum);
			if (this.exclusiveMaximum) {
				if (result >= 0) {
					problems.add(new NotLessThanMaximumProblem(value, Bound.of(this.maximum, true)));
				}
			} else {
				if (result > 0) {
					problems.add(new MoreThanMaximumProblem(value, Bound.of(this.maximum, false)));
				}
			}
		}
	}
}
