package com.luki.mlbio.hackathon;

import java.io.File;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.luki.mlbio.hackathon.model.IOverlayDataManager;
import com.luki.mlbio.hackathon.model.IOverlayDataObject;
import com.luki.mlbio.hackathon.model.OverlayException;

public class OverlayDataManager implements IOverlayDataManager {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<String, List<IOverlayDataObject>> data = new LinkedHashMap<String, List<IOverlayDataObject>>();

	public OverlayDataManager() {
	}

	public OverlayDataManager(String overlayFile) {
		SimpleOverlayDataReader reader = new SimpleOverlayDataReader();
		this.data.putAll(reader.readOverlayDataPlain(new File(overlayFile)));
	}

	@Override
	public List<? extends IOverlayDataObject> getAll(String stock)
			throws OverlayException {
		return null;
	}

	@Override
	public IOverlayDataObject getByDate(String stock, String year,
			String month, String day) throws OverlayException {
		// TODO Auto-generated method stub
		return null;
	}

	public IOverlayDataObject getByDate(String stock, Date day)
			throws OverlayException {
		// TODO Auto-generated method stub
		return null;
	}

}
