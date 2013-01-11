package com.luki.mlbio.hackathon;

import java.util.List;

import com.luki.mlbio.hackathon.model.IStockDayObject;
import com.luki.mlbio.hackathon.model.StockException;

public interface IStockManager {

	// public IStockManager(String stockId);

	public List<? extends IStockDayObject> getAll() throws StockException;

	public List<? extends IStockDayObject> getByDuration(String start,
			String end) throws StockException;

	public List<? extends IStockDayObject> getByMonth(String year, String month)
			throws StockException;

	public List<? extends IStockDayObject> getByDate(String year, String month,
			String day) throws StockException;

	public List<? extends IStockDayObject> getAllFromDate(String year,
			String month, String day) throws StockException;

	public List<? extends IStockDayObject> getLastXDays(String days)
			throws StockException;

}
