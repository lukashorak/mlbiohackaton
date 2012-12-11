package com.luki.mlbio.hackathon.model;

import java.util.List;


public interface IStockManager {

	//public IStockManager(String stockId);

	public List<? extends IStockDayObject> getAll() throws StockException;
	
	public List<? extends IStockDayObject> getByDuration(String start, String end) throws StockException;
	
	public List<? extends IStockDayObject> getByMonth(String year, String month) throws StockException;
	
	public List<? extends IStockDayObject> getByDate(String year,String month, String day) throws StockException;

}
