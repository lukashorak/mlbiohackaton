package com.luki.mlbio.hackathon;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.luki.mlbio.hackathon.model.IStockDayObject;
import com.luki.mlbio.hackathon.model.StockException;

public class StockManagerTest {

	StockManager sm;

	@Before
	public void prepare() {
		this.sm = new StockManager("2317");
	}

	@Test
	public void testGetAll() throws StockException {
		List<IStockDayObject> data = (List<IStockDayObject>) this.sm.getAll();
		System.out.println("Data size =" + data.size());
		if (data == null) {
			fail("Empty data");
		}
	}

	@Test
	public void testGetByDuration() throws StockException {
		List<IStockDayObject> data = (List<IStockDayObject>) this.sm.getByDuration("2012-10-16", "2012-10-17");
		System.out.println("Data size =" + data.size());
		if (data == null) {
			fail("Empty data");
		}
	}

	@Test
	public void testGetByMonth() throws StockException {
		List<IStockDayObject> data = (List<IStockDayObject>) this.sm.getByMonth("2012","10");
		System.out.println("Data size =" + data.size());
		if (data == null) {
			fail("Empty data");
		}
	}

	@Test
	public void testGetByDate() throws StockException {
		List<IStockDayObject> data = (List<IStockDayObject>) this.sm.getByDate("2012","10","16");
		System.out.println("Data size =" + data.size());
		if (data == null) {
			fail("Empty data");
		}
	}

}
