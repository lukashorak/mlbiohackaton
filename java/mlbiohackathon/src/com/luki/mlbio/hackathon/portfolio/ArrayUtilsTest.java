package com.luki.mlbio.hackathon.portfolio;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

public class ArrayUtilsTest {

	@Test
	public void testCombination() {
		List<List<String>> powerSet = new LinkedList<List<String>>();

		String[] args2 = { "ted", "williams", "golden", "voice", "radio" };
		for (int i = 1; i <= args2.length; i++) {
			// powerSet.addAll(combination(Arrays.asList(args2), i));
		}

		powerSet.addAll(ArrayUtils.combination(Arrays.asList(args2), 3));
		System.out.println(powerSet);

	}

}
