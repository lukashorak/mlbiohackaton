package com.luki.mlbio.hackathon;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.luki.mlbio.hackathon.model.IOverlayDataManager;
import com.luki.mlbio.hackathon.model.IOverlayDataObject;
import com.luki.mlbio.hackathon.model.OverlayException;

public class OverlayDataManager implements IOverlayDataManager{
	
	public OverlayDataManager(){
		
	}
	
	public OverlayDataManager(String stockId) {

		SimpleOverlayDataReader reader = new SimpleOverlayDataReader();
		this.data.addAll(reader.readOverlayDataPlain(new File(stockId + ".csv")));
	}

	private List<IOverlayDataObject> data = new ArrayList<IOverlayDataObject>();

	@Override
	public List<? extends IOverlayDataObject> getAll() throws OverlayException {
		return this.data;
	}

	@Override
	public List<? extends IOverlayDataObject> getByDuration(String start,
			String end) throws OverlayException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<? extends IOverlayDataObject> getByMonth(String year,
			String month) throws OverlayException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<? extends IOverlayDataObject> getByDate(String year,
			String month, String day) throws OverlayException {
		// TODO Auto-generated method stub
		return null;
	}

}
