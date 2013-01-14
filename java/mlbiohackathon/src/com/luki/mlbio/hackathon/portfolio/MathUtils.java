package com.luki.mlbio.hackathon.portfolio;

import java.util.ArrayList;
import java.util.Collections;

public class MathUtils {

	public static Double correlationCoefficient(ArrayList<Double> x,
			ArrayList<Double> y) {

		if (x == null || y == null || x.size() != y.size() || x.size() == 0) {
			return null;
		}
		int n = x.size();

		Double sumX = new Double(0);
		Double sumY = new Double(0);
		Double sumXY = new Double(0);
		Double sumSqX = new Double(0);
		Double sumSqY = new Double(0);
		for (int i = 0; i < x.size(); i++) {
			sumX += x.get(i);
			sumY += y.get(i);

			sumXY += x.get(i) * y.get(i);

			sumSqX += Math.pow(x.get(i), 2);
			sumSqY += Math.pow(y.get(i), 2);

		}
		Double corr = (n * sumXY - sumX * sumY)
				/ (Math.sqrt(n * sumSqX - Math.pow(sumX, 2)) * Math.sqrt(n
						* sumSqY - Math.pow(sumY, 2)));
		return corr;

	}

	@Deprecated
	public static Double degToRad(Double d) {
		return (d * Math.PI / 180);
	}

	public static Integer[] histogramCount(ArrayList<Double> x) {
		return histogramCount(x, 10);
	}

	public static Integer[] histogramCount(ArrayList<Double> x, int n) {

		Integer[] hist = new Integer[n];

		Double min = Collections.min(x);
		Double max = Collections.max(x);

		// Double h = (max - min) / n;
		// FIXME - min==max ???
		// FIXME - n==0 || n==1 ???
		double[] steps = ArrayUtils.linspace(min, max, n - 1);

		for (Double i : x) {
			for (int j = 1; j < steps.length; j++) {
				if (i >= steps[j - 1] && i < steps[j]) {
					hist[j - 1] = hist[j - 1] + 1;
					break;
				}
			}
		}

		return hist;
	}

	public static Double[] histogramNormalized(ArrayList<Double> x, int n) {

		Integer[] histCount = histogramCount(x, n);
		Double[] hist = new Double[n];

		for (int i = 0; i < n; i++) {
			hist[i] = (double) histCount[i] / (double) n;
		}

		return hist;
	}

	public static Double vectorNorm(ArrayList<Double> vector) {
		Double sumSqElement = 0D;

		for (Double e : vector) {
			sumSqElement += Math.pow(e, 2);
		}
		Double norm = Math.sqrt(sumSqElement);
		return norm;
	}

}
