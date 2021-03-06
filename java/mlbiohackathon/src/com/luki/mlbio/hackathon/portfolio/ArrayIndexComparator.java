package com.luki.mlbio.hackathon.portfolio;

import java.util.Comparator;

public class ArrayIndexComparator implements Comparator<Integer> {
	private final Double[] array;

	public ArrayIndexComparator(Double[] array) {
		this.array = array;
	}

	@Override
	public int compare(Integer index1, Integer index2) {
		// Autounbox from Integer to int to use as array indexes
		return array[index1].compareTo(array[index2]);
	}

	public Integer[] createIndexArray() {
		Integer[] indexes = new Integer[array.length];
		for (int i = 0; i < array.length; i++) {
			indexes[i] = i; // Autoboxing
		}
		return indexes;
	}
}