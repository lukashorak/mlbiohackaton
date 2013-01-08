package com.luki.mlbio.hackathon.portfolio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ArrayUtils {

	public static double[] add(double[] a, double[] b) {
		if (a.length != b.length) {
			return null;
		} else {
			double[] r = new double[a.length];
			for (int i = 0; i < a.length; i++) {
				r[i] = a[i] + b[i];
			}
			return r;
		}
	}

	public static double[] abs(double[] a) {
		double[] r = new double[a.length];
		for (int i = 0; i < a.length; i++) {
			r[i] = Math.abs(a[i]);
		}
		return r;
	}

	public static double[] dotTimes(double[] a, double k) {
		double[] r = new double[a.length];
		for (int i = 0; i < a.length; i++) {
			r[i] = a[i] * k;
		}
		return r;
	}

	public static double[] dotPlus(double[] a, double k) {
		double[] r = new double[a.length];
		for (int i = 0; i < a.length; i++) {
			r[i] = a[i] + k;
		}
		return r;
	}

	public static double sum(double[] a) {
		double sum = 0D;
		for (int i = 0; i < a.length; i++) {
			sum += a[i];
		}
		return sum;
	}

	public static double[] linspace(double start, double end, int n) {
		double[] ret = new double[n];
		double step = (end - start) / (n - 1);

		for (int i = 0; i < n; i++) {
			ret[i] = start + step * i;
		}
		return ret;
	}

	protected static String printArray(String name, double[] a) {
		StringBuilder sb = new StringBuilder();
		sb.append(name + ": [");
		for (int i = 0; i < a.length; i++) {
			sb.append(((int) (a[i] * 1000) / 1000.0) + " ");
		}
		sb.append("]");
		return sb.toString();
	}

	protected static String printArray(String name, int[] a) {
		StringBuilder sb = new StringBuilder();
		sb.append(name + ": [");
		for (int i = 0; i < a.length; i++) {
			sb.append((a[i] * 1000 / 1000.0) + " ");
		}
		sb.append("]");
		return sb.toString();
	}

	public static Double min(Double[] x) {
		if (x.length <= 0) {
			return null;
		}
		double min = x[0];
		for (double d : x) {
			if (d < min) {
				min = d;
			}
		}
		return min;
	}

	public static Double max(Double[] x) {
		if (x.length <= 0) {
			return null;
		}
		double max = x[0];
		for (double d : x) {
			if (d > max) {
				max = d;
			}
		}
		return max;
	}

	public static Double meanArithmetic(Double[] x) {
		double sum = 0D;
		for (Double i : x) {
			sum += i;
		}
		return (sum / x.length);
	}

	public static Double variance(Double[] x) {
		double sum = 0D;
		double sumSq = 0D;
		for (Double i : x) {
			sum += i;
			sumSq += Math.pow(i, 2);
		}
		return ((sumSq - Math.pow(sum, 2)/x.length) / (x.length-1));
	}

	public static ArrayList<Double> fromFloatToDoubleArrayList(List<Float> list) {
		ArrayList<Double> listDouble = new ArrayList<Double>();
		for (Float f : list) {
			Double d = new Double(f);
			listDouble.add(d);
		}
		return listDouble;

	}

	public static <T> List<List<T>> combination(List<T> values, int size) {

		if (0 == size) {
			return Collections.singletonList(Collections.<T> emptyList());
		}

		if (values.isEmpty()) {
			return Collections.emptyList();
		}

		List<List<T>> combination = new LinkedList<List<T>>();

		T actual = values.iterator().next();

		List<T> subSet = new LinkedList<T>(values);
		subSet.remove(actual);

		List<List<T>> subSetCombination = combination(subSet, size - 1);

		for (List<T> set : subSetCombination) {
			List<T> newSet = new LinkedList<T>(set);
			newSet.add(0, actual);
			combination.add(newSet);
		}

		combination.addAll(combination(subSet, size));

		return combination;
	}

}
