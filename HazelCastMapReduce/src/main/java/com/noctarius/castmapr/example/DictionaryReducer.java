package com.noctarius.castmapr.example;

import java.util.Iterator;

import com.noctarius.castmapr.spi.Reducer;

public class DictionaryReducer implements Reducer<String, String> {

	//@Override
	public String reduce(String key, Iterator<String> values) {
		StringBuilder sb = new StringBuilder();
		while (values.hasNext()) {
			String value = values.next();
			sb.append(value).append("|");
		}
		String result = sb.toString();
		return result.substring(0, result.length() - 1);
	}
}
