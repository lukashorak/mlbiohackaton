package com.luki.mlbio.hackathon.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public interface IOverlayDataObject extends Serializable {

	public String getTick();

	public void setTick(String tick);

	public Date getDate();

	public void setDate(Date date);

	public Map<String, String> getData();

	public void setData(Map<String, String> data);

}
