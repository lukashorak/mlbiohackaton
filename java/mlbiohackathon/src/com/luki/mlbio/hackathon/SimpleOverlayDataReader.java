package com.luki.mlbio.hackathon;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.luki.mlbio.hackathon.model.IOverlayDataObject;
import com.luki.mlbio.hackathon.model.OverlayDataObject;

public class SimpleOverlayDataReader {

	public SimpleOverlayDataReader() {

	}

	public Map<String, List<IOverlayDataObject>> readOverlayDataPlain(File f) {
		Map<String, List<IOverlayDataObject>> stockDataMap = new LinkedHashMap<String, List<IOverlayDataObject>>();

		try {
			// Open the file that is the first
			// command line parameter
			FileInputStream fstream = new FileInputStream(f);
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			// Read File Line By Line
			int lineNum = 0;
			int headerKeyCount = 0;

			strLine = br.readLine();
			// Read header
			String[] headerVal = strLine.split(",");
			headerKeyCount = (headerVal.length - 2);
			// First is date, Second is tick
			if (headerKeyCount <= 0) {
				throw new Exception("Header have no data");
			}

//			System.out.println("Header Keys:");
			List<String> headerKeys = new ArrayList<String>();
			for (int i = 2; i < headerKeyCount; i++) {
				headerKeys.add(headerVal[i]);
//				System.out.println(headerVal[i]);
			}

//			System.out.println("Data");
			SimpleDateFormat sdfMonth = new SimpleDateFormat("yyyy-MM");

			while ((strLine = br.readLine()) != null) {
				lineNum++;

				// Check if line match regexAcceptor, otherwise skip
				String regexAcceptor = "";
				if (strLine.contains(regexAcceptor)) {

					String[] val = strLine.split(",");

					if (val.length > 0) {
						try {

							String dateString = val[0];
							String tick = val[1];
							Date dateParsed = sdfMonth.parse(dateString);

//							System.out.print(tick + "\t" + dateParsed + "\t");

							HashMap<String, String> dataItemMap = new HashMap<String, String>();
							for (int i = 2; i < headerKeyCount; i++) {

								String key = headerKeys.get(i - 2);
								String value = val[i];

								dataItemMap.put(key, value);
//								System.out.print(key + ":" + value + " ");
							}

							OverlayDataObject overlayDataObject = new OverlayDataObject();
							overlayDataObject.tick = tick;
							overlayDataObject.date = dateParsed;
							overlayDataObject.data = dataItemMap;

//							System.out.println();

							List<IOverlayDataObject> dataList = null;
							if (stockDataMap.containsKey(tick)) {
								dataList = stockDataMap.get(tick);
							} else {
								dataList = new ArrayList<IOverlayDataObject>();
							}

							dataList.add(overlayDataObject);
							stockDataMap.put(tick, dataList);

							// dataList.add(d);
						} catch (Exception e) {
							System.out.println("Wrong format, data line="
									+ lineNum);
						}
					}
				} else {
					System.out.println("Skip line=" + lineNum);
				}
			}
			// Close the input stream
			in.close();
		} catch (Exception e) {// Catch exception if any
			e.printStackTrace();
		}

//		System.out.println("Summary");
//		System.out.println("Size of stockDataMap:" + stockDataMap.size());
		Iterator<Entry<String, List<IOverlayDataObject>>> it = stockDataMap
				.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, List<IOverlayDataObject>> e = it.next();
			String key = e.getKey();
//			System.out.println(key + "\t" + e.getValue().size());
		}
		return stockDataMap;
	}
}
