package com.luki.mlbio.hackathon.model;

import java.util.List;

public interface IOverlayDataManager {
	
	public List<? extends IOverlayDataObject> getAll() throws OverlayException;
	
	public List<? extends IOverlayDataObject> getByDuration(String start, String end) throws OverlayException;
	
	public List<? extends IOverlayDataObject> getByMonth(String year, String month) throws OverlayException;
	
	public List<? extends IOverlayDataObject> getByDate(String year,String month, String day) throws OverlayException;

}
