package com.luki.mlbio.hackathon.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StockDayObject implements IStockDayObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String tick;

	public Date date;

	public String open;
	public String high;
	public String low;
	public String close;
	public String volume;
	public String adjClose;

	public StockDayObject() {

	}

	@Override
	public String getAdjClose() {
		return this.adjClose;
	}

	@Override
	public String getClose() {
		return this.close;
	}

	@Override
	public Date getDate() {
		return this.date;
	}

	@Override
	public String getHigh() {
		return this.high;
	}

	@Override
	public String getLow() {
		return this.low;
	}

	@Override
	public String getOpen() {
		return this.open;
	}

	public String getTick() {
		return this.tick;
	}

	@Override
	public String getVolume() {
		return this.volume;
	}

	public String print() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String ret = this.tick + "," + sdf.format(this.date) + "," + this.open
				+ "," + this.high + "," + this.low + "," + this.close + ","
				+ this.volume + "," + this.adjClose;
		return ret;
	}

	@Override
	public void setAdjClose(String adjClose) {
		this.adjClose = adjClose;
	}

	@Override
	public void setClose(String close) {
		this.close = close;
	}

	@Override
	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public void setHigh(String high) {
		this.high = high;
	}

	@Override
	public void setLow(String low) {
		this.low = low;
	}

	@Override
	public void setOpen(String open) {
		this.open = open;
	}

	public void setTick(String tick) {
		this.tick = tick;
	}

	@Override
	public void setVolume(String volume) {
		this.volume = volume;
	}

}
