package com.luki.mlbio.hackathon;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import com.luki.mlbio.hackathon.model.IStockDayObject;
import com.luki.mlbio.hackathon.model.StockException;

public class Main {

	public static void main(String[] args) throws StockException {

		if (args.length >= 2) {
			String stockId = args[0];
			String method = args[1];

			String[] params = Arrays.copyOfRange(args, 2, args.length);
			StockManager sm = new StockManager(stockId);

			Class<?> cls;
			try {
				cls = Class.forName(StockManager.class.getCanonicalName());
				Method[] methods = cls.getDeclaredMethods();

				for (Method m : methods) {
					if (m.getName().equals(method)) {
						System.out.println(">>" + m.getName());

						Object o = m.invoke(sm, (Object[]) params);
						@SuppressWarnings("unchecked")
						List<? extends IStockDayObject> ret = (List<? extends IStockDayObject>) o;

						System.out.println("\t>>" + ret.size());

						return;
					}
				}

				System.err.println("Method " + method + " don't exist");

			} catch (Exception e) {
				e.printStackTrace();
				throw new StockException("Can't execute method " + method);
			}

		} else {
			throw new StockException(
					"Missing args - at least 2 (stockId, methodName) + ...methodArgs");
		}

	}
}
