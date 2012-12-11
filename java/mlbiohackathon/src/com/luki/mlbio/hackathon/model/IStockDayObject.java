package com.luki.mlbio.hackathon.model;
import java.util.Date;

public interface IStockDayObject {

	public Date getDate();

	public void setDate(Date date);

	public String getLow();

	public void setLow(String low);

	public String getHigh();

	public void setHigh(String high);

	public String getOpen();

	public void setOpen(String open);

	public String getClose();

	public void setClose(String close);

	public String getAdjClose();

	public void setAdjClose(String adjClose);
	
	public String getVolume();

	public void setVolume(String volume);
	
		

}
