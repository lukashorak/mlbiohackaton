package com.luki.mlbio.hackathon;

import java.io.File;

public class MainOverlay {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		File f = new File("overlay1.csv");
		
		SimpleOverlayDataReader r = new SimpleOverlayDataReader();
		r.readOverlayDataPlain(f);
		
		
		

	}

}
