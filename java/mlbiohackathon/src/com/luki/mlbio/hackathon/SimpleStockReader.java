package com.luki.mlbio.hackathon;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.luki.mlbio.hackathon.model.IStockDayObject;
import com.luki.mlbio.hackathon.model.StockDayObject;

public class SimpleStockReader {

	public SimpleStockReader() {

	}

	public List<IStockDayObject> readStocksPlain(File f) {
		List<IStockDayObject> dataList = new ArrayList<IStockDayObject>();

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
			// !!!Skip first line
			br.readLine();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			while ((strLine = br.readLine()) != null) {
				lineNum++;

				// Check if line match regexAcceptor, otherwise skip
				String regexAcceptor = "";
				if (strLine.contains(regexAcceptor)) {

					String[] val = strLine.split(",");

					if (val.length > 0) {
						try {
							StockDayObject d = new StockDayObject();
							d.setTick(f.getName());

							try {
								d.setDate(sdf.parse(val[0]));
							} catch (ParseException e) {
								System.out
										.println("Wrong date format - use: yyyy-MM-dd");
								e.printStackTrace();
							}

							d.setOpen(val[1]);
							d.setHigh(val[2]);
							d.setLow(val[3]);
							d.setClose(val[4]);
							d.setVolume(val[5]);
							d.setAdjClose(val[6]);

							dataList.add(d);
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

		return dataList;
	}
}
