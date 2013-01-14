package com.luki.mlbio.hackathon.model;

import java.util.Comparator;

public class OverlayDataDateComparator implements
		Comparator<IOverlayDataObject> {

	public OverlayDataDateComparator() {
		super();
	}

	@Override
	public int compare(IOverlayDataObject obj0, IOverlayDataObject obj1) {
		return obj0.getDate().compareTo(obj1.getDate());
	}
}
