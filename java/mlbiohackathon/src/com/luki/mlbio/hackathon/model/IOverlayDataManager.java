package com.luki.mlbio.hackathon.model;

import java.io.Serializable;
import java.util.List;

public interface IOverlayDataManager extends Serializable {

	public List<? extends IOverlayDataObject> getAll(String stock) throws OverlayException;

	public IOverlayDataObject getByDate(String stock, String year, String month,
			String day) throws OverlayException;

}
