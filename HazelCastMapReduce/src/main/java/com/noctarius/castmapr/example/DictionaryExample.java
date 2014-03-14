package com.noctarius.castmapr.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.util.Map.Entry;

import com.hazelcast.config.Config;
import com.hazelcast.config.SemaphoreConfig;
import com.hazelcast.config.SerializationConfig;
import com.hazelcast.config.SerializerConfig;
import com.hazelcast.config.XmlConfigBuilder;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ISemaphore;
import com.hazelcast.core.MultiMap;
import com.noctarius.castmapr.MapReduceTask;
import com.noctarius.castmapr.MapReduceTaskFactory;

public class DictionaryExample {

	private static final String BASE_URL = "http://www.ilovelanguages.com/IDP/files/%LANG%.txt";
	private static final String[] LANGUAGES = new String[] { "German",
			"Italian", "Portuguese", "French", "Latin", "Spanish" };

	public static void main(String[] args) {
		int nodesCount = Integer.parseInt(args[0]);
		String searchTerm = args[1];

		// Build the Hazelcast config and add the serializer
		Config config = new XmlConfigBuilder().build();
		config.addSemaphoreConfig(buildSemaphoreConfig(nodesCount));
		SerializationConfig sc = config.getSerializationConfig();
		sc.addSerializerConfig(buildSerializerConfig());

		// Start Hazelcast and get the distributed MultiMap
		HazelcastInstance hz = Hazelcast.newHazelcastInstance(config);
		MultiMap<String, DictionaryEntry> dic = hz.getMultiMap("dic");

		// If MultiMap is empty fill it up with dictionary entries
		if (dic.size() == 0) {
			readDictionaries(dic);  
		}

		// Now we wait for all cluster members to fully come up
		ISemaphore semaphore = hz.getSemaphore("members");
		semaphore.release();

		callMapReduceTask(searchTerm, hz, dic);

		Hazelcast.shutdownAll();
	}

	private static void callMapReduceTask(String searchTerm,
			HazelcastInstance hz, MultiMap<String, DictionaryEntry> dictionary) {

		MapReduceTaskFactory factory = MapReduceTaskFactory.newInstance(hz);
		MapReduceTask<String, DictionaryEntry, String, String> task = factory
				.build(dictionary);

		Map<String, String> result = task
				.mapper(new DictionaryMapper(searchTerm))
				.reducer(new DictionaryReducer()).submit();

		if (result.size() == 0) {
			System.out.println("No translation found for '" + searchTerm + "'");
		} else {
			System.out.println("Translations found for '" + searchTerm + "':");
			for (Entry<String, String> entry : result.entrySet()) {
				System.out.println(entry.getKey() + ": " + entry.getValue());
			}
		}
	}

	private static void readDictionaries(
			MultiMap<String, DictionaryEntry> dictionary) {

		for (String language : LANGUAGES) {
			try {
				System.out.print("Reading language " + language + "... ");

				URL url = new URL(BASE_URL.replace("%LANG%", language));
				URLConnection con = url.openConnection();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(con.getInputStream()));

				String line;
				while ((line = reader.readLine()) != null) {
					if (line.trim().startsWith("#"))
						continue;

					String[] terms = line.trim().split("\\t");
					if (terms.length != 2)
						continue;

					dictionary.put(terms[0], new DictionaryEntry(terms[1],
							language));
				}
				reader.close();
				System.out.println("done.");
			} catch (Exception e) {
				// Just ignore exceptions for now
				System.out.println("failed.");
				e.printStackTrace();
			}
		}
	}

	private static SerializerConfig buildSerializerConfig() {
		// Define a special serializer for dictionary entries
		SerializerConfig config = new SerializerConfig();
		config.setTypeClass(DictionaryEntry.class);
		config.setImplementation(new DictionaryEntrySerializer());
		return config;
	}

	private static SemaphoreConfig buildSemaphoreConfig(int nodesCount) {
		SemaphoreConfig config = new SemaphoreConfig();
		config.setName("members");
		config.setInitialPermits(nodesCount);
		return config;
	}
}
