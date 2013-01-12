package com.luki.mlbio.hackathon.model;

import java.io.Serializable;
import java.util.List;

public interface IOverlayDataManager extends Serializable {

	public List<? extends IOverlayDataObject> getAll() throws OverlayException;

	public List<? extends IOverlayDataObject> getByDate(String year,
			String month, String day) throws OverlayException;

	public List<? extends IOverlayDataObject> getByDuration(String start,
			String end) throws OverlayException;

	public List<? extends IOverlayDataObject> getByMonth(String year,
			String month) throws OverlayException;

}
