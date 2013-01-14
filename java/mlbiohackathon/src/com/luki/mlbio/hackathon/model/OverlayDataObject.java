package com.luki.mlbio.hackathon.model;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class OverlayDataObject implements IOverlayDataObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String tick;
	public Date date;
	public Map<String, String> data = new LinkedHashMap<String, String>();
	
	public OverlayDataObject() {

	}

	public String getTick() {
		return tick;
	}

	public void setTick(String tick) {
		this.tick = tick;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Map<String, String> getData() {
		return data;
	}

	public void setData(Map<String, String> data) {
		this.data = data;
	}

	

}
