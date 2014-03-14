package com.noctarius.castmapr.example;

import java.io.IOException;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.DataSerializable;
import com.noctarius.castmapr.spi.Collector;
import com.noctarius.castmapr.spi.Mapper;

public class DictionaryMapper extends
		Mapper<String, DictionaryEntry, String, String> implements
		DataSerializable {

	private String searchTerm;

	public DictionaryMapper() {
	}

	public DictionaryMapper(String searchTerm) {
		if (searchTerm == null)
			throw new NullPointerException("searchTerm must not be null");
		this.searchTerm = searchTerm.toLowerCase();
	}

	@Override
	public void map(String key, DictionaryEntry value,
			Collector<String, String> collector) {

		if (key == null)
			return;
		if (key.toLowerCase().contains(this.searchTerm)) {
			collector.emit(key, value.getValue());
		}
	}

	//@Override
	public void writeData(ObjectDataOutput out) throws IOException {
		out.writeUTF(searchTerm);
	}

	//@Override
	public void readData(ObjectDataInput in) throws IOException {
		searchTerm = in.readUTF();
	}
}
