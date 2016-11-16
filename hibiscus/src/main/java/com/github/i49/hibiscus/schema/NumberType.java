package com.github.i49.hibiscus.schema;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

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

	private Optional<Bound<BigDecimal>> minimum = Optional.empty();
	private Optional<Bound<BigDecimal>> maximum = Optional.empty();
	
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
	 * Specifies the inclusive lower bound of the value space for this type.
	 * @param value the inclusive lower bound value.
	 * @return this type.
	 */
	public NumberType minInclusive(long value) {
		return minInclusive(BigDecimal.valueOf(value));
	}

	/**
	 * Specifies the exclusive lower bound of the value space for this type.
	 * @param value the exclusive lower bound value.
	 * @return this type.
	 */
	public NumberType minExclusive(long value) {
		return minExclusive(BigDecimal.valueOf(value));
	}

	/**
	 * Specifies the inclusive lower bound of the value space for this type.
	 * @param value the inclusive lower bound value.
	 * @return this type.
	 */
	public NumberType minInclusive(BigDecimal value) {
		this.minimum = Optional.of(Bound.of(value, false));
		return this;
	}

	/**
	 * Specifies the exclusive lower bound of the value space for this type.
	 * @param value the exclusive lower bound value.
	 * @return this type.
	 */
	public NumberType minExclusive(BigDecimal value) {
		this.minimum = Optional.of(Bound.of(value, true));
		return this;
	}

	/**
	 * Specifies the inclusive upper bound of the value space for this type.
	 * @param value the inclusive upper bound value.
	 * @return this type.
	 */
	public NumberType maxInclusive(long value) {
		return maxInclusive(BigDecimal.valueOf(value));
	}

	/**
	 * Specifies the exclusive upper bound of the value space for this type.
	 * @param value the exclusive upper bound value.
	 * @return this type.
	 */
	public NumberType maxExclusive(long value) {
		return maxExclusive(BigDecimal.valueOf(value));
	}
	
	/**
	 * Specifies the inclusive upper bound of the value space for this type.
	 * @param value the inclusive upper bound value.
	 * @return this type.
	 */
	public NumberType maxInclusive(BigDecimal value) {
		this.maximum = Optional.of(Bound.of(value, false));
		return this;
	}
	
	/**
	 * Specifies the exclusive upper bound of the value space for this type.
	 * @param value the exclusive upper bound value.
	 * @return this type.
	 */
	public NumberType maxExclusive(BigDecimal value) {
		this.maximum = Optional.of(Bound.of(value, true));
		return this;
	}

	private void validateAgainstRange(JsonNumber value, List<Problem> problems) {
		BigDecimal decimal = value.bigDecimalValue();
		this.minimum.ifPresent(bound->{
			int result = decimal.compareTo(bound.getValue());
			if (bound.isExclusive()) {
				if (result <= 0) {
					problems.add(new NotMoreThanMinimumProblem(value, bound));
				}
			} else {
				if (result < 0) {
					problems.add(new LessThanMinimumProblem(value, bound));
				}
			}
		});
		this.maximum.ifPresent(bound->{
			int result = decimal.compareTo(bound.getValue());
			if (bound.isExclusive()) {
				if (result >= 0) {
					problems.add(new NotLessThanMaximumProblem(value, bound));
				}
			} else {
				if (result > 0) {
					problems.add(new MoreThanMaximumProblem(value, bound));
				}
			}
		});
	}
}
