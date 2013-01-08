package com.luki.mlbio.hackathon.portfolio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FactorCalculator {

	public FactorCalculator() {

	}

	/**
	 * Calculate Sum(x)
	 * 
	 * @param x
	 * @return
	 */
	public Double sum(List<Double> x) {
		Double sum = 0D;
		for (Double x_i : x) {
			sum += x_i;
		}
		return sum;
	}

	/**
	 * Calculate Mean(x) = sum(x)/n
	 * 
	 * @param x
	 * @return
	 */
	public Double mean(List<Double> x) {

		Double ret = this.sum(x);
		if (x.size() > 0) {
			ret = ret / x.size();
		}
		return ret;

	}

	/**
	 * Calculate central moments in single loop
	 * source:http://en.wikipedia.org/wiki/Algorithms_for_calculating_variance
	 * 
	 * @param data
	 * @return
	 */
	public Moments centralMoments(List<Double> data) {
		Integer n = 0;
		Double mean = 0D;
		Double M2 = 0D;
		Double M3 = 0D;
		Double M4 = 0D;

		for (Double x : data) {
			Integer n1 = n;
			n = n + 1;
			Double delta = x - mean;
			Double delta_n = delta / n;
			Double delta_n2 = delta_n * delta_n;
			Double term1 = delta * delta_n * n1;
			mean = mean + delta_n;
			M4 = M4 + term1 * delta_n2 * (n * n - 3 * n + 3) + 6 * delta_n2
					* M2 - 4 * delta_n * M3;
			M3 = M3 + term1 * delta_n * (n - 2) - 3 * delta_n * M2;
			M2 = M2 + term1;
		}

		Moments ret = new Moments();
		ret.mean = mean;
		ret.variance = M2 / (n - 1);
		ret.variance_n = M2 / n;
		ret.sd = Math.sqrt(M2 / (n - 1));
		ret.sd_n = Math.sqrt(M2 / n);
		ret.skewness = (Math.sqrt(n) * M3) / (Math.pow(M2, 3D / 2D));
		ret.kurtosis = (n * M4) / (M2 * M2) - 3D;

		return ret;
	}

	/**
	 * Calculate covaraince in two pass
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public Double covariance(List<Double> x, List<Double> y) {
		Double meanX = this.mean(x);
		Double meanY = this.mean(y);

		return this.covariance(x, y, meanX, meanY);
	}

	/**
	 * Calculate covariance in single pass with mean(X) and mean(Y) provided
	 * 
	 * @param x
	 * @param y
	 * @param meanX
	 * @param meanY
	 * @return
	 */
	public Double covariance(List<Double> x, List<Double> y, Double meanX,
			Double meanY) {

		if (x.size() != y.size()) {
			// throw new RuntimeException("size(x) != size(y)");
			return null;
		}
		Double covariance = 0D;
		Integer n = x.size();

		for (int i = 0; i < n; i++) {
			Double a = x.get(i) - meanX;
			Double b = y.get(i) - meanY;
			covariance += (a * b) / n;
		}

		return covariance;
	}

	/**
	 * Calculate correlation coefficient corr(X,Y) = cov(X,Y)/(sd(X)*sd(Y))
	 * 
	 * @param covXY
	 * @param sdX
	 * @param sdY
	 * @return
	 */
	public Double correlation(Double covXY, Double sdX, Double sdY) {
		Double correlation = covXY / (sdX * sdY);
		return correlation;
	}

	/**
	 * Calculate ZCR - Number of elements crossing zero
	 * 
	 * @param x
	 * @return
	 * @deprecated
	 * @see #crossingRates
	 */
	@Deprecated
	public Integer zeroCrossingRate(List<Double> x) {
		Integer ZCR = 0;

		if (x.size() <= 2) {
			return null;
		}

		Double x_im1 = x.get(0);
		for (int i = 1; i < x.size(); i++) {
			Double x_i = x.get(i);
			if ((x_i > 0 && x_im1 < 0) || (x_i < 0 && x_im1 > 0)) {
				ZCR++;
			}
		}

		return ZCR;
	}

	/**
	 * Calculate MCR - Number of elements crossing mean
	 * 
	 * @param x
	 * @param meanX
	 * @return
	 * @deprecated
	 * @see #crossingRates
	 */
	@Deprecated
	public Integer meanCrossingRate(List<Double> x, Double meanX) {
		Integer MCR = 0;

		if (x.size() <= 2) {
			return null;
		}

		Double x_im1 = x.get(0);
		for (int i = 1; i < x.size(); i++) {
			Double x_i = x.get(i);
			if ((x_i > meanX && x_im1 < meanX)
					|| (x_i < meanX && x_im1 > meanX)) {
				MCR++;
			}
		}

		return MCR;
	}


	/**
	 * Calculate Average Resultant Acceleration - ARA =
	 * ARA=(sqrt(x_i^2+y_i^2+z_i^2 )) ̅
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	public Double averageResultantAcceleration(List<Double> x, List<Double> y,
			List<Double> z) {

		if (!((x.size() == y.size()) && (x.size() == z.size()))) {
			return null;
		}
		Integer n = x.size();
		Double ARA = 0D;

		for (int i = 0; i < n; i++) {
			Double element = Math.sqrt(Math.pow(x.get(i), 2)
					+ Math.pow(x.get(i), 2) + Math.pow(x.get(i), 2));
			ARA += element / n;
		}

		return ARA;
	}

	/**
	 * Calculate magnitude = sum(sqrt(x_i^2 + y_i^2 + z_i^2))
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	public Double magnitude(List<Double> x, List<Double> y, List<Double> z) {

		if (!((x.size() == y.size()) && (x.size() == z.size()))) {
			return null;
		}
		Integer n = x.size();
		Double magnitude = 0D;

		for (int i = 0; i < n; i++) {
			Double element = Math.sqrt(Math.pow(x.get(i), 2)
					+ Math.pow(x.get(i), 2) + Math.pow(x.get(i), 2));
			magnitude += element;
		}

		return magnitude;
	}

	/**
	 * Calculate 10 bins distribution
	 * 
	 * @param x
	 * @return
	 */
	@Deprecated
	public ArrayList<Integer> binnedDistribution10(List<Double> x) {

		if (x.size() == 0) {
			return null;
		}
		Double min = Collections.min(x);
		Double max = Collections.max(x);

		ArrayList<Integer> hist = this.binnedDistributionK(x, min, max, 10);

		return hist;
	}

	public ArrayList<Integer> binnedDistributionK(List<Double> x, Double min,
			Double max, Integer k) {

		if (x.size() == 0) {
			return null;
		}
		Double range = max - min;
		Double segmentSize = range / k;

		Double[] borders = new Double[k + 1];
		borders[0] = min;
		borders[k] = max;
		for (int i = 1; i < k; i++) {
			borders[i] = borders[i - 1] + segmentSize;
		}

		int[] bins = new int[k];

		for (int i = 0; i < x.size(); i++) {
			for (int b = 0; b < k; b++) {
				Double x_i = x.get(i);
				if (x_i >= borders[b] && x_i <= borders[b + 1]) {
					bins[b] = bins[b] + 1;
					break;
				}
			}
		}

		ArrayList<Integer> hist = new ArrayList<>();
		for (int i = 0; i < k; i++) {
			hist.add(bins[i]);
		}
		// ArrayList<Integer> hist = (ArrayList<Integer>) Arrays.asList(bins);

		return hist;
	}

	/**
	 * Calculate Average Absolute Difference - AAD = sum(abs(x_i -
	 * x_(i-1))/(n-1)
	 * 
	 * @param x
	 * @return
	 */
	public Double averageAbsoluteDifference(List<Double> x) {
		if (x.size() == 0) {
			return null;
		}

		Double AAD = 0D;
		Integer n = x.size();

		Double x_im1 = x.get(0);
		for (int i = 1; i < n; i++) {
			Double x_i = x.get(i);
			Double absDifference = Math.abs(x_i - x_im1);

			AAD += absDifference / (n - 1);
		}

		return AAD;
	}

	/**
	 * Calculate Average Absolute Value - AAV = sum(abs(x_i)) / n
	 * 
	 * @param x
	 * @return
	 */
	public Double averageAbsoluteValue(List<Double> x) {
		if (x.size() == 0) {
			return null;
		}

		Double avgAbs = 0D;
		Integer n = x.size();

		for (int i = 0; i < n; i++) {
			Double x_i = Math.abs(x.get(i));

			avgAbs += x_i / n;
		}

		return avgAbs;
	}

	/**
	 * Calculate Root Mean Square - RMS = sqrt(sum((x_i)^2)/n)
	 * 
	 * @param x
	 * @return
	 */
	public Double rootMeanSquare(List<Double> x) {
		if (x.size() == 0) {
			return null;
		}

		Integer n = x.size();
		Double rms = 0D;

		for (int i = 0; i < n; i++) {
			Double x_i = Math.pow(x.get(i), 2);

			rms += x_i / n;
		}

		rms = Math.sqrt(rms);

		return rms;

	}

}
