package com.luki.mlbio.hackathon.portfolio;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.math3.stat.correlation.Covariance;

/**
 * Construct a portfolio of equities that have high sharpe ratios and low
 * correlation to each other. Script proceeds by: 1. Reading data from every
 * .csv file in the execution directory 2. Calculating the sharpe ratios for
 * each equity and sorting 3. Taking the top _n_ equities according to sharpe
 * ratio 4. Computing a correlation matrix for those n equities 5. Selecting a
 * portfolio that minimizes the sum of cross-correlations
 * 
 * @author Administrator
 * 
 */
public class PortfolioOptimization {

	// Portfolio size is the number of instruments included in our 'best
	// portfolio'
	public int PORTFOLIO_SIZE = 4;

	// Stocks are sorted by sharpe ratio, then the top n stocks are analysed
	// for cross-correlation
	public int TOP_K_EQUITIES = 10;

	public ArrayList<File> files = new ArrayList<File>();
	public ArrayList<String> symbols = new ArrayList<String>();

	public int numberOfDaysInFiles = 0;

	// # Creates a 'record array', which is like a spreadsheet with a header.
	// The header is the
	// # symbol (filename without the csv extension), and the data is all
	// floats.
	// closes = np.recarray((datalength,), dtype=[(symbol, 'float') for symbol
	// in symbols])
	public HashMap<String, ArrayList<Double>> closeValues = new HashMap<String, ArrayList<Double>>();

	// # Do the same for daily returns, except one row smaller than closes.
	// daily_ret = np.recarray((datalength-1,), dtype=[(symbol, 'float') for
	// symbol in symbols])
	public HashMap<String, ArrayList<Double>> dailyReturns = new HashMap<String, ArrayList<Double>>(); // closeValues.length()-1
	public int dailyRetSize = 0;

	public ArrayList<Double> averageReturns = new ArrayList<Double>();
	public ArrayList<Double> returnStDev = new ArrayList<Double>();
	public ArrayList<Double> sharepeRatios = new ArrayList<Double>();
	public HashMap<String, ArrayList<Double>> cummulativeReturns = new HashMap<String, ArrayList<Double>>(); // closeValues.length()-1

	public Double[][] corrMatrix = new Double[TOP_K_EQUITIES][TOP_K_EQUITIES];

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PortfolioOptimization p = new PortfolioOptimization();
		p.optimize();

	}

	public void fillData() {
		// # Get an array of file names in the current directory ending with csv
		// files = [fi for fi in os.listdir('.') if fi.endswith(".csv")]
		//
		// # Grab a second array with just the names, for convenience. These are
		// used
		// # to name columns of data.
		// symbols = [os.path.splitext(fi)[0] for fi in files]
		//
		// # Load one file so we can find out how many days of data are in it.
		// firstfile = recfromcsv(files[0])
		// datalength = len(firstfile['close'])

	}

	public void optimize() {

		for (int i = 0; i < this.symbols.size(); i++) {
			String symbol = this.symbols.get(i);
			ArrayList<Double> data = null;
			// TODO - Load data (last n days)

			// Make sure the record have same amount of day data
			if (data.size() != this.numberOfDaysInFiles) {
				System.out.println(this.symbols.get(i)
						+ " Have wrong amount of data");
				continue;
			}
			// # Read the 'close' column of the data and reverse the numbers
			Collections.reverse(data);

			ArrayList<Double> dailyRet = new ArrayList<Double>();
			for (int x_t = 1; x_t < data.size(); x_t++) {
				dailyRet.add(data.get(x_t) - data.get(x_t - 1)
						/ data.get(x_t - 1));
			}
			this.dailyRetSize = dailyRet.size();
			this.dailyReturns.put(symbol, dailyRet);
			Double avgRet = ArrayUtils
					.meanArithmetic((Double[]) data.toArray());
			Double stdevRet = Math.sqrt(ArrayUtils.variance((Double[]) data
					.toArray()));
			this.averageReturns.add(avgRet);
			this.returnStDev.add(stdevRet);
			this.sharepeRatios
					.add((avgRet / stdevRet) * Math.sqrt(data.size()));

		}

		Double[] sharpeRatiosArray = new Double[this.sharepeRatios.size()];
		sharpeRatiosArray = this.sharepeRatios.toArray(sharpeRatiosArray);
		ArrayIndexComparator comparator = new ArrayIndexComparator(
				sharpeRatiosArray);
		Integer[] sortedSharepeIndice = comparator.createIndexArray();
		Arrays.sort(sortedSharepeIndice, comparator);

		// Reduce to only topK
		Integer[] sortedSharepeIndiceTopK = Arrays.copyOfRange(
				sortedSharepeIndice, 0,
				Math.min(this.TOP_K_EQUITIES, this.symbols.size()));

		// Fill covData table
		Double[][] covData = new Double[sortedSharepeIndiceTopK.length][this.dailyRetSize];
		for (int i = 0; i < sortedSharepeIndiceTopK.length; i++) {
			String symbol = this.symbols.get(i);
			covData[i] = (Double[]) this.dailyReturns.get(symbol).toArray();
		}

		Double[][] correlationMatrix = new Double[this.dailyRetSize][this.TOP_K_EQUITIES];

		for (int x = 0; x < correlationMatrix.length; x++) {
			for (int y = 0; y < correlationMatrix[x].length; y++) {

			}
		}
		// new Covariance().covariance(x, y)

		// # Now make a correlation matrix for the top n equities
		// cormat = np.corrcoef(cov_data.transpose())
		//
		// # Create all possible combinations of the n top equites for the given
		// portfolio size.
		// portfolios = list(combinations(range(0, top_n_equities),
		// portfolio_size))
		this.TOP_K_EQUITIES = 3;
		List<List<Integer>> portfolios = new LinkedList<List<Integer>>();

		Integer[] range = { 1, 2, 3, 4, 5, 6, 7 };
		portfolios.addAll(ArrayUtils.combination(Arrays.asList(range),
				this.TOP_K_EQUITIES));
		System.out.println(portfolios);

		Double[] totalSumCorrelation = new Double[portfolios.size()];

		for (int i = 0; i < portfolios.size(); i++) {
			List<Integer> p = portfolios.get(i);

			Double corrSum = 0D;

			List<List<Integer>> corrCombination = new LinkedList<List<Integer>>();
			corrCombination.addAll(ArrayUtils.combination(p, 2));
			for(List<Integer> c:corrCombination){
				System.out.print(c.get(0)+"_"+c.get(1)+"\t");
//				corrSum += correlationMatrix[c.get(0)][c.get(1)];
			}
			System.out.println();

			totalSumCorrelation[i] = corrSum;
		}

		
		Double minCorr = totalSumCorrelation[0];
		int minIndex = 0;
		for (int i = 0;i<totalSumCorrelation.length;i++){
			Double c  =totalSumCorrelation[i];
			if (c > minCorr){
				minCorr = c;
				minIndex = i;
			}
		}
		
		
		List<Integer> bestPortfolio  =	portfolios.get(minIndex);
		
		System.out.println("Best portfolio");
		for (Integer i:bestPortfolio){
			int sortedIndice = sortedSharepeIndice[i];
			String symbol = this.symbols.get(sortedIndice);
			System.out.println(i + " "+sortedIndice+" "+symbol);
		}
		
		
		//
		// # Find the portfolio with the smallest sum of correlations, and
		// convert that back into
		// # the instrument names via a lookup in the symbols array
		// best_portfolio=[symbols[sorted_sharpe_indices[i]] for i in
		// portfolios[total_corr.index(np.nanmin(total_corr))]]
		// print(best_portfolio)

	}

}
