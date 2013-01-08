package com.luki.mlbio.hackathon.portfolio;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

public class ArrayIndexComparatorTest {

	@Test
	public void test() {
		Double[] data = { 1D, 0D, 0.5D, 1.5D };
		for (Double d : data) {
			System.out.print(d + " ");
		}
		System.out.println();

		ArrayIndexComparator comparator = new ArrayIndexComparator(data);
		Integer[] indexes = comparator.createIndexArray();

		Arrays.sort(indexes, comparator);
		for (Integer d : indexes) {
			System.out.print(d + " ");
		}
		System.out.println();
		Double[] orderData = new Double[data.length];
		for (int i = 0; i < indexes.length; i++) {
			Integer d = indexes[i];
			System.out.print(data[d] + " ");
			orderData[i] = data[d];
		}
		System.out.println();

		Double[] orderDataTrue = { 0D, 0.5D, 1D, 1.5D };

		if (!Arrays.deepEquals(orderDataTrue, orderData)) {
			fail("Not the same");
		}

	}

}
