package com.luki.mlbio.hackathon.model;

import java.util.Comparator;

public class StockDayDateComparator implements Comparator<StockDayObject> {

	public StockDayDateComparator() {
		super();
	}

	@Override
	public int compare(StockDayObject obj0, StockDayObject obj1) {
		return obj0.getDate().compareTo(obj1.getDate());
	}
}