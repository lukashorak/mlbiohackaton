package com.luki.mlbio.hackathon;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

import com.luki.mlbio.hackathon.model.IOverlayDataObject;
import com.luki.mlbio.hackathon.model.OverlayException;

public class MainOverlay {

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * @param args
	 */
	public static void main(String[] args) throws OverlayException {
		MainOverlay m = new MainOverlay();
		m.getDayExample(args);
	}

	public void getDayExample(String[] args) throws OverlayException {

		// TODO Auto-generated method stub

		// if (args.length < 2) {
		// System.out
		// .println("Not enough parameters, use: <stockId> <date(yyyy-mm-dd)> <keys(key1,key2,...,keyn)>");
		// return;
		// }
		//
		// String stock = args[0];
		// String day = args[1];
		//
		List<String> keys = new ArrayList<String>();
		keys.add("key1");
		if (args.length == 3) {
			for (int i = 2; i < args.length; i++) {
				keys.add(args[i]);
			}
		}

		File f = new File("overlay1.csv");

		OverlayDataManager m = new OverlayDataManager(f.getName());

		IOverlayDataObject obj = m.getByDate("2320", "2012", "11", "5", keys);
		System.out.print(obj.getTick() + "\t" + sdf.format(obj.getDate()));

		List<String> k = new ArrayList<String>();
		k.addAll(obj.getData().keySet());
		Collections.sort(k);

		for (String key : k) {
			if (keys.contains(key)) {
				System.out.print("\t" + key + ":" + obj.getData().get(key));
			}
		}

		System.out.println();

	}

	public void getAllExample(String[] args) throws OverlayException {
		List<String> keys = new ArrayList<String>();
		keys.add("key1");
		if (args.length == 3) {
			for (int i = 2; i < args.length; i++) {
				keys.add(args[i]);
			}
		}

		File f = new File("overlay1.csv");

		OverlayDataManager m = new OverlayDataManager(f.getName());

		List<? extends IOverlayDataObject> data = m.getAll("2320", keys);
		for (IOverlayDataObject o : data) {
			System.out.print(o.getTick() + "\t" + sdf.format(o.getDate()));

			List<String> k = new ArrayList<String>();
			k.addAll(o.getData().keySet());
			Collections.sort(k);

			for (String key : k) {
				if (keys.contains(key)) {
					System.out.print("\t" + key + ":" + o.getData().get(key));
				}
			}

			System.out.println();
		}

	}

}
