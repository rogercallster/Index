package com.noctarius.castmapr.example;

import java.io.IOException;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.StreamSerializer;

public class DictionaryEntrySerializer implements
		StreamSerializer<DictionaryEntry> {

	//@Override
	public int getTypeId() {
		return 999;
	}

	//@Override
	public void destroy() {
	}

	//@Override
	public void write(ObjectDataOutput out, DictionaryEntry object)
			throws IOException {

		out.writeUTF(object.getValue());
		out.writeUTF(object.getLanguage());
	}

	//@Override
	public DictionaryEntry read(ObjectDataInput in) throws IOException {
		String value = in.readUTF();
		String language = in.readUTF();
		return new DictionaryEntry(value, language);
	}

}
