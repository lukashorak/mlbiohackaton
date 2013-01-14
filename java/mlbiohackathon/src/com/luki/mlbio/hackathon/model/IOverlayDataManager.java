package com.luki.mlbio.hackathon.model;

import java.io.Serializable;
import java.util.List;

public interface IOverlayDataManager extends Serializable {

	public List<? extends IOverlayDataObject> getAll(String stock, List<String> keys)
			throws OverlayException;

	public IOverlayDataObject getByDate(String stock, String year,
			String month, String day, List<String> keys) throws OverlayException;

}
