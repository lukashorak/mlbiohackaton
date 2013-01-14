package com.luki.mlbio.hackathon;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.luki.mlbio.hackathon.model.IOverlayDataManager;
import com.luki.mlbio.hackathon.model.IOverlayDataObject;
import com.luki.mlbio.hackathon.model.OverlayDataDateComparator;
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
	public List<? extends IOverlayDataObject> getAll(String stock, List<String> keys)
			throws OverlayException {

		List<? extends IOverlayDataObject> data = this.data.get(stock);
		if (data != null) {
			Collections.sort(data, new OverlayDataDateComparator());
			
		}
		return data;
	}

	@Override
	public IOverlayDataObject getByDate(String stock, String year,
			String month, String day, List<String> keys) throws OverlayException {
		// TODO Auto-generated method stub
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = sdf.parse(year + "-" + month + "-" + day);
		} catch (ParseException e) {
			throw new OverlayException("Can't parse date :" + year + "-"
					+ month + "-" + day);
		}

		return this.getByDate(stock, date,keys);
	}

	public IOverlayDataObject getByDate(String stock, Date day, List<String> keys)
			throws OverlayException {
		List<? extends IOverlayDataObject> data = this.data.get(stock);
		if (data != null) {
			Collections.sort(data, new OverlayDataDateComparator());
		}
		IOverlayDataObject ret = null;
		if (data.size() > 0) {
			ret = data.get(0);
			if (data.size() == 1) {
				return ret;
			} else {

				Iterator<? extends IOverlayDataObject> it = data.iterator();
				while (it.hasNext()) {

					IOverlayDataObject val = it.next();
					if (val.getDate().compareTo(day) >= 0) {
						return ret;
					} else {
						ret = val;
					}
				}
			}
		}

		return ret;

	}

}
