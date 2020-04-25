package com.biit.drools.utils;

import java.util.List;

public class RulesOperators {

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
			Double min = 10000000.;
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
		StringBuilder stringBuilder = new StringBuilder();
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
		return String.join(", ", variables);
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
			if (variables.size() == 3) {
				double rate = variables.get(0);
				double term = variables.get(1);
				double amount = variables.get(2);

				double v = 1 + rate;
				double t = -term;
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
			double number;
			double base;
			if (variables.size() == 1) {
				number = variables.get(0);
				base = 10;
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
