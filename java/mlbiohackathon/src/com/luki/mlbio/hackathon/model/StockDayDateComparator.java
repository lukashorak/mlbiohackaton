package com.luki.mlbio.hackathon.model;

import java.util.Comparator;

public class StockDayDateComparator implements Comparator<IStockDayObject> {

	public StockDayDateComparator() {
		super();
	}

	@Override
	public int compare(IStockDayObject obj0, IStockDayObject obj1) {
		return obj0.getDate().compareTo(obj1.getDate());
	}
}