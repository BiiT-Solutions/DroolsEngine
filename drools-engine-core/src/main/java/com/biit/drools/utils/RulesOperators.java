package com.biit.drools.utils;

/*-
 * #%L
 * Drools Engine Core
 * %%
 * Copyright (C) 2022 - 2025 BiiT Sourcing Solutions S.L.
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * #L%
 */

import java.util.List;
import java.util.Objects;

public final class RulesOperators {
	private static final String DEFAULT_CONCAT_SEPARATOR = ", ";
	private static final int MAX_CONCAT_SEPARATOR_LENGTH = 2;
	private static final int VARIABLES_LENGTH = 3;
	private static final int LOGARITHM_BASE = 10;

	private RulesOperators() {

	}

	/**
	 * Calculates the maximum of the variables array
	 *
	 * @param variables input variables.
	 * @return max value
	 */
	public static Double calculateMaxValueFunction(List<Double> variables) {
		Double max = 0.;
		if (variables != null && !variables.isEmpty()) {
			for (Double variable : variables) {
				max = Math.max(max, variable);
			}
		} else {
			return null;
		}
		return max;
	}

	/**
	 * Calculates the minimum of the variables array
	 *
	 * @param variables input variables.
	 * @return min value
	 */
	public static Double calculateMinValueFunction(List<Double> variables) {
		if (variables != null && !variables.isEmpty()) {
			double min = Double.MAX_VALUE;
			for (Double variable : variables) {
				min = Math.min(min, variable);
			}
			return min;
		} else {
			return null;
		}
	}

	/**
	 * Calculates the average of the variables array
	 *
	 * @param variables input variables.
	 * @return average value
	 */
	public static Double calculateAvgValueFunction(List<Double> variables) {
		Double avg = 0.;
		if (variables != null && !variables.isEmpty()) {
			for (Double variable : variables) {
				avg += variable;
			}
		} else {
			return null;
		}
		return (avg / (double) variables.size());
	}

	/**
	 * Concatenate strings.
	 *
	 * @param variables input variables.
	 * @return string
	 */
	public static String concatenateStringsFunction(List<String> variables) {
		variables.removeIf(Objects::isNull);
		final StringBuilder stringBuilder = new StringBuilder();
		if (variables != null && !variables.isEmpty()) {
			for (String variable : variables) {
				if (variable != null) {
					stringBuilder.append(variable);
				}
			}
		} else {
			return "";
		}
		return stringBuilder.toString();
	}

	public static String concatenateStringsSeaparatedFunction(List<String> variables) {
		variables.removeIf(Objects::isNull);
		if (variables.size() <= 1) {
			return String.join(DEFAULT_CONCAT_SEPARATOR, variables);
		}
		// First element is a separator?
		if (variables.get(0).length() > MAX_CONCAT_SEPARATOR_LENGTH) {
			return String.join(DEFAULT_CONCAT_SEPARATOR, variables);
		}
		final String separator = variables.remove(0);
		return String.join(separator, variables);
	}

	/**
	 * Calculates the total sum of the variables array
	 *
	 * @param variables input variables.
	 * @return total value
	 */
	public static Double calculateSumValueFunction(List<Double> variables) {
		Double sum = 0.;
		if (variables != null && !variables.isEmpty()) {
			for (Double variable : variables) {
				sum += variable;
			}
		} else {
			return null;
		}
		return sum;
	}

	/**
	 * Calculates how much your monthly payment would be on a loan based on an
	 * interest rate and a constant payment schedule.
	 *
	 * @param variables input variables.
	 * @return obtained value
	 */
	public static Double calculatePmtValueFunction(List<Double> variables) {
		Double pmtValue = 0.0;
		if (variables != null && !variables.isEmpty()) {
			if (variables.size() == VARIABLES_LENGTH) {
				final double rate = variables.get(0);
				final double term = variables.get(1);
				final double amount = variables.get(2);

				final double v = 1 + rate;
				final double t = -term;
				pmtValue = (amount * rate) / (1 - Math.pow(v, t));
			}
		} else {
			return null;
		}
		return pmtValue;
	}

	/**
	 * Calculates the logarithm of any base.<br>
	 * First variable of the array is the number.<br>
	 * Second variable of the array is the base.
	 *
	 * @param variables input variables.
	 * @return obtained value
	 */
	public static Double calculateLogarithmFunction(List<Double> variables) {
		if (variables != null) {
			final double number;
			final double base;
			if (variables.size() == 1) {
				number = variables.get(0);
				base = LOGARITHM_BASE;
			} else if (variables.size() == 2) {
				number = variables.get(0);
				base = variables.get(1);
			} else {
				return null;
			}
			return Math.log(number) / Math.log(base);
		}
		return null;
	}
}
