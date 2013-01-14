package com.luki.mlbio.hackathon.portfolio;

import java.io.File;
import java.io.FilenameFilter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.luki.mlbio.hackathon.StockManager;
import com.luki.mlbio.hackathon.model.StockDayObject;
import com.luki.mlbio.hackathon.model.StockException;

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

	/**
	 * @param args
	 * @throws StockException
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PortfolioOptimization p = new PortfolioOptimization();
		File dir = new File("c:\\PROG\\ml\\data2\\");
		p.readFromDir(dir);
		// p.fillData();
		p.optimize();

	}

	// Portfolio size is the number of instruments included in our 'best
	// portfolio'
	private int PORTFOLIO_SIZE = 4;

	// Stocks are sorted by sharpe ratio, then the top n stocks are analysed
	// for cross-correlation
	private int TOP_K_EQUITIES = 10;

//	private ArrayList<File> files = new ArrayList<File>();

	private ArrayList<String> symbols = new ArrayList<String>();
	private int numberOfDaysInFiles = 10;

	// # Creates a 'record array', which is like a spreadsheet with a header.
	// The header is the
	// # symbol (filename without the csv extension), and the data is all
	// floats.
	// closes = np.recarray((datalength,), dtype=[(symbol, 'float') for symbol
	// in symbols])
	private HashMap<String, ArrayList<Double>> closeValues = new HashMap<String, ArrayList<Double>>();

	// # Do the same for daily returns, except one row smaller than closes.
	// daily_ret = np.recarray((datalength-1,), dtype=[(symbol, 'float') for
	// symbol in symbols])
	private HashMap<String, ArrayList<Double>> dailyReturns = new HashMap<String, ArrayList<Double>>(); // closeValues.length()-1

	private int dailyRetSize = 0;
	private ArrayList<Double> averageReturns = new ArrayList<Double>();

	private ArrayList<Double> returnStDev = new ArrayList<Double>();
	private ArrayList<Double> sharepeRatios = new ArrayList<Double>();
//	private HashMap<String, ArrayList<Double>> cummulativeReturns = new HashMap<String, ArrayList<Double>>(); // closeValues.length()-1
//	private Double[][] corrMatrix = new Double[TOP_K_EQUITIES][TOP_K_EQUITIES];

	private FactorCalculator factorCalculator = new FactorCalculator();

	private DecimalFormat df = new DecimalFormat("#.##");

	public PortfolioOptimization() {

	}

	public PortfolioOptimization(Integer portfolioSize, Integer topK) {
		if (portfolioSize != null) {
			this.PORTFOLIO_SIZE = portfolioSize;
		}
		if (topK != null) {
			this.TOP_K_EQUITIES = topK;
		}
	}

	public void fillData() {
		String[] stocks = { "2317", "2318", "2319", "2320" };
		Random r = new Random(1);

		for (String s : stocks) {
			StockManager sm = new StockManager(s);
			try {
				@SuppressWarnings("unchecked")
				List<StockDayObject> dataRaw = (List<StockDayObject>) sm
						.getAll();
				ArrayList<Double> data = new ArrayList<Double>();
				for (StockDayObject o : dataRaw) {
					Double v = Double.valueOf(o.adjClose);
					v += r.nextGaussian();
					v = Math.round(v * 100D) / 100D;
					data.add(v);
				}

				System.out.println(data);

				this.closeValues.put(s, data);
				this.symbols.add(s);
				this.numberOfDaysInFiles = data.size();
			} catch (StockException ex) {
				ex.printStackTrace();
			}
		}
	}

	public List<OptimizedStock> optimize() {

		for (int i = 0; i < this.symbols.size(); i++) {
			String symbol = this.symbols.get(i);
			ArrayList<Double> data = this.closeValues.get(symbol);
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
			Moments m = this.factorCalculator.centralMoments(data);
			this.averageReturns.add(m.mean);
			this.returnStDev.add(m.sd);
			Double sharpeRatio = (m.mean / m.sd) * Math.sqrt(data.size());
			this.sharepeRatios.add(sharpeRatio);

			System.out.println(symbol + " " + dailyRet.size() + " " + m.mean
					+ " " + m.sd + " " + sharpeRatio);
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

		System.out.println(Arrays.toString(sortedSharepeIndiceTopK));

		// Fill covData table
		Double[][] covData = new Double[sortedSharepeIndiceTopK.length][this.dailyRetSize];
		for (int i = 0; i < sortedSharepeIndiceTopK.length; i++) {
			String symbol = this.symbols.get(i);
			covData[i] = this.dailyReturns.get(symbol).toArray(
					new Double[this.dailyRetSize]);
		}
		System.out.println("covData");

		// Calculate correlation Matrix for all the combinations
		// FIXME - maybe need some correction|normalization
		Double[][] covDataTranspose = new Double[this.dailyRetSize][sortedSharepeIndiceTopK.length];
		for (int x = 0; x < covData.length; x++) {
			for (int y = 0; y < covData[x].length; y++) {
				covDataTranspose[y][x] = covData[x][y];
			}
		}
		Double[][] correlationMatrix = new Double[this.TOP_K_EQUITIES][this.TOP_K_EQUITIES];
		for (int x = 0; x < this.TOP_K_EQUITIES; x++) {
			for (int y = 0; y < this.TOP_K_EQUITIES; y++) {
				List<Double> xList = Arrays.asList(covDataTranspose[x]);
				List<Double> yList = Arrays.asList(covDataTranspose[y]);
				Double covXY = factorCalculator.covariance(xList, yList);
				Double sdX = factorCalculator.centralMoments(xList).sd;
				Double sdY = factorCalculator.centralMoments(yList).sd;
				correlationMatrix[x][y] = factorCalculator.correlation(covXY,
						sdX, sdY);

			}
		}
		System.out.println("corrMatrix");

		// # Create all possible combinations of the n top equites for the given
		// portfolio size.

		List<List<Integer>> portfolios = new LinkedList<List<Integer>>();
		Integer[] range = new Integer[this.TOP_K_EQUITIES];
		for (int i = 0; i < range.length; i++) {
			range[i] = i;
		}
		portfolios.addAll(ArrayUtils.combination(Arrays.asList(range),
				this.PORTFOLIO_SIZE));
		System.out.println(portfolios);

		// Calculate sum of correlation coeficients for each portfolio
		Double[] totalSumCorrelation = new Double[portfolios.size()];
		for (int i = 0; i < portfolios.size(); i++) {
			List<Integer> p = portfolios.get(i);

			Double corrSum = 0D;
			List<List<Integer>> corrCombination = new LinkedList<List<Integer>>();
			corrCombination.addAll(ArrayUtils.combination(p, 2));
			for (List<Integer> c : corrCombination) {
				System.out.print(c.get(0) + "_" + c.get(1) + "="
						+ df.format(correlationMatrix[c.get(0)][c.get(1)])
						+ "\t");
				corrSum += correlationMatrix[c.get(0)][c.get(1)];
			}
			System.out.println();

			totalSumCorrelation[i] = corrSum;
		}

		// Find the portfolio with lowest correlation coefficient
		Double minCorr = totalSumCorrelation[0];
		int minIndex = 0;
		for (int i = 0; i < totalSumCorrelation.length; i++) {
			Double c = totalSumCorrelation[i];
			if (c > minCorr) {
				minCorr = c;
				minIndex = i;
			}
		}
		System.out.println("best");

		// Print result
		List<Integer> bestPortfolio = portfolios.get(minIndex);
		System.out.println("Best portfolio Sum(corr)="
				+ totalSumCorrelation[minIndex]);
		System.out.println("i" + "\t" + "s" + "\t" + "symbol" + "\t" + "sharpe"
				+ "\t" + "avgRet" + "\t" + "retStdev");

		ArrayList<OptimizedStock> result = new ArrayList<OptimizedStock>();
		for (Integer i : bestPortfolio) {
			int sortedIndice = sortedSharepeIndice[i];
			String symbol = this.symbols.get(sortedIndice);
			Double sharpeValue = this.sharepeRatios.get(sortedIndice);
			Double avgRet = this.averageReturns.get(sortedIndice);
			Double retStdev = this.returnStDev.get(sortedIndice);
			OptimizedStock optimizedStock = new OptimizedStock();
			optimizedStock.setSymbol(symbol);
			optimizedStock.setAvgReturn(avgRet);
			optimizedStock.setReturnStdev(retStdev);
			optimizedStock.setSharpeRatio(sharpeValue);
			result.add(optimizedStock);
			System.out.println(i + "\t" + sortedIndice + "\t" + symbol + "\t"
					+ df.format(sharpeValue) + "\t" + df.format(avgRet) + "\t"
					+ df.format(retStdev));
		}
		return result;

	}

	public void printResult(List<OptimizedStock> result) {
		System.out.println("symbol" + "\t" + "sharpe" + "\t" + "avgRet" + "\t"
				+ "retStdev");
		for (OptimizedStock o : result) {
			System.out.println(o.getSymbol() + "\t"
					+ df.format(o.getSharpeRatio()) + "\t"
					+ df.format(o.getAvgReturn()) + "\t"
					+ df.format(o.getReturnStdev()));
		}
	}

	public void readFromDir(File dir) {
		if (dir.isDirectory()) {
			FilenameFilter filter = new FilenameFilter() {
				@Override
				public boolean accept(File directory, String fileName) {
					return fileName.endsWith(".csv");
				}
			};
			File[] files = dir.listFiles(filter);

			for (File f : files) {

				try {
					String s = f.getName().replace(".csv", "");
					StockManager sm = new StockManager(f.getCanonicalPath()
							.replace(".csv", ""));

					@SuppressWarnings("unchecked")
					List<StockDayObject> dataRaw = (List<StockDayObject>) sm
							.getAll();
					ArrayList<Double> data = new ArrayList<Double>();
					for (StockDayObject o : dataRaw) {
						Double v = Double.valueOf(o.adjClose);
						data.add(v);
					}

					System.out.println(s + " " + data.size());

					ArrayList<Double> dataShort = new ArrayList<Double>();
					dataShort.addAll(data.subList(0, 50));
					this.closeValues.put(s, dataShort);
					this.symbols.add(s);
					this.numberOfDaysInFiles = dataShort.size();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}

	}

}
