package com.luki.mlbio.hackathon.portfolio;

import java.io.Serializable;

public class OptimizedStock implements Serializable {

	private String symbol;

	private Double sharpeRatio;
	private Double avgReturn;
	private Double returnStdev;

	public OptimizedStock() {

	}

	public Double getAvgReturn() {
		return avgReturn;
	}

	public Double getReturnStdev() {
		return returnStdev;
	}

	public Double getSharpeRatio() {
		return sharpeRatio;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setAvgReturn(Double avgReturn) {
		this.avgReturn = avgReturn;
	}

	public void setReturnStdev(Double returnStdev) {
		this.returnStdev = returnStdev;
	}

	public void setSharpeRatio(Double sharpeRatio) {
		this.sharpeRatio = sharpeRatio;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

}
