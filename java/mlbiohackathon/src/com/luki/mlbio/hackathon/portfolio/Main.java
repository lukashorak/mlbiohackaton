package com.luki.mlbio.hackathon.portfolio;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import com.luki.mlbio.hackathon.StockManager;
import com.luki.mlbio.hackathon.model.IStockDayObject;
import com.luki.mlbio.hackathon.model.StockException;

public class Main {

	/**
	 * @param args
	 * @throws StockException
	 */
	public static void main(String[] args) {

		Main m = new Main();
		try {
			String[] arg2 = {"c:\\PROG\\ml\\data2\\","20","5"};
			m.run(arg2);
		} catch (StockException e) {
			e.printStackTrace();
		}
	}

	public void run(String[] args) throws StockException {
		if (args.length >= 1) {
			String dirName = args[0];
			File dir = new File(dirName);
			Integer topK = 10;
			Integer portfolioSize = 4;
			if (args.length == 3) {
				try {
					topK = Integer.valueOf(args[1]);
					portfolioSize = Integer.valueOf(args[2]);
				} catch (Exception e) {
					throw new StockException(
							"Wrong 2nd or 3rd parameter - integer topK, integer portfolioSize");
				}
			}

			PortfolioOptimization po = new PortfolioOptimization(portfolioSize,
					topK);
			po.readFromDir(dir);
			List<OptimizedStock> bestPortfolio = po.optimize();
			po.printResult(bestPortfolio);

		} else {
			throw new StockException("Missing args - directory with stock data");
		}
	}

}
