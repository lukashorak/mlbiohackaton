package com.luki.mlbio.hackathon;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.luki.mlbio.hackathon.model.IStockDayObject;
import com.luki.mlbio.hackathon.model.IStockManager;
import com.luki.mlbio.hackathon.model.StockException;

public class StockManager implements IStockManager {

	public StockManager(String stockId) {

		SimpleStockReader reader = new SimpleStockReader();
		this.data.addAll(reader.readStocksPlain(new File(stockId + ".csv")));
	}

	private List<IStockDayObject> data = new ArrayList<IStockDayObject>();

	@Override
	public List<? extends IStockDayObject> getAll() throws StockException {
		return this.data;
	}

	@Override
	public List<? extends IStockDayObject> getByDuration(String start,
			String end) throws StockException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = null;
		Date endDate = null;
		try {
			startDate = sdf.parse(start);
		} catch (ParseException e) {
			throw new StockException("Can't parse start date :" + start);
		}
		try {
			endDate = sdf.parse(end);
		} catch (ParseException e) {
			throw new StockException("Can't parse end date :" + end);
		}

		ArrayList<IStockDayObject> returnList = new ArrayList<IStockDayObject>();

		Iterator<IStockDayObject> it = this.data.iterator();
		while (it.hasNext()) {
			IStockDayObject o = it.next();
			if (o.getDate().compareTo(startDate) >= 0
					&& o.getDate().compareTo(endDate) <= 0) {
				returnList.add(o);
			}
		}

		return returnList;
	}

	@Override
	public List<? extends IStockDayObject> getByMonth(String year, String month)
			throws StockException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Date date = null;
		try {
			date = sdf.parse(year + "-" + month);
		} catch (ParseException e) {
			throw new StockException("Can't parse date :" + year + "-" + month);
		}

		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date);
		ArrayList<IStockDayObject> returnList = new ArrayList<IStockDayObject>();

		Calendar calO = Calendar.getInstance();
		Iterator<IStockDayObject> it = this.data.iterator();
		while (it.hasNext()) {
			IStockDayObject o = it.next();
			calO.setTime(o.getDate());
			if (cal1.get(Calendar.YEAR) == calO.get(Calendar.YEAR)
					&& cal1.get(Calendar.MONTH) == calO.get(Calendar.MONTH)) {
				returnList.add(o);
			}
		}

		return returnList;
	}

	@Override
	public List<? extends IStockDayObject> getByDate(String year, String month,
			String day) throws StockException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = sdf.parse(year + "-" + month + "-" + day);
		} catch (ParseException e) {
			throw new StockException("Can't parse date :" + year + "-" + month
					+ "-" + day);
		}

		ArrayList<IStockDayObject> returnList = new ArrayList<IStockDayObject>();

		Iterator<IStockDayObject> it = this.data.iterator();
		while (it.hasNext()) {
			IStockDayObject o = it.next();
			if (o.getDate().compareTo(date) == 0) {
				returnList.add(o);
			}
		}

		return returnList;
	}

}
