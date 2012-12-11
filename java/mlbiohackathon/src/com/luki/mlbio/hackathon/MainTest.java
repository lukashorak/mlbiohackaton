package com.luki.mlbio.hackathon;

import org.junit.Before;
import org.junit.Test;

import com.luki.mlbio.hackathon.model.StockException;

public class MainTest {

	Main m;

	@Before
	public void prepare() {
		m = new Main();
	}

	@Test
	public void testMain() throws StockException {
		String argsGetAll[] = { "2317", "getAll" };
		String argsGetByDuration[] = { "2317", "getByDuration", "2012-10-16",
				"2012-10-17" };
		String argsGetByMonth[] = { "2317", "getByMonth", "2012", "10" };
		String argsGetByDate[] = { "2317", "getByDate", "2012", "10", "17" };

		m.main(argsGetAll);
		m.main(argsGetByDuration);
		m.main(argsGetByMonth);
		m.main(argsGetByDate);
	}
}
