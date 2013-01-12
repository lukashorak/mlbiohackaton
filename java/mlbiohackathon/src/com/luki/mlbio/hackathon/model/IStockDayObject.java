package com.luki.mlbio.hackathon.model;

import java.io.Serializable;
import java.util.Date;

public interface IStockDayObject extends Serializable {

	public String getAdjClose();

	public String getClose();

	public Date getDate();

	public String getHigh();

	public String getLow();

	public String getOpen();

	public String getVolume();

	public void setAdjClose(String adjClose);

	public void setClose(String close);

	public void setDate(Date date);

	public void setHigh(String high);

	public void setLow(String low);

	public void setOpen(String open);

	public void setVolume(String volume);

}
